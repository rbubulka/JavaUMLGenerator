package parsers.MethodParser;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

public abstract class MIDParser extends MethodsParser {

	public MIDParser(MethodsParser other) {
		super(other);
	}

	@Override
	public String parse(List methods, Set<String> relations, String classname) {
		StringBuilder result = new StringBuilder();
		for (MethodNode mn : (List<MethodNode>) methods) {
			if (mn.localVariables != null) {
				for (LocalVariableNode local : (List<LocalVariableNode>) mn.localVariables)
					if (local != null && local.signature != null) {
						if (local.signature.contains("<")) {
							addCollectionDependency(relations, classname, local.signature);
						} else {
							addDependency(relations, classname, local.signature, false);
						}
					}
			}
		}
		if (otherparser != null)
			result.append(this.otherparser.parse(methods, relations, classname));
		return result.toString();
	}

	private void addCollectionDependency(Set<String> relations, String classname, String local) {
		if (!local.contains("<")) {
			addDependency(relations, classname, local, true);
		} else {
			String collectionType = local.substring(local.indexOf("<")+1, local.lastIndexOf(">"));
			String[] types = collectionType.split(";");
			for (String type : types) {
				if (type.contains("<")) {
					addCollectionDependency(relations, classname, type);
				} else {
					addDependency(relations, classname, type, true);
				}
			}
		}
	}

	private void addDependency(Set<String> relations, String classname, String type, boolean isCollection) {
		String num = "1 ";
		if (isCollection)
			num = "* ";
		String[] signature = type.split("/");
		String dname = signature[signature.length - 1].replaceAll(";", "");
		relations.add(classname + " 1 usea " + num + dname);
	}

}
