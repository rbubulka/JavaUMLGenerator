package parsers;

import java.util.List;

import org.objectweb.asm.tree.FieldNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PrivateFieldsPpp extends FieldsPpp {
	
	
	
	public PrivateFieldsPpp(FieldsPpp other) {
		super(other);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String parse(List fields) {
		StringBuilder result = new StringBuilder();
		for(FieldNode fn : (List<FieldNode>) fields ){
			if((fn.access&Opcodes.ACC_PRIVATE)>0){
			result.append("private "+fn.desc + fn.name+"\n");
			}
		}
		if(otherPpp !=  null)this.otherPpp.parse(fields);
		return result.toString();

	}
}
