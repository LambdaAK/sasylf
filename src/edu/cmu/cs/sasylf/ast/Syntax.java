package edu.cmu.cs.sasylf.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import edu.cmu.cs.sasylf.util.CopyData;
import edu.cmu.cs.sasylf.util.ErrorHandler;
import edu.cmu.cs.sasylf.util.Location;

/**
 * Definition of syntax.
 * These definitions affect the way SASyLF parses syntax.
 */
public abstract class Syntax extends Node implements ModuleComponent {
	/** Create a syntax node with a single location.
	 * @param l location this syntax starts at
	 */
	public Syntax(Location l) {
		super(l);
	}

	/**
	 * Create a syntax node with a starting and ending location
	 * @param l1 starting location, must not be null
	 * @param l2 ending location, must not be null
	 */
	public Syntax(Location l1, Location l2) {
		super(l1, l2);
	}

	/// Checking (multiple passes)
	// We require multiple passes to check syntax because
	// syntax can be used before it is defined, and can be recursive.
	// The following four methods handle the four passes.
	
	/**
	 * Update the contexts syntax maps so that we know about each declared entity.
	 * @param ctx TODO
	 */
	public void updateContext(Context ctx) {}

	/**
	 * Some last checks before we can type check.
	 * Currently this is needed only to make sure each variable is connected with a particular nonterminal.
	 * @param ctx context, must not be null
	 */
	public void precheck(Context ctx) {}

	/**
	 * Type check the syntax and update the grammar.
	 * @param ctx context, must not be null
	 */
	public abstract void typecheck(Context ctx);

	/**
	 * Checks that require that all syntax has been type checked.  Anything that
	 * needs to parse using the syntax should be done here.
	 * @param ctx context, must not be null
	 */
	public void postcheck(Context ctx) {}

	/**
	 * Get all the terminals used in this syntax definition.
	 * @return a new set of terminals appearing here.
	 */
	public abstract Set<Terminal> getTerminals();

	/**
	 * Compute Subordination relations using this syntax.
	 */
	public void computeSubordination() {}
	
	/**
	 * Check subordination among bindings in syntax
	 * definitions.
	 */
	public void checkSubordination() {}

	/**
	 * Get the original declaration of this syntax. If this syntax is a renaming,
	 * then this method will return the original syntax that was renamed.
	 * If this syntax is not a renaming, then this method will return this syntax.
	 * @return the original declaration of this syntax
	 */
	public abstract SyntaxDeclaration getOriginalDeclaration();

	/**
	 * Substitute inside of this Syntax according to the given substitution data.
	 * @param sd substitution data to use
	 */
	public abstract void substitute(SubstitutionData sd);

	/**
	 * Create a deep copy of this Syntax.
	 * @param cd clone data to use for copying
	 */
	public abstract Syntax copy(CopyData cd);

	public String getName() {
		return getOriginalDeclaration().getName();
	}

	public Optional<SubstitutionData> matchesParam(
		ModuleComponent paramModArg,
		ModulePart mp,
		Map<Syntax, Syntax> paramToArgSyntax,
		Map<Judgment, Judgment> paramToArgJudgment) {

		/*
		 * When applying a syntax as an argument, we want it to be the original syntax.
		 * Use getOriginalDeclaration() to get the SyntaxDeclaration object.
		 */

		SyntaxDeclaration arg = getOriginalDeclaration();

		if (!(paramModArg instanceof SyntaxDeclaration)) {
			// the wrong type of argument has been provided

			String argKind = this.getKind();
			String paramKind = paramModArg.getKind();

			// throw an exception

			ErrorHandler.modArgTypeMismatch(argKind, paramKind, mp);
			return Optional.empty();
		}

		// they are of the same type, so cast the parameter to a SyntaxDeclaration

		SyntaxDeclaration param = (SyntaxDeclaration) paramModArg;

		// now, we need to check if the two syntax declarations are compatible with eachother

		if (paramToArgSyntax.containsKey(this)) {
			// check if the parameter syntax is bound to the same argument syntax

			SyntaxDeclaration boundSyntax = paramToArgSyntax.get(this).getOriginalDeclaration();

			if (boundSyntax != param) {
				ErrorHandler.modArgMismatchSyntax(arg, param, boundSyntax, mp);
				return Optional.empty();
			}
		}

		// otherwise, bind the param to the arg in the map

		paramToArgSyntax.put(param, arg);

		// if the parameter is not abstract, there are other things that we have to verify

		if (!param.isAbstract()) {
			// This is a concrete syntax declaration, so there are productions to check
			// check that param and arg have the same number of productions

			List<Clause> paramProductions = param.getClauses();
			List<Clause> argProductions = arg.getClauses();

			if (paramProductions.size() != argProductions.size()) {
				ErrorHandler.modArgSyntaxWrongNumProductions(arg, param, mp);
				return Optional.empty();
			}

			// check that each pair of productions has the same structure

			for (int i = 0; i < paramProductions.size(); i++) {
				Clause paramClause = paramProductions.get(i);
				Clause argClause = argProductions.get(i);
				boolean sameStructure = Clause.checkClauseSameStructure(paramClause,
					argClause,
					paramToArgSyntax,
					paramToArgJudgment,
					new HashMap<String, String>(),
					mp
				);

				if (!sameStructure) return Optional.empty();
			}

		}

		// they match

		SubstitutionData sd = new SubstitutionData(param.getName(), arg.getName(), arg, param);
		return Optional.of(sd);
	}
	

	@Override
	public String getKind() {
		return "syntax";
	}

}