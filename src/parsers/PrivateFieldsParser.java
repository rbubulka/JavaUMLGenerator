package parsers;

import java.util.List;

import org.objectweb.asm.tree.FieldNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PrivateFieldsParser extends FieldsParser {
	
	
	
	public PrivateFieldsParser(FieldsParser other) {
		super(other);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String parse(List fields) {
		return this.parseinfo(Opcodes.ACC_PRIVATE, "private", fields);
	

	}
}
