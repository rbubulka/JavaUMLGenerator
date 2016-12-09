package parsers;

import java.util.List;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PublicClassParser extends ClassParser {

	public PublicClassParser(ClassParser other) {
		super(other);
		
	}
	@Override
	public String parse(List fields) {
		return this.parseinfo(Opcodes.ACC_PUBLIC, "public", fields);}

}
