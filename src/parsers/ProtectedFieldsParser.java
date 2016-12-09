package parsers;

import java.util.List;

import org.objectweb.asm.tree.FieldNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ProtectedFieldsParser extends FieldsParser {
	

	
	public ProtectedFieldsParser(FieldsParser other){
		super(other);
	}
	@Override
	public String parse(List fields) {
		return this.parseinfo(Opcodes.ACC_PROTECTED, "protected", fields);
	}
}
