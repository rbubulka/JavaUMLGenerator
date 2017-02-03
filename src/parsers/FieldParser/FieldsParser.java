package parsers.FieldParser;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.FieldNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import parsers.Parser;
import parsers.ClassParser.ClassParser;

public abstract class FieldsParser implements Parser {
	protected int opcode;
	protected String text;
	protected FieldsParser otherparser;
	public FieldsParser(FieldsParser other){
		this.otherparser = other;
	}
	
	public String parse(List fields, Set<String> relations,  List<HashMap<String, String>> classinfo){
		return this.parse(fields, relations, "", classinfo);
	}
	
	public  String parse(List fields, Set<String> relations,String classname,  List<HashMap<String, String>> classinfo){
		StringBuilder result = new StringBuilder();
		for(FieldNode fn : (List<FieldNode>) fields ){
			if((fn.access&opcode)>0){
			result.append(text+" "+fn.desc +" "+ fn.name+"\n");
			}
		}
		if(otherparser !=  null)result.append(this.otherparser.parse(fields, relations, classname, classinfo));
		return result.toString();
		

	}
	public int getOpcode(){
		return opcode;
	}
	public void setParser(Parser other){
		this.otherparser = (FieldsParser) other;
	}
	
}
