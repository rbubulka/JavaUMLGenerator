package parsers;

import java.util.List;

import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PublicMethodsParser extends MethodsParser {
	
	public PublicMethodsParser(MethodsParser other){
		super(other);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String parse(List methods) {
		return this.parseinfo(Opcodes.ACC_PUBLIC, "public", methods);
	}
}
