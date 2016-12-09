package parsers;

import java.util.List;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PublicFieldsParser extends FieldsParser {
	public PublicFieldsParser(FieldsParser other) {
		super(other);
		this.opcode=Opcodes.ACC_PUBLIC;
		this.text="public";
		
	}

}
