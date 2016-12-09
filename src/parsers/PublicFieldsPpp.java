package parsers;

import java.util.List;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PublicFieldsPpp extends FieldsPpp {
	public PublicFieldsPpp(FieldsPpp other) {
		super(other);
		
	}

	@Override
	public String parse(List fields) {
		StringBuilder result = new StringBuilder();
		for(FieldNode fn : (List<FieldNode>) fields ){
			if((fn.access&Opcodes.ACC_PUBLIC)>0){
			result.append("public "+fn.desc + fn.name+"\n");
			}
		}
		if(otherPpp !=  null)this.otherPpp.parse(fields);
		return result.toString();
	}
}
