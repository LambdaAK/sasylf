package edu.cmu.cs.sasylf;

import java.util.Map;
import java.util.Optional;

import edu.cmu.cs.sasylf.ast.Judgment;
import edu.cmu.cs.sasylf.ast.ModulePart;
import edu.cmu.cs.sasylf.ast.Syntax;

public interface ModuleArgument {

  /**
   * Returns true if and only if this ModuleArgument object can be applied to
   * the given parameter.
   * 
   * Raises an exception and returns false if and only if the argument is not
   * applicable to the given parameter.
   * @param param
   * @return true if and only if this ModuleArgument object can be applied to, false otherwise
   */
  public Optional<SubstitutionData> matchesParam(
    ModuleArgument paramModArg,
		ModulePart mp,
		Map<Syntax, Syntax> paramToArgSyntax,
		Map<Judgment, Judgment> paramToArgJudgment
  );

  /**
   * Returns the name of this ModuleArgument object.
   * @return the name of this ModuleArgument object
   */
  public String getName();

  /**
   * Returns the kind of this ModuleArgument object.
   * Kind is either "syntax", "judgment", or "theorem".
   * @return the kind of this ModuleArgument object
   */
  public String getKind();

}