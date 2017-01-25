package parsers.FieldParser;

import java.util.List;

import org.objectweb.asm.tree.FieldNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ProtectedFieldsParser extends FieldsParser {
	
	public ProtectedFieldsParser(FieldsParser other){
		super(other);
		this.opcode=Opcodes.ACC_PROTECTED;
		this.text="protected";
	}
	
}
