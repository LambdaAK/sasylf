package edu.cmu.cs.sasylf.ast;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

import edu.cmu.cs.sasylf.CloneData;
import edu.cmu.cs.sasylf.SubstitutionData;

/**
 * A part of a compilation unit/module.
 */
public interface Part extends Cloneable {

	/**
	 * Type check the elements in this chunk.
	 * @param ctx context to use, must not be null
	 */
	public abstract void typecheck(Context ctx);

	public abstract void prettyPrint(PrintWriter out);

	/**
	 * Export all top-level declarations in this chunk
	 * @param things collection to place in, must not be null.
	 */
	public abstract void collectTopLevel(Collection<? super Node> things);

	/**
	 * Export all rule-likes into a provided map
	 * @param map destination, must not be null.
	 */
	public abstract void collectRuleLike(Map<String,? super RuleLike> map);
	
	public abstract void collectQualNames(Consumer<QualName> consumer);

	public abstract void substitute(SubstitutionData sd);
	public abstract Part copy(CloneData cd);
}