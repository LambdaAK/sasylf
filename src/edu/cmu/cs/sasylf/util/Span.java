package edu.cmu.cs.sasylf.util;

import edu.cmu.cs.sasylf.CloneData;

/**
 * A span of text in the input file.
 */
public interface Span {
	public Location getLocation();
	public Location getEndLocation();
	public Span copy(CloneData cd);
}
