/**
 * 
 */
package edu.cmu.cs.sasylf.ast;

import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

import edu.cmu.cs.sasylf.CloneData;
import edu.cmu.cs.sasylf.SubstitutionData;
import edu.cmu.cs.sasylf.term.Constant;
import edu.cmu.cs.sasylf.term.Substitution;
import edu.cmu.cs.sasylf.util.ErrorHandler;
import edu.cmu.cs.sasylf.util.Errors;
import edu.cmu.cs.sasylf.util.Location;

/**
 * A syntax declaration that is renamed from an existing syntax declaration.
 * TODO: Figure out how to handle variable cases.
 * Make sure it works even when the source is hidden in a module.
 */
public class RenameSyntaxDeclaration extends SyntaxDeclaration {
	public QualName source;
	public SyntaxDeclaration original;
	
	/**
	 * Rename a syntax declaration (optionally) with clauses that must match the
	 * source.
	 * @param loc location of the declaration in the file
	 * @param nt name being defined locally
	 * @param l clauses making up the syntax, empty if clauses are hidden
	 */
	public RenameSyntaxDeclaration(Location loc, NonTerminal nt, QualName s, List<Clause> l) {
		super(loc, nt, l);
		source = s;
	}

	/**
	 * Rename a syntax declaration without clauses.
	 * @param loc location of the declaration in the file
	 * @param nt name being defined locally
	 */
	public RenameSyntaxDeclaration(Location loc, NonTerminal nt, QualName s) {
		super(loc, nt);
		source = s;
	}

	@Override
	protected void printExtra(PrintWriter out) {
		out.append('=');
		source.prettyPrint(out);
	}

	@Override
	public Variable getVariable() {
		// TODO not sure
		return super.getVariable();
	}

	@Override
	public void updateContext(Context ctx) {
		// There's a bug somewhere between here
		Object resolution = source.resolveNotPackage(ctx);
		// and here
		if (resolution != null) {
			if (resolution instanceof SyntaxDeclaration) {
				original = (SyntaxDeclaration)resolution;
			} else {
				ErrorHandler.recoverableError(Errors.RENAME_SYNTAX, QualName.classify(resolution) + " " + source, this);
			}
		}
		super.updateContext(ctx); // typeTerm() needs to work!
	}

	@Override
	public void precheck(Context ctx) {
		// TODO not sure
		super.precheck(ctx);
	}

	@Override
	public void typecheck(Context ctx) {
		// populate the clauses with premade clause defs that use the original:
		if (original != null && !isAbstract()) {
			List<Clause> originalClauses = original.getClauses();
			List<Clause> myClauses = getClauses();
			if (originalClauses.size() != myClauses.size()) {
				ErrorHandler.error(Errors.RENAME_LENGTH_MISMATCH, originalClauses.size() + " != " + myClauses.size(), this);
			}
			int n = myClauses.size();
			for (int i = 0; i < n; ++i) {
				Clause c = myClauses.get(i);
				Clause o = originalClauses.get(i);
				// must have same nonterminals in the same order.
				c = c.typecheck(ctx);
				if (c.isVarOnlyClause()) {
					if (!o.isVarOnlyClause()) {
						ErrorHandler.error(Errors.RENAME_MISMATCH, "" + o, c);
					}
				} else {
					if (o.isVarOnlyClause()) {
						ErrorHandler.error(Errors.RENAME_MISMATCH, "" + o, c);
					}
					ClauseDef cd;
					if (c instanceof ClauseDef) cd = (ClauseDef) c;
					else cd = new ClauseDef(c, this, ((ClauseDef)o).getConstructorName());
					// Now check that two clauses have the same sort of things
					// except terminals.
					c.checkClauseMatch(o);
					myClauses.set(i, cd);
				}
			}
		}
		// now do what we normally do:
		super.typecheck(ctx);
		Context.updateVersion();
	}

	
	
	/*
	@Override
	public void postcheck(Context ctx) {
		super.postcheck(ctx);
	}

	@Override
	public void setContext(ClauseDef cd) {
		super.setContext(cd);
	}

	@Override
	public Set<SyntaxDeclaration> getVarTypes() {
		// TODO Auto-generated method stub
		return super.getVarTypes();
	}
	*/

	@Override
	public boolean isProductive() {
		if (original != null) return original.isProductive();
		return super.isProductive();
	}

	@Override
	public boolean isInContextForm() {
		if (original != null) return original.isInContextForm();
		return super.isInContextForm();
	}

	@Override
	public Constant typeTerm() {
		if (original != null) return original.typeTerm();
		return super.typeTerm();
	}

	/*
	@Override
	public edu.cmu.cs.sasylf.grammar.NonTerminal getSymbol() {
		if (original != null) return original.getSymbol();
		return super.getSymbol();
	}

	@Override
	public String getTermSymbolString() {
		if (original != null) return original.getTermSymbolString();
		return super.getTermSymbolString();
	}

	@Override
	public GrmTerminal getTermSymbol() {
		if (original != null) return original.getTermSymbol();
		return super.getTermSymbol();
	}
	*/

	@Override
	public void computeSubordination() {
		// Nothing to do.
	}
	
	@Override
	public void collectQualNames(Consumer<QualName> consumer) {
		source.visit(consumer);
	}

	public void substitute(SubstitutionData sd) {
		if (sd.didSubstituteFor(this)) return;
		sd.setSubstitutedFor(this);
		super.substitute(sd);

		/*
			public QualName source;
			public SyntaxDeclaration original;
		*/

		if (source != null) source.substitute(sd);
		if (original != null) original.substitute(sd);


		System.out.println("original is now: " + original);

	}

	public RenameSyntaxDeclaration copy(CloneData cd) {
		if (cd.containsCloneFor(this)) return (RenameSyntaxDeclaration) cd.getCloneFor(this);

		RenameSyntaxDeclaration clone = (RenameSyntaxDeclaration) super.copy(cd);

		// clone source and original

		cd.addCloneFor(this, clone);

		if (clone.source != null) clone.source = clone.source.copy(cd);
		if (clone.original != null) {
			clone.original = clone.original.copy(cd);
		}
		return clone;
	}



}
