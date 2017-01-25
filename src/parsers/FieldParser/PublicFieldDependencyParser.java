package parsers.FieldParser;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PublicFieldDependencyParser extends FieldDependencyParser {

	public PublicFieldDependencyParser(FieldsParser other) {
		super(other);
		this.opcode=Opcodes.ACC_PUBLIC;
		this.text="public";
		
	}

}
