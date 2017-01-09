package parsers.MethodParser;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PrivateMIDParser extends MIDParser {

	public PrivateMIDParser(MethodsParser other) {
		super(other);
		// TODO Auto-generated constructor stub
		this.opcode=Opcodes.ACC_PRIVATE;
		this.text="private";
	}

}
