package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import parsers.Parser;
import parsers.ClassParser.ClassParser;
import parsers.FieldParser.FieldsParser;
import parsers.MethodParser.MethodsParser;


public class NodeParseToUML {
	private MethodsParser mparser;
	private FieldsParser fparser;
	private ClassParser cparser;
	private List<Parser> oparsers;
	public NodeParseToUML(MethodsParser mppp,FieldsParser fppp,ClassParser cppp,List<Parser> otherppp){
		this.mparser=mppp;
		this.fparser=fppp;
		this.cparser=cppp;
		this.oparsers=otherppp;
	}
	
	public List<HashMap<String,String>> doParse(Set<ClassNode> nodes, Set<String> relations){
		List<HashMap<String,String>> classInfoList = new ArrayList<>();
		for(ClassNode n: nodes){
			HashMap<String, String> classInfo = new HashMap<>();
			classInfo.put("Class",cparser.parse(Collections.singletonList(n), relations));
			classInfo.put("Method",mparser.parse(n.methods, relations,n.name));
			classInfo.put("Field",fparser.parse(n.fields, relations,n.name));
			StringBuilder details = new StringBuilder();
			for(Parser o: this.oparsers){
				details.append(o.parse(Collections.singletonList(n), relations));
			}
			classInfo.put("Details", details.toString());
			classInfoList.add(classInfo);
			
		}
		return classInfoList;
		
		
		
	}
}
