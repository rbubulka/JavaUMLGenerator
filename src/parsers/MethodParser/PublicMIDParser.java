package parsers.MethodParser;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PublicMIDParser extends MIDParser {

	public PublicMIDParser(MethodsParser other) {
		super(other);
		// TODO Auto-generated constructor stub
		this.opcode=Opcodes.ACC_PUBLIC;
		this.text="public";
	}

}
