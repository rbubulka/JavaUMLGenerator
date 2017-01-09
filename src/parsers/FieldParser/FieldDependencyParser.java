package parsers.FieldParser;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.FieldNode;

public abstract class FieldDependencyParser extends FieldsParser {

	public FieldDependencyParser(FieldsParser other) {
		super(other);
	}
	@Override
	 public  String parse(List fields, Set<String> relations,String classname){
		StringBuilder result = new StringBuilder();
		for(FieldNode fn : (List<FieldNode>) fields ){
			if((fn.access&opcode)>0){
		relations.add(classname+" hasa "+fn.desc);
			}
		}
		if(otherparser !=  null)result.append(this.otherparser.parse(fields, relations));
		return result.toString();
		
		
		
	}

}
