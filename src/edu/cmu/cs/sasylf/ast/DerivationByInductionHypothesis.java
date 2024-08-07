package edu.cmu.cs.sasylf.ast;

import edu.cmu.cs.sasylf.util.CopyData;
import edu.cmu.cs.sasylf.util.Location;



public class DerivationByInductionHypothesis extends DerivationByIHRule {
	public DerivationByInductionHypothesis(String n, Location l, Clause c) {
		super(n,l,c);
	}
	@Override
	public String prettyPrintByClause() {
		return " by induction hypothesis";
	}
	@Override
	public RuleLike getRule(Context ctx) {
		return ctx.currentTheorem;
	}
	@Override
	public String getRuleName() { return "induction hypothesis"; }

	@Override
	public void typecheck(Context ctx) {
		super.typecheck(ctx);

		this.checkInduction(ctx, ctx.currentTheorem, ctx.currentTheorem);
	}
	@Override
	public DerivationByInductionHypothesis copy(CopyData cd) {
		if (cd.containsCopyFor(this)) return (DerivationByInductionHypothesis) cd.getCopyFor(this);
		DerivationByInductionHypothesis clone = (DerivationByInductionHypothesis) super.copy(cd);
		cd.addCopyFor(this, clone);
		return clone;
	}

}
