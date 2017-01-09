package parsers.FieldParser;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.FieldNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import parsers.Parser;

public abstract class FieldsParser implements Parser {
	protected int opcode;
	protected String text;
	protected FieldsParser otherparser;
	public FieldsParser(FieldsParser other){
		this.otherparser = other;
	}
	
	public String parse(List fields, Set<String> relations){
		return this.parse(fields, relations, "");
	}
	
	public  String parse(List fields, Set<String> relations,String classname){
		StringBuilder result = new StringBuilder();
		for(FieldNode fn : (List<FieldNode>) fields ){
			if((fn.access&opcode)>0){
			result.append(text+" "+fn.desc +" "+ fn.name+"\n");
			}
		}
		if(otherparser !=  null)result.append(this.otherparser.parse(fields, relations, classname));
		return result.toString();
		

	}
	public int getOpcode(){
		return opcode;
	}
	
	
}
