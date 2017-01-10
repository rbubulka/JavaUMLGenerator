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
	 public  String parse(List fields, Set<String> relations,String classname){
		StringBuilder result = new StringBuilder();
		for(FieldNode fn : (List<FieldNode>) fields ){
			if ((fn.access & opcode) > 0) {
				String hasNumber = "1";
				if (fn.desc.trim().charAt(0) == '[') {
					hasNumber = "*";
				}

				relations.add(classname + " 1 hasa " + hasNumber + " " + splitclassname(fn.desc));
			}
		}
		if(otherparser !=  null)result.append(this.otherparser.parse(fields, relations,classname));
		return result.toString();
		
		
		
	}
	public String splitclassname(String in) {
		String[] result = in.split("/");
		return result[result.length - 1].replaceAll("//W", "").replaceAll(";", "").replaceAll("\\[", "");
	}

}
