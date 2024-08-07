package edu.cmu.cs.sasylf.ast;

import edu.cmu.cs.sasylf.util.CopyData;
import edu.cmu.cs.sasylf.util.Location;


public class ClauseAssumption extends SyntaxAssumption {

	public ClauseAssumption(Clause c, Location location) {
		this(c,location,null);
	}

	public ClauseAssumption(Clause c, Location location, Element a) {
		super(null,location,a);
		clause = c;
	}


	@Override
	public Element getElementBase() {
		return clause;
	}

	private Clause clause;

	@Override
	public void typecheck(Context ctx) {
		super.typecheck(ctx);
		clause = clause.typecheck(ctx);
	}

	// ClauseAssumptions don't get added to the derivation map
	@Override
	public void addToDerivationMap(Context ctx) {
		throw new UnsupportedOperationException();
	}

	public void substitute(SubstitutionData sd) {
		if (sd.didSubstituteFor(this)) return;
		sd.setSubstitutedFor(this);
		clause.substitute(sd);
	}

	public ClauseAssumption copy(CopyData cd) {
		if (cd.containsCopyFor(this)) return (ClauseAssumption) cd.getCopyFor(this);

		ClauseAssumption clone = (ClauseAssumption) super.copy(cd);

		cd.addCopyFor(this, clone);
		
		clone.clause = clause.copy(cd);

		return clone;

	}
}
