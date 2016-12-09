package parsers;

import java.util.HashMap;
import java.util.List;

import org.objectweb.asm.tree.MethodNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public abstract class MethodsParser implements Parser {

	protected MethodsParser otherparser;
	public MethodsParser(MethodsParser other){
		this.otherparser = other;
		
	}
	public abstract String parse(List methods);
	
	public String parseinfo(int Opcode, String text,List methods){
		StringBuilder result= new StringBuilder();
		for(MethodNode md:(List<MethodNode>)methods){
			if((md.access&Opcode)>0){
				result.append(text+" " + md.name +" "+ md.signature + "\n");
			}
		}
		if(otherparser !=  null)result.append(this.otherparser.parse(methods));
		return result.toString();
	}

}
