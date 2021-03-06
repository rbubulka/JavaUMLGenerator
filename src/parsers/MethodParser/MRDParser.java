package parsers.MethodParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.MethodNode;

import utilities.AvoidNonclassUtils;
import static utilities.SignatureInterpreter.interpret;
public abstract class MRDParser extends MethodsParser {

	public MRDParser(MethodsParser other) {
		super(other);
	}

	@Override
	public String parse(List methods, Set<String> relations, String name,  List<HashMap<String, String>> classinfo) {
		StringBuilder result = new StringBuilder();
		for (MethodNode md : (List<MethodNode>) methods) {
			if ((md.access & opcode) > 0) {
				String start = "";
				String returntype = "";
				if (md.signature != null ) {
		
					List<String> strs = new ArrayList<>();
					HashMap<String,Boolean> hm=new HashMap<>();
					interpret(md.signature,strs,hm,false);
					for(String s: strs){
					
						addDependency(relations, name, s, hm.get(s));
						
					}
//					int index1 = md.signature.indexOf("(");
//					int index2 = md.signature.lastIndexOf(")");
//					returntype = md.signature.substring(index2 + 1);
//					start = md.signature.substring(index1+1, index2);
				} else {
					List<String> strs = new ArrayList<>();
					HashMap<String,Boolean> hm=new HashMap<>();
					interpret(md.desc,strs,hm,false);
					for(String s: strs){
					
						addDependency(relations, name, s, hm.get(s));
						
					}
				}
//				if (start != null) {
//					if (start.contains(":")) {
//						start = start.substring(start.indexOf(">") + 1);
//					}
//					String[] startarray = start.split("\\)");
//					String arg = startarray[0].replaceAll("\\(", "");
//					arg = arg.replace(";>;", ">;");
//					
//					List<String> temp = new ArrayList<>();
//					for(String str: arg.split("[<>]")){
//						if(str.contains(";")){
//						for(String s: str.split(";")){
//							temp.add(s);
//						}
//						}
//					}
//					String[] rettemp=returntype.split("[<>]");
//						
//					for(String s:rettemp){
//						for(String ss:s.split(";")){
//						temp.add(ss);}
//					}
//					
//					
//					for (String s : temp) {
//						if (s != null && s.contains("<")) {
//							this.addCollectionDependency(relations, name, s);
//						} else {
//							if (s.contains("[")) {
//
//								this.addDependency(relations, name, s.replace(";", ""), true);
//							} else {
//
//								this.addDependency(relations, name, s.replace(";", ""), false);
//							}
//						}
//
//					}
//				}
			}
		}
		if (otherparser != null)
			result.append(this.otherparser.parse(methods, relations, name, classinfo));
		return result.toString();
	}

	private void addDependency(Set<String> relations, String classname, String type, boolean isCollection) {
		if (AvoidNonclassUtils.isAClass(type)) {
			String num = "1 ";
			if (isCollection||type.contains("["))
				num = "* ";
			relations.add(classname + " 1 usea " + num + type.replaceAll("\\[", ""));
		}

	}
}
