package parsers.MethodParser;

import java.util.List;

import org.objectweb.asm.tree.MethodNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ProtectedMethodsParser extends MethodsParser {
	
	
	public ProtectedMethodsParser(MethodsParser other){
		super(other);
		this.opcode=Opcodes.ACC_PROTECTED;
		this.text="protected";
	}
	
	
}
