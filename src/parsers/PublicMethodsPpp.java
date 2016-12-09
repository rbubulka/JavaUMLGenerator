package parsers;

import java.util.List;

import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class PublicMethodsPpp extends MethodsPpp {
	
	public PublicMethodsPpp(MethodsPpp other){
		super(other);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String parse(List methods) {
		StringBuilder result= new StringBuilder();
		for(MethodNode md:(List<MethodNode>)methods){
			if((md.access&Opcodes.ACC_PUBLIC)>0){
				result.append("public" + md.name +" "+ md.signature + "\n");
			}
		}
		if(otherPpp !=  null)this.otherPpp.parse(methods);
		return result.toString();
	}
}
