package parsers;

import java.util.List;

import org.objectweb.asm.tree.MethodNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ProtectedMethodsPpp extends MethodsPpp {
	
	
	public ProtectedMethodsPpp(MethodsPpp other){
		super(other);
	}
	
	@Override
	public String parse(List methods) {
		StringBuilder result= new StringBuilder();
		for(MethodNode md:(List<MethodNode>)methods){
			if((md.access&Opcodes.ACC_PROTECTED)>0){
				result.append("protected " + md.name +" "+ md.signature + "\n");
			}
		}
		if(otherPpp !=  null)result.append(this.otherPpp.parse(methods));
		return result.toString();
	}
}
