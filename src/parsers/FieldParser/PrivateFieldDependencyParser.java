package parsers.FieldParser;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PrivateFieldDependencyParser extends FieldDependencyParser {

	public PrivateFieldDependencyParser(FieldsParser other) {
		super(other);
		this.opcode=Opcodes.ACC_PRIVATE;
		this.text="private";
	}

}
