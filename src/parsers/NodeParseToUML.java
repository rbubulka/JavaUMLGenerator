package parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;


public class NodeParseToUML {
	private MethodsPpp mppp;
	private FieldsPpp fppp;
	public NodeParseToUML(MethodsPpp mppp,FieldsPpp fppp,List<Ppp> otherppp){
		this.mppp=mppp;
		this.fppp=fppp;
	}
	
	public void doParse(List<ClassNode> nodes){
		List<HashMap<String,String>> classInfoList = new ArrayList<>(); 
		for(ClassNode n: nodes){
			HashMap<String, String> classInfo = new HashMap<>();
			classInfo.put("FileName",n.name);
			classInfo.put("Interface",String.valueOf((n.access&Opcodes.ACC_INTERFACE)>0));
			classInfo.put("Abstract", String.valueOf((n.access&Opcodes.ACC_ABSTRACT)>0));
			classInfo.put("Protected",String.valueOf((n.access&Opcodes.ACC_PROTECTED)>0));
			classInfo.put("Method",mppp.parse(n.methods));
			classInfo.put("Field",fppp.parse(n.fields));
			classInfoList.add(classInfo);
			
		}
		
	}
}
