package parsers.MethodParser;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.MethodNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import parsers.Parser;

public abstract class MethodsParser implements Parser {
	protected int opcode;
	protected String text;
	protected MethodsParser otherparser;
	public MethodsParser(MethodsParser other){
		this.otherparser = other;
	
		
	}
	public String parse(List methods, Set<String> relations){
		return this.parse(methods, relations, "");
	}
	
	public  String parse(List methods, Set<String> relations,String name){
		StringBuilder result= new StringBuilder();
		for(MethodNode md:(List<MethodNode>)methods){
			if((md.access&opcode)>0){
				
				result.append(text+" " + md.name +" "+ md.desc + "\n");
			}
		}
		if(otherparser !=  null)result.append(this.otherparser.parse(methods, relations, name));
		return result.toString();
	}


}
