package parsers.ClassParser;

import java.util.List;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PublicClassParser extends ClassParser {

	public PublicClassParser(ClassParser other) {
		super(other);
		this.opcode=Opcodes.ACC_PUBLIC;
		this.text="public";
		
	}

}
