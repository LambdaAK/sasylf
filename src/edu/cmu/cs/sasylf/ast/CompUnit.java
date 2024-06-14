package edu.cmu.cs.sasylf.ast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import edu.cmu.cs.sasylf.module.Module;
import edu.cmu.cs.sasylf.module.ModuleFinder;
import edu.cmu.cs.sasylf.module.ModuleId;
import edu.cmu.cs.sasylf.module.NullModuleFinder;
import edu.cmu.cs.sasylf.util.ErrorHandler;
import edu.cmu.cs.sasylf.util.Errors;
import edu.cmu.cs.sasylf.util.Location;
import edu.cmu.cs.sasylf.util.ParseUtil;
import edu.cmu.cs.sasylf.util.SASyLFError;


public class CompUnit extends Node implements Module {
	public CompUnit(PackageDeclaration pack, Location loc, String n) {
		super(loc);
		packageDecl=pack; 
		moduleName = n; 
		updateReportCount();
	}

	/**
	 * Update the number of reports generated during parsing.
	 */
	protected void updateReportCount() {
		parseReports = ErrorHandler.getReports().size();
	}

	/**
	 * Add a chunk that is required by this module.
	 * @param c part to add, must not be null
	 */
	public void addParameterChunk(Part c) {
		params.add(c); 
		updateReportCount();
	}

	/**
	 * Add a part to this compilation unit.
	 * @param c
	 */
	public void addChunk(Part c) {
		parts.add(c);
		updateReportCount();
	}

	public PackageDeclaration getPackage() { return packageDecl; }

	/* (non-Javadoc)
	 * @see edu.cmu.cs.sasylf.ast.Module#getName()
	 */
	@Override
	public String getName() { return moduleName; }

	@Override
	public boolean isAbstract() {
		return !params.isEmpty();
	}

	private PackageDeclaration packageDecl;
	private String moduleName;
	private List<Part> params = new ArrayList<Part>();
	private List<Part> parts = new ArrayList<Part>();
	private int parseReports;

	public List<Part> getParams() {
		return params;
	}

	public List<Part> getParts() {
		return parts;
	}

	/* (non-Javadoc)
	 * @see edu.cmu.cs.sasylf.ast.Module#prettyPrint(java.io.PrintWriter)
	 */
	@Override
	public void prettyPrint(PrintWriter out) {
		packageDecl.prettyPrint(out);

		if (moduleName != null) {
			out.println("module " + moduleName);
		}

		if (!params.isEmpty()) {
			out.println("requires");
			for (Part part : params) {
				part.prettyPrint(out);
			}
			out.println("provides");
		}

		for (Part part : parts) {
			part.prettyPrint(out);
		}

		out.flush();
	}

	/**
	 * Return the number of reports generated by the parser before this
	 * compilation unit was generated.
	 * @return number of errors
	 */
	public int getParseReports() {
		return parseReports;
	}
	
	/* (non-Javadoc)
	 * @see edu.cmu.cs.sasylf.ast.Module#typecheck()
	 */
	@Override
	public boolean typecheck() {
		return typecheck(NullModuleFinder.get(),(ModuleId)null);  
	}

	/** Typechecks this compilation unit, returning true if the check was successful,
	 * false if there were one or more errors.
	 */
	public boolean typecheck(ModuleFinder mf, ModuleId id) {
		ErrorHandler.recordLastSpan(this);
		int oldCount = ErrorHandler.getErrorCount();
		Context ctx = new Context(mf,this);
		try {
			typecheck(ctx,id);
		} catch (SASyLFError e) {
			// ignore the error; it has already been reported
			//e.printStackTrace();
		}
		return ErrorHandler.getErrorCount() == oldCount;
	}

