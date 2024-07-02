package edu.cmu.cs.sasylf.util;

import edu.cmu.cs.sasylf.CloneData;
import edu.cmu.cs.sasylf.parser.Token;


public class StringSpan extends DefaultSpan {

	public StringSpan(String s, Location loc) {
		super(loc,loc.add(s.length()));
		builder.append(s);
	}

	public StringSpan(Token t) {
		this(t.image,new Location(t));
	}

	public void add(String s) {
		builder.append(s);
		this.shiftEndLocation(s.length());
	}

	public int length() {
		return builder.length();
	}
	
	@Override
	public String toString() {
		return builder.toString();
	}

	@Override
	public StringSpan copy(CloneData cd) {
		if (cd.containsCloneFor(this)) return (StringSpan) cd.getCloneFor(this);
		StringSpan clone = (StringSpan) super.copy(cd);
		cd.addCloneFor(this, clone);
		clone.builder = new StringBuilder(builder);
		return clone;
	}

	private StringBuilder builder = new StringBuilder();
}
