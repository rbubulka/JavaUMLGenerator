package parsers.MethodParser;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.MethodNode;

public abstract class MRDParser extends MethodsParser {

	public MRDParser(MethodsParser other) {
		super(other);
		// TODO Auto-generated constructor stub
	}
	@Override
	public  String parse(List methods, Set<String> relations,String name){
		StringBuilder result= new StringBuilder();
		for(MethodNode md:(List<MethodNode>)methods){
			if((md.access&opcode)>0){
				result.append(text+" " + md.name +" "+ md.signature + "\n");
			}
		}
		if(otherparser !=  null)result.append(this.otherparser.parse(methods, relations));
		return result.toString();
	}

}
