package edu.cmu.cs.sasylf.term;

import java.util.List;

import edu.cmu.cs.sasylf.CloneData;
import edu.cmu.cs.sasylf.SubstitutionData;
import edu.cmu.cs.sasylf.ast.Judgment;
import edu.cmu.cs.sasylf.ast.Syntax;
import edu.cmu.cs.sasylf.ast.Theorem;
import edu.cmu.cs.sasylf.util.Pair;


public abstract class Atom extends Term implements Cloneable {
	protected Atom(String name) { this.name = name; }

	public final String getName() { return name; }
	@Override
	public Term getType(List<Pair<String, Term>> varBindings) { return getType(); }
	@Override
	public abstract Term getType();
	private String name;

	@Override
	public int hashCode() { return name.hashCode(); }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Atom)) return false;
		if (obj.getClass() != this.getClass()) return false;
		Atom a = (Atom) obj;
		return name.equals(a.name);
	}

	@Override
	public Term apply(List<? extends Term> arguments, int whichApplied) {
		if (whichApplied < arguments.size())
			return new Application(this, arguments.subList(whichApplied,arguments.size()));
		else
			return this;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void substitute(SubstitutionData sd) {
		System.out.println("Atom.substitute");
		if (sd.didSubstituteFor(this)) return;
		
		sd.setSubstitutedFor(this);

		// need to substitute for name

		if (sd.containsSyntaxReplacementFor(name)) {
			System.out.println("WARNING: contains syntax replacement in Atom.substitute");
		}

		else if (sd.containsJudgmentReplacementFor(name)) {
			Judgment replacement = sd.getJudgmentReplacement();
			name = replacement.getName();
		}

		else if (sd.containsTheoremReplacementFor(name)) {
			Theorem theorem = sd.getTheoremReplacement();
			name = theorem.getName();
		}

	}
	
	public abstract Atom copy(CloneData cd);
}
