package edu.cmu.cs.sasylf.util;

public enum Errors {
	// The following errors are exclusively generated during parsing
	PARSE_EXPECTED_LBRACE ("expected '{', not "),
	PARSE_EXPECTED_RBRACE ("expected '}', not "),
	PARSE_EXPECTED_COLONEQ ("expected ':=', not "),
	PARSE_EXPECTED_INVERSION_OF ("expected 'inversion of' not "),
	PARSE_EXPECTED_INVERSION_ON ("inversion on what?"),
	PARSE_EXPECTED_INDUCTION_HYPO_ON ("induction hypothesis on what?"),
	PARSE_EXPECTED_CASE ("expected case here"),
	PARSE_EXPECTED_DERIVATION ("expected at least one derivation here"),
	LEXICAL_ERROR ("Lexical error: "),
	REQUIRES_IN_MODULE ("'requires' legal only for explicit modules"),
	EXTENDS_IN_MODULE ("'extends' legal only for explicit modules"),
	EXTENDS_UNIMPLEMENTED ("module 'extends' not implemented yet"),
	PROVIDES_IN_MODULE ("'provides' legal only for explicit modules"),
	PROVIDES_NOT_IN_MODULE ("'provides' expected for explicit modules"),
	ABSTRACT_NOT_PERMITTED_HERE ("'abstract' not allowed in main part of proof file"),
	SYNTAX_DUPLICATE ("syntax nonterminal duplicate"),
	JUDGMENT_ABSTRACT ("abstract judgment cannot have rules"),
	RULE_NAME_EXPECTED ("Missing a rule name (must be on the same line as the ---)"),
	WRONG_END ("expected "),
	PARSE_ERROR ("Parse error: "), // Place last for parser errors
	// The following errors are generated *after* parsing
	MODULE_NOT_FOUND ("module not found: "),
	MODULE_ILLFORMED ("module has errors: "),
	MODULE_CYCLE("Cyclic module reference: "),
	MODULE_PARAMETERS ("module parameters not yet supported"),
	MODULE_ABSTRACT ("modules with parameters cannot yet be used"),
	QUAL_NOT_FOUND ("cannot find something with this name exported from the module"),
	QUAL_NOT_AVAILABLE ("SASyLF does not know how to use qualification to access "),
	ANDOR_AMBIGUOUS("Ambiguous use of 'and' and 'or'.  Use parentheses."),
	ANDOR_CONTEXT("Cannot join judgments with different assumes: "),
	ANDOR_NOSYNTAX("Cannot join syntax with and/or, only judgments"),
	ANDOR_PREFIX("All joined clauses must share the same prefix in their assumptions"),
	NOT_UNSUPPORTED("'not' judgments are not supported"),
	NOT_ASSUMPTION("A 'not' judgment cannot have an assumption"),
	CLAUSE_PARSE("Cannot parse any syntactic case or judgment"),
	CLAUSE_AMBIGUOUS("Multiple syntactic case(s) or judgment(s)"),
	CLAUSE_DEF_PAREN("judgment/syntax must not include parenthesized expressions"),
	RENAME_MISMATCH("Renamed clause has different contents"),
	RENAME_NO_RULES ("Renaming a judgment should not include renaming of rules"),
	RENAME_ASSUME_MISMATCH ("Renamed judgment must have the same 'assumes' if any"),
	ABSTRACT_REQUIRED("A required judgment without rules should be declared abstract"),
	BAD_FILE_NAME_SUFFIX ("Proof file name must end in '.slf'"),
	BAD_FILE_NAME("Proof file for module must be a legal identifier"),
	WRONG_PACKAGE ("wrong package"),
	WRONG_MODULE_NAME ("wrong module name"),
	SYNTAX_TERMINAL("a syntax nonterminal should not also be declared as a terminal"),
	SYNTAX_REDECLARED ("nonterminal already declared"),
	SYNTAX_UNPRODUCTIVE ("this syntax is unproductive.  You need a production that can actually generate a string."),
	SYNTAX_VARIABLE_MISSING ("no syntax found for variable.  Did you forget to make it a case of a BNF syntax definition?"),
	SYNTAX_VARIABLE_TWICE ("the same variable may not appear in multiple syntax definitions"),
	SYNTAX_SUBORDINATION_ERROR ("there is no way to use this variable in the syntax: "),
	DERIVATION_UNPROVED		("derivation unproved"),
	DERIVATION_SYNTAX ("cannot prove syntax, only judgments"),
	RULE_NOT_FOUND			("cannot find a rule named "),
	THEOREM_NOT_FOUND  ("cannot find lemma/theorem named "),
	RULE_NOT_THEOREM  ("expected theorem/lemma, not rule "),
	THEOREM_EXPECTED	("expected theorem but found "),
	THEOREM_NOT_RULE  ("expected rule, not theorem/lemma "),
	THEOREM_KIND_WRONG ("not a "),
	THEOREM_KIND_MISSING ("missing keyword "),
	THEOREM_ABSTRACT ("abstract theorem/lemma should not include proof"),
	THEOREM_MULTIPLE_CONTEXT ("SASyLF cannot handle theorem/lemma with multiple contexts"),
	FORALL_NOT_SYNTAX ("Could not find syntax nonterminal "),
	DUPLICATE_JUDGMENT("declaration uses name name as previous judgment"),
	RULE_LIKE_REDECLARED ("declaration uses same name as previous rule/theorem"),
	RULE_BAD  ("rule/theorem has a bad interface: "),
	RULE_PREMISE_NUMBER		("wrong number of premises for "),
	BAD_RULE_APPLICATION,
	RULE_CASE_TOO_GENERAL ("The given pattern is overly general, should restrict "),
	RULE_CASE_USES_OLD ("The given pattern uses variables already substituted"), // unused
	BAD_SYNTAX_BINDING("Only variables are permitted inside '[]' in a syntax or judgment definition"),
	TOO_MANY_VARIABLES ("only one variable per nonterminal is permitted"),
	VARIABLE_HAS_NO_CONTEXT ("the variable for this nonterminal is never bound in a context"),
	VARIABLE_HAS_MULTIPLE_CONTEXTS ("the variable for this nonterminal is illegaly bound in multiple contexts"),
	BINDER_MUST_BE_NT("binding '[...]' can only be done within a nonterminal"),
	NO_DERIVATION			("must provide a derivation"),
	INDUCTION_IMPLICIT ("implicit induction deprecated; please use explicit induction"), // warning
	INDUCTION_MISSING ("'induction hypothesis' requires explicit proof by induction"),
	INDUCTION_REPEAT ("can't nest induction analysis inside existing induction"),
	INDUCTION_NESTED ("Induction can only be declared at top level of a proof."),
	INDUCTION_PARSE ("SASyLF cannot understand this induction scheme"),
	INDUCTION_EMPTY ("empty induction scheme encountered"),
	INDUCTION_SHORT ("induction scheme cut short"),
	INDUCTION_MISMATCH ("induction schema does not match"),
	INDUCTION_PERMUTATION ("could find no permutation that matched"),
	INDUCTION_NOT_INPUT,
	NOT_SUBDERIVATION		("argument to induction hypothesis must be a subderivation of theorem input"),
	DERIVATION_MISMATCH,
	OTHER_CONTEXT_NEEDED ("requires a different context (perhaps using weakening or exchange): "),
	OTHER_CONTEXT_JUSTIFIED ("Claimed result has the wrong context, expected "),
	OTHER_JUSTIFIED   ("Claimed fact is not the one justified"),
	WRONG_RESULT			("the last derivation in a sequence does not match the statement to be proven"),
	RULE_PREMISE_MISMATCH ("rule/theorem expects type of premise #"),
	RULE_CONCLUSION_MISMATCH ("rule/theorem can't produce any instance of the claimed judgment: "),
	RULE_CONTEXT_INCONSISTENT ("contexts used in application of rule/theorem are inconsistent"),
	MISSING_CASE			("must provide a case for "),
	TOO_MANY_CASES	        ("Inversion cannot be used since one must provide a case for "),
	EXTRA_CASE				("case is redundant or unnecessary"),
	PARTIAL_CASE_ANALYSIS ("'do case analysis' only handles special cases, you need a full proof still"),
	RULE_EXPECTED ("expected rule but found "),
	RULE_WRONG_JUDGMENT ("rule is not of the expected judgment "),
	RULE_NO_QUALIFICATION ("rule names in cases don't need qualification"),
	MISSING_ASSUMES			("found a use of a context nonterminal but no assumes clause"),
	ILLEGAL_ASSUMES    ("assumed entity is not a context nonterminal"),
	ILLEGAL_ASSUMES_CLAUSE ("assumed clause is not a context"),
	ASSUMES_FOR_SYNTAX ("'assumes' can only be used with syntax"),
	EXTRANEOUS_ASSUMES ("found no use of the context nonterminal "),
	ASSUMES_BRANCH("An assumption case must not have more than one nested list of assumptions"),
	ASSUMES_MULTI_USE("Multiple uses of the context form not supported "),
	ASSUMES_MISSING_VAR ("assumptions without variables not supported"), // can't generate
	ASSUMES_MULTI_VAR("Can't handle more than one variable in assumption rule"),
	ASSUMES_CONTEXT_RESTRICT ("Assumption rule must use context form unchanged"),
	ASSUMES_DUPLICATE("Assumption rule has duplicate use "),
	ASSUMES_UNDEFINED("Assumption rule doesn't give a way to determine "),
	ASSUMES_UNUSED("Assumption rule ignores element from context "), // Unused
	SYNTAX_CASE_FOR_DERIVATION ("when case-analyzing a derivation, must use rule cases, not syntax cases"),
	SYNTAX_CASE_FOR_DISJUNCTION ("when case-analyzing a disjunction, must use 'or' cases, not syntax cases"),
	UNBOUND_VAR_CASE,
	VAR_REBOUND ("this variable already has been declared here: "),
	VAR_UNBOUND ("this variable has not been declared here: "),
	DERIVATION_NOT_FOUND,
	NONTERMINAL_CASE,
	CASE_WRONG_TYPE ("case is for a different type: "),
	CASE_NONTERM ("case doesn't break down the subject"),
	CASE_SINGLE ("case can only uncover one new variable"),
	INVALID_CASE,
	CASE_REDUNDANT ("case is redundant (already handled)"),
	CASE_UNNECESSARY ("case is unnecessary (subject cannot be produced with this rule)"),
	CASE_STRICT_RESTRICTS ("case is overly strict; perhaps because it constrains the following variable: "),
	CASE_STRICT_NEED_VAR ("case is overly strict; perhaps replace the following with a fresh variable: "),
	CASE_STRICT_DUP_VAR ("case is overly strict; perhaps it uses a new variable multiple times"),
	CASE_STRICT_NEED_DEPEND ("case is overly strict; perhaps replace the following with something that could depend on all the variables in the context: "),
	CASE_MISMATCH ("case doesn't match subject, was unable to unify "),
	CASE_OCCUR ("case doesn't match subject; perhaps you reused this variable instead of a new one? "),
	CASE_INCOMPLETE("case leads to incomplete unification; perhaps use a variable instead of "),
	EXPECTED_VARIABLE   ("only variables can be bound, not syntax"),
	BINDING_INCONSISTENT ("meta-variable must have consistent numbers and types of bindings throughout a rule or branch of a theorem: "),
	MISSING_ASSUMPTION_RULE,
	UNBOUND_VAR_USE,
	DUPLICATE_CASE			("already provided a case of this syntactic form"),
	RULE_CASE_SYNTAX ("when case-analyzing a non-terminal, must use syntax cases, not rule cases"),
	NOT_EQUIVALENT,
	JUDGMENT_EXPECTED ("rule premises and conclusions must be in judgment form, not just syntax"),
	SYNTAX_EXPECTED ("cannot create judgment by syntax"),
	WRONG_JUDGMENT,
	INVERSION_WRONG_RULE ("wrong rule named in inversion, should be "),
	EMPTY_CONCLUSION_CONTEXT ("conclusion of judgment that assumes a context cannot have an empty context"), 
	VAR_CONCLUSION_CONTEXT ("conclusion of judgment that assumes a context cannot have a context with variables"),
	PREMISE_CONTEXT_MISMATCH ("premise cannot use a context variable not present in conclusion"),
	FORWARD_REFERENCE		("mutual induction is unchecked"),
	UNDECLARED_NONTERMINAL,
	VAR_STRUCTURE_KNOWN,
	CASE_SUBJECT_MULTIPLE ("case analysis can take only one argument"),
	CASE_SUBJECT_UNKNOWN ("subject not known, perhaps you intended one of "),
	CASE_SUBJECT_ABSTRACT ("subject is abstract; no case analysis permitted"),
	CASE_SUBJECT_NOT("case analysis is not allowed on a 'not' judgment"),
	CASE_SUBJECT_CONSTRUCTED("case analysis is not allowed on something just put together"),
	CASE_SUBJECT_VAR_UNUSED ("variable in assumption context is unused, please remove"),
	CASE_SUBJECT_ROOT_INTERNAL ("the assumption context of subject must be rooted in the theorem context "),
	CASE_ASSUMPTION_IMPOSSIBLE("assumption rule cannot apply since variables cannot occur in "),
	CASE_ASSUMPTION_OLD ("case matching an assumption in the context needs a new context name"),
	CASE_ASSUMPTION_NOT_VAR ("a new assumption can only be used with variable cases"),
	UNKNOWN_CONTEXT ("unknown context name "),
	REUSED_CONTEXT ("may not reuse context name "),
	THEOREM_INCONSISTENT_CONTEXTS ("theorem/lemma has inconsistent contexts"),
	CASE_CONTEXT_ADDED ("case subject is rooted in the empty context, should not assume "),
	CASE_CONTEXT_CHANGED ("cannot change context in case analysis"),
	CASE_ASSUMPTION_SINGLE ("the assumption rule only handles one level of context"),
	CASE_CONCLUSION_UNSOUND ("conclusion in pattern matching of assumption rule cannot be used\nUse '_' instead of "),
	CASE_CONTEXT_INCONSISTENT ("all derivations in the rule cases must use the same bindings and context"),
	CASE_CONTEXT_MAYBE_KNOWN ("Perhaps should have used existing context"), // Warning
	CONTEXT_DISCARDED ("context discarded"),
	CONTEXT_DISCARDED_APPL ("passing argument implicitly discards its context "),
	WRONG_SUBSTITUTION_ARGUMENTS ("expected 2 arguments to a substitution justification: the judgment being substituted, and the judgment being substituted into"),
	SUBSTITUTION_ARGUMENT ("argument of substitution must be a judgment instance "),
	SUBSTITUTION_NO_PREFIX ("context of first argument to substitution must have as prefix that of second"),
	SUBSTITUTION_NO_HOLE ("target of substitution does not have more assumptions than argument"),
	SUBSTITUTION_CONSTRAIN ("substitution would constrain variables, including "),
	SUBSTITUTION_FAILED ("context cannot be filled with given clause"),
	WRONG_WEAKENING_ARGUMENTS ("expected 1 argument as a weakening justification: the stronger judgment being weakened"),
	WEAKENING_SYNTAX ("do not weaken syntax using 'weakening'\nWeakening is implicit for syntax"),
	BAD_WEAKENING("result cannot be produced by adding unused variable bindings"),
	WEAKENING_NOP ("result does not require any weakening"),
	RELAX_UNKNOWN("no known way to weaken "),
	RELAX_WRONG("to weaken the context, one needs to use the same variable and assumptions"),
	EXCHANGE_SYNTAX("exchange argument should be a judgment, not syntax"),
	WRONG_EXCHANGE_ARGUMENTS("expected 1 arguments an an exchange justification on which to permute bindings"),
	BAD_EXCHANGE("result cannot be produced by permuting variable bindings"),
	INVERSION_SYNTAX_NO_RULE("inversion on syntax doesn't use rules; just write 'inversion on'"),
	INVERSION_SYNTAX_NO_RESULTS("cannot prove anything with inversion on syntax; just write 'use inversion on'"),
	INVERSION_BAD_RULE ("rule cannot be used for inversion until it is fixed"),
	INVERSION_EMPTY ("The subject of inversion is actually not possible.  Suggest using 'by contradiction on' instead of inversion."),
	INVERSION_RESULT_SIZE ("inversion results in a different number of premises: "),
	INVERSION_NOT_FOUND("did not find claimed judgment in inversion of rule"),
	WEIRD_ADAPT_ERROR("internal error in adapt"),
	EXISTS_SYNTAX("'exists' of theorem must be a judgment, not syntax"),
	MUTUAL_INDUCTION_NO_INDUCTION("each theorem/lemma in a mutual induction group needs its own inductive argument"),
	MUTUAL_NOT_SUBDERIVATION   ("argument to mutual induction must be a subderivation of theorem induction"),
	MUTUAL_NOT_EARLIER ("if inductive argument is unchanged, the mutual induction must be to an earlier theorem"),
	ASSUMED_ASSUMES("an 'assumes' clause should have been given for this theorem/lemma"),
	OR_SYNTAX("can only 'or' with judgments, not syntax"),
	OR_CASE_NOT_APPLICABLE("derivation under consideration is not a disjunction"),
	RENAME_JUDGMENT ("Cannot rename as a judgment "),
	RENAME_SYNTAX ("Cannot rename as syntax "),
	SUGAR_UNKNOWN ("SASyLF cannot figure out what syntax is being defined"),
	SUGAR_JUDGMENT ("sugar can only be used for syntax, not judgments"),
	SUGAR_MULTIPLE_USES ("a nonterminal occurs more than once"),
	SUGAR_MULTIPLE_VARS ("a variable occurs more than once"),
	SUGAR_BINDING_ARG ("an argument in [...] in syntax must be a variable"),
	SUGAR_NO_USES ("nonterminal doesn't occur in the syntax"),
	SUGAR_UNUSED ("no indication what to do with nonterminal"),
	SUGAR_SYNTAX_UNKNOWN ("unknown syntax"), // infeasible
	SUGAR_ABSTRACT ("cannot define abstract syntactic sugar on concrete syntax"), // infeasable
	NEVER_RIGID ("Using rule is likely to lead to incomplete unification because the following variables are never used outside of a binding: "), // Warning
	UNIFICATION_INCOMPLETE("Unification incomplete for case "),
	SOLVE_FAILED ("Unable to find proof"),
	SOLVE_UNRELIABLE ("proof by solve is not reliable"), // Warning
	DERIVATION_NAME_REUSED ("reusing derivation identifier"), // Warning
	WHERE_ASSUMPTION ("SASyLF cannot (yet) produce or verify where clauses for this case"),
	WHERE_MISSING ("missing 'where' clause(s)"),
	WHERE_MISSING_EXT ("variable(s) implicitly bound"),
	WHERE_WRONG ("right-hand side of 'where' clause wrong"),
	WHERE_NONE_NEEDED ("no where clauses are needed here"),
	WHERE_LEFT_BAD ("the left side of this 'where' clause does not describe a meta-variable to substitute"),
	WHERE_NOT_NEEDED ("no substitution needed"),
	WHERE_DUPLICATE ("substitution already given"),
	WHERE_ARGUMENTS_NEEDED ("the left-hand side needs to indicate holes as in "),
	WHERE_ARGUMENTS_WRONG ("the left-hand side has the wrong number f arguments, should be "),
	WHERE_ARGUMENT_NOTVAR ("the argument(s) in the left-hand side [...]'s must be variable(s)"),
	WHERE_TOO_GENERAL ("replacement too general, perhaps these variables should be replaced with something specific: "),
	WHERE_TOO_SPECIFIC_RESTRICTS ("replacement too specific, imposes constraint on free variable "),
	WHERE_NEED_FRESH ("replacement too specific, probably needs a fresh variable instead of "),
	WHERE_NEW_OVERUSED ("new variable should only be used once: "),
	WHERE_REBOUND ("variable bound more than once in where clause"),
	WHERE_OCCUR ("replacement for meta-variable should not include itself"),
	INTERNAL_ERROR("SASyLF Internal Error")
	;

	Errors() {
		text = "";
	}

	Errors(String text) {
		this.text = text;
	}

	private String text;

	public String getText() {
		return text;
	}
}
