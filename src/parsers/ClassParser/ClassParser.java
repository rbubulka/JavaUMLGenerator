package parsers.ClassParser;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import parsers.Parser;

public abstract class ClassParser implements Parser {
	protected int opcode;
	protected String text;
	protected ClassParser otherparser;
	public ClassParser(ClassParser other){
		this.otherparser = other;
	}
	
	@Override
	public  String parse(List nodes, Set<String> relations){
		StringBuilder result = new StringBuilder();
		for(ClassNode cn : (List<ClassNode>) nodes ){
			if((cn.access&this.opcode)>0){
			result.append(text+" "+cn.name+" " +"Interface? "+String.valueOf((cn.access&Opcodes.ACC_INTERFACE)>0)+" Abstract? "+String.valueOf((cn.access&Opcodes.ACC_ABSTRACT)>0)+"\n");
			}
		}
		if(otherparser !=  null)result.append(this.otherparser.parse(nodes, relations));
		return result.toString();
	}



}
