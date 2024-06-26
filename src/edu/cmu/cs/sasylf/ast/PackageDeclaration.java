package edu.cmu.cs.sasylf.ast;

import java.io.PrintWriter;
import java.util.Arrays;

import edu.cmu.cs.sasylf.CloneData;
import edu.cmu.cs.sasylf.SubstitutionData;
import edu.cmu.cs.sasylf.util.ErrorHandler;
import edu.cmu.cs.sasylf.util.Errors;
import edu.cmu.cs.sasylf.util.Location;
import edu.cmu.cs.sasylf.util.Span;

public class PackageDeclaration extends Node {
	private QualName name;

	public PackageDeclaration(Location l, QualName q, Location endL) {
		super(l,endL);
		name = q;
		if (q != null) q.resolveAsPackage();
	}

	public void typecheck(String[] expected) {
		final String[] pieces = getPieces();
		if (Arrays.equals(pieces, expected)) return;
		if (pieces.length == 0) {
			ErrorHandler.warning(Errors.WRONG_PACKAGE, this, "\npackage " + toString(expected) + ";");
		} else if (expected.length == 0) {
			ErrorHandler.warning(Errors.WRONG_PACKAGE, this, "package " + toString(pieces) + ";\n");
		} else {
			ErrorHandler.warning(Errors.WRONG_PACKAGE, this,  toString(pieces) + "\n" + toString(expected)); 
		}
	}

	@Override
	public void prettyPrint(PrintWriter out) {
		final String[] pieces = getPieces();
		if (pieces.length == 0) return;
		out.print("package ");
		out.print(toString(pieces));
		out.println(";");
	}

	private String[] EMPTY_PACKAGE = new String[0];
	
	public String[] getPieces() {
		if (name == null) return EMPTY_PACKAGE;
		final String[] pieces = name.resolveAsPackage();
		return pieces;
	}

	public static String toString(String[] pieces) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i < pieces.length; ++i) {
			if (i > 0) sb.append(".");
			sb.append(pieces[i]);
		}
		return sb.toString();
	}

	public PackageDeclaration copy(CloneData cd) {
		if (cd.containsCloneFor(this)) return (PackageDeclaration) cd.getCloneFor(this);

		PackageDeclaration clone;

		try {
			clone = (PackageDeclaration) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Clone not supported in PackageDeclaration");
			System.exit(1);
			return null;
		}

		cd.addCloneFor(this, clone);

		clone.name = name.copy(cd);

		return clone;
	}

	public void substitute(SubstitutionData sd) {
		if (sd.didSubstituteFor(this)) return;
		sd.setSubstitutedFor(this);
	}
}
