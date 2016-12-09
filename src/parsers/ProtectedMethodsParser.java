package parsers;

import java.util.List;

import org.objectweb.asm.tree.MethodNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ProtectedMethodsParser extends MethodsParser {
	
	
	public ProtectedMethodsParser(MethodsParser other){
		super(other);
	}
	
	@Override
	public String parse(List methods) {
		return this.parseinfo(Opcodes.ACC_PROTECTED, "protected", methods);
	}
}
