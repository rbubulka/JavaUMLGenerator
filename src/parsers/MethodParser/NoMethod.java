package parsers.MethodParser;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.MethodNode;

public class NoMethod extends MethodsParser {

	public NoMethod(MethodsParser other) {
		super(other);
	}
	@Override
	public  String parse(List methods, Set<String> relations){
		return "";
	}

}
