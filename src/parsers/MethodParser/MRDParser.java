package parsers.MethodParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.MethodNode;

public abstract class MRDParser extends MethodsParser {

	public MRDParser(MethodsParser other) {
		super(other);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String parse(List methods, Set<String> relations, String name) {
		StringBuilder result = new StringBuilder();
		for (MethodNode md : (List<MethodNode>) methods) {
			if ((md.access & opcode) > 0) {
				String start = md.desc;
//				System.out.println(md.desc);
				if (start != null) {
					if(start.contains(":")){
						start=start.substring(start.indexOf(">")+1);
					}
					String[] startarray = start.split("\\)");
					String arg = startarray[0].replaceAll("\\(", "");
					arg = arg.replace(";>;", ">;");
					String returtype = startarray[1];
					if(returtype.contains(";")){returtype = returtype.substring(0, returtype.lastIndexOf(";")) + returtype.substring(returtype.lastIndexOf(";") + 1);}
					if (arg.contains(";")) {
						arg = arg.substring(0, arg.lastIndexOf(";")) + arg.substring(arg.lastIndexOf(";") + 1);
					}
					String[] argls = arg.split(";");
					List<String> temp = new ArrayList<>();
					for (String s : argls) {
						temp.add(s);
					}
					temp.add(returtype);
					for (String s : temp) {
						if (s != null && s.contains("<")) {
							this.addCollectionDependency(relations, name, s);
						} else {
							this.addDependency(relations, name, s, false);
						}

					}
				}
			}
		}
		if (otherparser != null)
			result.append(this.otherparser.parse(methods, relations, name));
		return result.toString();
	}

	public String splitclassname(String in) {
		String[] result = in.split("/");
		return result[result.length - 1].replaceAll("//W", "").replaceAll(";", "").replaceAll("\\[", "");
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
		if (isCollection)
			num = "* ";
			relations.add(classname + " 1 usea " + num +type.replaceAll("\\[", ""));
		
	}
}
