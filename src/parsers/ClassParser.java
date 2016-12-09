package parsers;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

public abstract class ClassParser implements Parser {

	protected ClassParser otherparser;
	public ClassParser(ClassParser other){
		this.otherparser = other;
	}
	
	
	
	@Override
	public abstract String parse(List nodes) ;

	@Override
	public String parseinfo(int Opcode, String text, List node) {
		StringBuilder result = new StringBuilder();
		for(ClassNode cn : (List<ClassNode>) node ){
			if((cn.access&Opcode)>0){
			result.append(text+" "+cn.name+" " +"Interface? "+String.valueOf((cn.access&Opcodes.ACC_INTERFACE)>0)+" Abstract? "+String.valueOf((cn.access&Opcodes.ACC_ABSTRACT)>0)+"\n");
			}
		}
		if(otherparser !=  null)this.otherparser.parse(node);
		return "";
	}

}
