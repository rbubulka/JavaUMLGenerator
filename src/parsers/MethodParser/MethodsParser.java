package parsers.MethodParser;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.MethodNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import parsers.Parser;
import parsers.ClassParser.ClassParser;

public abstract class MethodsParser implements Parser {
	protected int opcode;
	protected String text;
	protected MethodsParser otherparser;
	public MethodsParser(MethodsParser other){
		this.otherparser = other;
	
		
	}
	public String parse(List methods, Set<String> relations,  List<HashMap<String, String>> classinfo){
		return this.parse(methods, relations, "", classinfo);
	}
	
	public  String parse(List methods, Set<String> relations,String name,  List<HashMap<String, String>> classinfo){
		StringBuilder result= new StringBuilder();
		for(MethodNode md:(List<MethodNode>)methods){
			if((md.access&opcode)>0){
				
				result.append(text+" " + md.name +" "+ md.desc + "\n");
			}
		}
		if(otherparser !=  null)result.append(this.otherparser.parse(methods, relations, name, classinfo));
		return result.toString();
	}
	public void setParser(Parser other){
		this.otherparser = (MethodsParser) other;
	}

}
