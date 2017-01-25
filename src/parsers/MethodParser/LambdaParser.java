package parsers.MethodParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.MethodNode;

public class LambdaParser extends MethodsParser {

	public LambdaParser(MethodsParser other) {
		super(other);
	}
	@Override
	public  String parse(List methods, Set<String> relations,String name){
		StringBuilder result= new StringBuilder();
		ArrayList<MethodNode> toRemove=new ArrayList<>();
		for(MethodNode m:(List<MethodNode>) methods){
			if(m.name.contains("$")){
				toRemove.add(m);
			}
		}
		methods.removeAll(toRemove);
		if(otherparser !=  null)result.append(this.otherparser.parse(methods, relations, name));
		return result.toString();
	}

}
