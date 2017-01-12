package parsers.FieldParser;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.FieldNode;

import com.sun.javafx.scene.traversal.Hueristic2D;

public abstract class FieldDependencyParser extends FieldsParser {

	public FieldDependencyParser(FieldsParser other) {
		super(other);
	}

	@Override
	public String parse(List fields, Set<String> relations, String classname) {
		StringBuilder result = new StringBuilder();
		for (FieldNode fn : (List<FieldNode>) fields) {
			if ((fn.access & opcode) > 0) {
				if (fn.signature != null && fn.signature.contains("<")) {
					addCollectionDependency(relations, classname, fn.signature);
				} else {
					addDependency(relations, classname, fn.desc, false);
				}
			}
		}
		if (otherparser != null)
			result.append(this.otherparser.parse(fields, relations, classname));
		return result.toString();

	}

	private void addCollectionDependency(Set<String> relations, String classname, String local) {
		if (!local.contains("<")) {
			addDependency(relations, classname, local, true);
		} else {
			String collectionType = local.substring(local.indexOf("<") + 1, local.lastIndexOf(">"));
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
		if (isCollection) {
			num = "* ";
		}
		relations.add(classname + " 1 hasa " + num + type.trim().replaceAll(";", ""));
	}

}
