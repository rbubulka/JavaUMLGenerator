package parsers.FieldParser;

import java.util.List;

import org.objectweb.asm.tree.FieldNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PrivateFieldsParser extends FieldsParser {
	
	
	
	public PrivateFieldsParser(FieldsParser other) {
		
		super(other);
		this.opcode=Opcodes.ACC_PRIVATE;
		this.text="private";
	}


}
