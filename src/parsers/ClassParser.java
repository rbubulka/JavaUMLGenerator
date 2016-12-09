package parsers;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

public abstract class ClassParser implements Parser {
	protected int opcode;
	protected String text;
	protected ClassParser otherparser;
	public ClassParser(ClassParser other){
		this.otherparser = other;
	}
	
	
	
	@Override
	public  String parse(List nodes){
		StringBuilder result = new StringBuilder();
		for(ClassNode cn : (List<ClassNode>) nodes ){
			if((cn.access&this.opcode)>0){
			result.append(text+" "+cn.name+" " +"Interface? "+String.valueOf((cn.access&Opcodes.ACC_INTERFACE)>0)+" Abstract? "+String.valueOf((cn.access&Opcodes.ACC_ABSTRACT)>0)+"\n");
			}
		}
		if(otherparser !=  null)this.otherparser.parse(nodes);
		return "";
	}



}
