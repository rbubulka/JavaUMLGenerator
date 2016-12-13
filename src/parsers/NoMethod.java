package parsers;

import java.util.List;

import org.objectweb.asm.tree.MethodNode;

public class NoMethod extends MethodsParser {

	public NoMethod(MethodsParser other) {
		super(other);
		// TODO Auto-generated constructor stub
	}
	@Override
	public  String parse(List methods){
		return "";
	}

}