	/**
	 * Check this compilation unit in the given context.
	 * @param ctx context, must not be null
	 * @param id identifier declared for this compilation unit, or null if no declared module name
	 */
	public void typecheck(Context ctx, ModuleId id) {
		if (id != null) checkFilename(id);
		for (Part part : params) {
			try {
				part.typecheck(ctx);
				if (part instanceof JudgmentPart) {
					List<Node> pieces = new ArrayList<>();
					part.collectTopLevel(pieces);
					for (Node n : pieces) {
						if (n instanceof Judgment) {
							Judgment j = (Judgment)n;
							if (!j.isAbstract() && j.getRules().isEmpty()) {
								ErrorHandler.recoverableError(Errors.ABSTRACT_REQUIRED, j);
							}
						}
					}
				}
			} catch (SASyLFError ex) {
				// already reported
			}
		}
		for (Part part : parts) {
			try {
				part.typecheck(ctx);
			} catch (SASyLFError ex) {
				// already reported
			}
		}

	}

	private void checkFilename(ModuleId id) {
		packageDecl.typecheck(id.packageName);

		if (moduleName != null) {
			if (!ParseUtil.isLegalIdentifier(id.moduleName)) {
				ErrorHandler.error(Errors.BAD_FILE_NAME,this);
			}
			if (!moduleName.equals(id.moduleName)) {
				ErrorHandler.warning(Errors.WRONG_MODULE_NAME, this, moduleName+"\n"+id.moduleName);
			}
		}
	}

	/* (non-Javadoc)
	 * @see edu.cmu.cs.sasylf.ast.Module#collectTopLevel(java.util.Collection)
	 */
	@Override
	public void collectTopLevel(Collection<? super Node> things) {
		for (Part part : params) {
			part.collectTopLevel(things);
		}
		for (Part part : parts) {
			part.collectTopLevel(things);
		}
	}

	/* (non-Javadoc)
	 * @see edu.cmu.cs.sasylf.ast.Module#collectRuleLike(java.util.Map)
	 */
	@Override
	public void collectRuleLike(Map<String,? super RuleLike> map) {
		for (Part part : params) {
			part.collectRuleLike(map);
		}
		for (Part part : parts) {
			part.collectRuleLike(map);
		}
	}
	
	/**
	 * Collect all {@link QualName}s using the given consumer.
	 * @param consumer The consumer that accepts conditions based on the given {@link QualName}.
	 */
	@Override
	public void collectQualNames(Consumer<QualName> consumer) {
		for (Part part : params) {
			part.collectQualNames(consumer);
		}
		
		for (Part part : parts) {
			part.collectQualNames(consumer);
		}
	}

	private Map<String,Object> declCache = new HashMap<String,Object>();
	private int cacheVersion = -1;

	@Override
	public Object getDeclaration(Context ctx, String name) {
		if (cacheVersion != ctx.version()) {
			declCache.clear();
			Collection<Node> things = new ArrayList<Node>();
			this.collectTopLevel(things);
			this.collectRuleLike(declCache); // doesn't get syntax declarations or judgments
			for (Node n : things) {
				if (n instanceof SyntaxDeclaration) {
					SyntaxDeclaration sd = (SyntaxDeclaration)n;
					for (String s : sd.getAlternates()) {
						declCache.put(s, sd);
					}
				} else if (n instanceof Judgment) {
					Judgment jd = (Judgment)n;
					declCache.put(jd.getName(),jd);
				}
			}
			cacheVersion = ctx.version();
		}
		return declCache.get(name);
	}

	public void substitute(String from, String to) {
		for (Part part : parts) {
			part.substitute(from, to);
		}
	}

	public CompUnit clone() {
		CompUnit clone = (CompUnit) super.clone();
		/*
			private PackageDeclaration packageDecl;
			private String moduleName;
			private List<Part> params = new ArrayList<Part>();
			private List<Part> parts = new ArrayList<Part>();
			private int parseReports;
		*/
		clone.packageDecl = packageDecl.clone();

		List<Part> newParams = new ArrayList<>();
		for (Part p: params) {
			newParams.add(p.clone());
		}
		clone.params = newParams;

		List<Part> newParts = new ArrayList<>();
		for (Part p : parts) {
			newParts.add(p.clone());
		}
		clone.parts = newParts;

		return clone;
	}
}
