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

	public NodeParseToUML(MethodsParser mppp, FieldsParser fppp, ClassParser cppp, List<Parser> otherppp) {
		this.mparser = mppp;
		this.fparser = fppp;
		this.cparser = cppp;
		this.oparsers = otherppp;
	}

	public List<HashMap<String, String>> doParse(Set<ClassNode> nodes, Set<String> relations) {
		List<HashMap<String, String>> classInfoList = new ArrayList<>();
		HashMap<String, String> classInfo;
		for (ClassNode n : nodes) {
			classInfo = new HashMap<>();
			classInfo.put("Class", cparser.parse(Collections.singletonList(n), relations));
			classInfo.put("Method", mparser.parse(n.methods, relations, n.name));
			classInfo.put("Field", fparser.parse(n.fields, relations, n.name));
			classInfoList.add(classInfo);
		}
		for (HashMap<String, String> map : classInfoList) {
			for (ClassNode n : nodes) {
				String nname = n.name;
				String[] classnameinfo = map.get("Class").split(" ");
				if(classnameinfo.length >= 2){
				String realname = classnameinfo[1];
				if (realname.contains(nname)) {

					StringBuilder details = new StringBuilder();
					for (int i = 1; i < oparsers.size(); i++) {
						oparsers.get(i).setParser(oparsers.get(i-1));
					}
					details.append(oparsers.get(oparsers.size()-1).parse(Collections.singletonList(n), relations));
					System.out.println("details"+details);
					map.put("Details", details.toString());
				}}
			}
		}
		return classInfoList;

	}
}
