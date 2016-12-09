package parsers;

import java.util.List;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ProtectedClassParser extends ClassParser {

	public ProtectedClassParser(ClassParser other) {
		super(other);
		this.opcode=Opcodes.ACC_PROTECTED;
		this.text="protected";
	}
	}
	
