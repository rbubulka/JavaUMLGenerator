package parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;


public class NodeParseToUML {
	private MethodsParser mparser;
	private FieldsParser fparser;
	private ClassParser cparser;
	public NodeParseToUML(MethodsParser mppp,FieldsParser fppp,ClassParser cppp,List<Parser> otherppp){
		this.mparser=mppp;
		this.fparser=fppp;
		this.cparser=cppp;
	}
	
	public List<HashMap<String,String>> doParse(Set<ClassNode> nodes){
		List<HashMap<String,String>> classInfoList = new ArrayList<>();
		for(ClassNode n: nodes){
			HashMap<String, String> classInfo = new HashMap<>();
			ArrayList<ClassNode> temp=new ArrayList<>();
			temp.add(n);
			classInfo.put("Class",cparser.parse(temp));
			classInfo.put("Method",mparser.parse(n.methods));
			classInfo.put("Field",fparser.parse(n.fields));
			classInfoList.add(classInfo);
			
		}
		return classInfoList;
		
		
		
	}
}
