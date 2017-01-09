package parsers.FieldParser;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ProtectedFieldDependencyParser extends FieldDependencyParser {

	public ProtectedFieldDependencyParser(FieldsParser other) {
		super(other);
		this.opcode=Opcodes.ACC_PROTECTED;
		this.text="protected";
	}

}
