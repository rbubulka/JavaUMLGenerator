package parsers.MethodParser;

import java.util.List;

import org.objectweb.asm.tree.MethodNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PrivateMethodsParser extends MethodsParser {
	

	public PrivateMethodsParser(MethodsParser other){
		super(other);
		this.opcode=Opcodes.ACC_PRIVATE;
		this.text="private";
	}
	
	
}
