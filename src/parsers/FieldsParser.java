package parsers;

import java.util.List;

import org.objectweb.asm.tree.FieldNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public abstract class FieldsParser implements Parser {
	
	protected FieldsParser otherPpp;
	public FieldsParser(FieldsParser other){
		this.otherPpp = other;
	}
	
	
	public abstract String parse(List fields) ;
	
	
	public String parseinfo(int Opcode, String text,List fields) {
		StringBuilder result = new StringBuilder();
		for(FieldNode fn : (List<FieldNode>) fields ){
			if((fn.access&Opcode)>0){
			result.append(text+" "+fn.desc + fn.name+"\n");
			}
		}
		if(otherPpp !=  null)this.otherPpp.parse(fields);
		return result.toString();
		

	}
}
