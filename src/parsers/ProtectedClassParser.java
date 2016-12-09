package parsers;

import java.util.List;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ProtectedClassParser extends ClassParser {

	public ProtectedClassParser(ClassParser other) {
		super(other);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String parse(List node) {
		return this.parseinfo(Opcodes.ACC_PROTECTED, "protected", node);
	}
}
