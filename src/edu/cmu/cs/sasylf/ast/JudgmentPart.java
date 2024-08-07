package edu.cmu.cs.sasylf.ast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import edu.cmu.cs.sasylf.term.FreeVar;
import edu.cmu.cs.sasylf.term.Term;
import edu.cmu.cs.sasylf.util.CopyData;
import edu.cmu.cs.sasylf.util.ErrorReport;
import edu.cmu.cs.sasylf.util.Errors;
import edu.cmu.cs.sasylf.util.SASyLFError;
import edu.cmu.cs.sasylf.util.Util;

/**
 * A sequence of judgments that may be mutually recursive.
 */
public class JudgmentPart implements Part {
	List<Judgment> judgments;
	
	/**
	 * Create a part from a series of judgment declarations
	 * The collection is not stored locally.
	 * @param judges judgment declarations (with rules) (must not be null)
	 */
	public JudgmentPart(List<Judgment> judges) {
		judgments = new ArrayList<Judgment>(judges);
	}
	
	/**
	 * @param out
	 */
	@Override
	public void prettyPrint(PrintWriter out) {
		for (Judgment j: judgments) {
			j.prettyPrint(out);
		}
	}

	/**
	 * Type check the elements in this chunk.
	 * @param ctx context to use, must not be null
	 */
	@Override
	public void typecheck(Context ctx) {
		// Since judgments can be mutually recursive, we have multiple passes through the syntax
	
		for (Judgment j: judgments) {
			j.defineConstructor(ctx);
		}
	
		for (Judgment j: judgments) {
			try {
				j.typecheck(ctx);
			} catch (SASyLFError e) {
				// already reported.
			}
		}
	
		computeSubordinationJudgment();
	}

	void computeSubordinationJudgment() {
		for (Judgment j : judgments) {
			Clause form = j.getForm();
			if (form instanceof ClauseDef) {
				((ClauseDef)form).computeSubordination(true);
			}
			Term jType = j.typeTerm();
			for (Rule r : j.getRules()) {
				if (r.isAssumption()) {
					Util.debug("subordination: ", jType, " < ", jType, " forced");
					FreeVar.setAppearsIn(jType,jType);
					// Do we say that any assumption of a context nonterminal
					// means that we depend on anything that that context can have?
					// See good45.slf for why we answer: No.
					Term cType = r.getAssumes().getTypeTerm();
					Util.debug("subordination: ", jType, " < ", cType, " not forced.");
					// Not: FreeVar.setAppearsIn(jType,cType);
				}
				for (Clause cl : r.getPremises()) {
					if (!(cl instanceof ClauseUse)) continue; // avoid recovered error -> internal error
					Term pType = ((ClauseUse)cl).getTypeTerm();
					Util.debug("subordination: ", pType, " < ", jType);
					FreeVar.setAppearsIn(pType, jType);
				}
			}
			if (form instanceof ClauseDef) {
				((ClauseDef)form).checkSubordination();
			}
		}
	}
	
	@Override
	public void collectTopLevel(Collection<? super Node> things) {
		for (Judgment j : judgments) {
			things.add(j);
		}
	}
	
	@Override
	public void collectRuleLike(Map<String,? super RuleLike> map) {
		for (Judgment j : judgments) {
			for (Rule r : j.getRules()) {
				map.put(r.getName(),r);
			}
		}
	}
	
	@Override
	public void collectQualNames(Consumer<QualName> consumer) {
		for (Judgment j : judgments) {
			j.collectQualNames(consumer);
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Judgment j : judgments) {
			sb.append(j.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public void substitute(SubstitutionData sd) {
		if (sd.didSubstituteFor(this)) return;
		sd.setSubstitutedFor(this);
		for (Judgment j : judgments) {
			j.substitute(sd);
		}
	}

	@Override
	public JudgmentPart copy(CopyData cd) {
		if (cd.containsCopyFor(this)) return (JudgmentPart) cd.getCopyFor(this);
		JudgmentPart clone;
		try {
			clone = (JudgmentPart) super.clone();
		}
		catch (CloneNotSupportedException e) {
			ErrorReport report = new ErrorReport(Errors.INTERNAL_ERROR, "Clone not supported in class: " + getClass(), null, null, true);
			throw new SASyLFError(report);
		}

		cd.addCopyFor(this, clone);

		List<Judgment> newJudgments = new ArrayList<Judgment>();
		for (Judgment j : judgments) {
			newJudgments.add(j.copy(cd));
		}
		clone.judgments = newJudgments;
		
		return clone;
	}

	@Override
	public List<ModuleComponent> argsParams() {
		List<ModuleComponent> judgments = new ArrayList<>();

		for (Judgment j : this.judgments) {
			judgments.add(j.getOriginalDeclaration());
		}

		return judgments;
	}

	@Override
	public void collectTopLevelAsModuleComponents(Collection<ModuleComponent> things) {
		judgments.forEach(things::add);
	}

}