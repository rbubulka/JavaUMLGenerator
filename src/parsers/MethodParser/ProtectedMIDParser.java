package parsers.MethodParser;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ProtectedMIDParser extends MIDParser {

	public ProtectedMIDParser(MethodsParser other) {
		super(other);
		// TODO Auto-generated constructor stub
		this.opcode=Opcodes.ACC_PROTECTED;
		this.text="protected";
	}

}
