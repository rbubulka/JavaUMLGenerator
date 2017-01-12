package parsers.MethodParser;

import java.util.ArrayList;
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
			List<LocalVariableNode> variables = (List<LocalVariableNode>)mn.localVariables;
//			int lastIndex = 0;
//			List<LocalVariableNode> toRemove = new ArrayList<LocalVariableNode>();
//			for(int i = 0; i < variables.size(); i++){
//				if(variables.get(i).index < lastIndex){
//					int diff =  lastIndex - variables.get(i).index;
//					for(int j = 0; j < i; j++) toRemove.add(variables.get(i-(j+1));
//				}
//				lastIndex = variables.get(i).index;
//			}			
			
			if (mn.localVariables != null) {
				for (LocalVariableNode local : variables)
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
			//System.out.println(local);
			String collectionType = "";
			if(local.contains(">")){ collectionType = local.substring(local.indexOf("<")+1, local.lastIndexOf(">"));}
			else{
				collectionType = local.substring(local.indexOf("<")+1);
			}
			
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
		if (isCollection) num = "* ";
		relations.add(classname + " 1 usea " + num +type.trim().replaceAll("\\[", ""));
	}

}
