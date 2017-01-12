package main;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.objectweb.asm.tree.ClassNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import NodeGetter.ClassFileGetter;
import NodeGetter.FileGetter;
import outputMakers.GVMaker;
import outputMakers.OutputMaker;
import parserMakers.*;
import parsers.*;
import parsers.ClassParser.*;
import parsers.FieldParser.*;
import parsers.MethodParser.*;
import parsers.MethodParser.PublicMethodsParser;
import printing.Printer;

public class UMLGenerator {

	private FileGetter parser = new ClassFileGetter();
	private MethodsParser methodparser = new PublicMethodsParser(null);
	private FieldsParser fieldparser = new PublicFieldsParser(null);
	private ClassParser classparser = new PublicClassParser(null);
	private OutputMaker outputmaker = new GVMaker();
	private String output = ".\\Documents\\output.dot";
	private boolean recursive = false;
	private ArrayList<String> classnames = new ArrayList<String>();

	public UMLGenerator(String[] args) throws Exception {
		for (String a : args) {
			if (a.equals("-publicClass")) {
				ClassParserMaker.getInstance().setPublicFields(true);
			} else if (a.equals("-protectedClass")) {
				ClassParserMaker.getInstance().setProtectedFields(true);
			} else if (a.equals("-FD")) {
				FieldParserMaker.getInstance().setDependecies(true);
			} else if (a.equals("-publicField")) {
				FieldParserMaker.getInstance().setPublicFields(true);
			} else if (a.equals("-protectedField")) {
				FieldParserMaker.getInstance().setProtectedFields(true);
			} else if (a.equals("-privateField")) {
				FieldParserMaker.getInstance().setPrivateFields(true);
			} else if (a.equals("-fieldDependency")) {
				FieldParserMaker.getInstance().setDependecies(true);
			} else if (a.equals("-publicMethod")) {
				MethodParserMaker.getInstance().setPublicFields(true);
			} else if (a.equals("-protectedMethod")) {
				MethodParserMaker.getInstance().setProtectedFields(true);
			} else if (a.equals("-privateMethod")) {
				MethodParserMaker.getInstance().setPrivateFields(true);
			} else if (a.equals("-MRD")) {
				MethodParserMaker.getInstance().setDependecies(true);
			} else if (a.equals("-MID")) {
				MethodParserMaker.getInstance().setInstructions(true);
			} else if (a.equals("-GVM")) {
				outputmaker = new GVMaker();
			} else if (a.equals("-recursive")) {
				this.recursive = true;
			} else if (a.contains("-o=")) {
				this.output = a.substring(3);
			} else {
				this.classnames.add(a);
			}

		}
		MethodsParser mp = MethodParserMaker.getInstance().makeParser();
		if (mp != null) {
			this.methodparser = mp;
		}
		FieldsParser fp = FieldParserMaker.getInstance().makeParser();
		if (fp != null) {
			this.fieldparser = fp;
		}
		ClassParser cp = ClassParserMaker.getInstance().makeParser();
		if (cp != null) {
			this.classparser = cp;
		}

		NodeRelation nodeRelations = this.getNodes(this.recursive);
		Set<ClassNode> nodelist = nodeRelations.getNodes();
		Set<String> relations = nodeRelations.getRelations();
		NodeParseToUML nptu = new NodeParseToUML(this.methodparser, this.fieldparser, this.classparser, null);
//		for(String s:relations){
//			System.out.println(s);
//		}
		List<HashMap<String, String>> parsedstring = nptu.doParse(nodelist, relations);
		
		this.simplifyRelations(relations);
		this.twoWayRelations(relations);
	
	
		this.outputmaker.fileWrite(this.output, parsedstring, relations);

	}

	public NodeRelation getNodes(boolean recursive) throws IOException {
		HashSet<ClassNode> nodes = new HashSet<ClassNode>();
		Set<String> relations = new HashSet<>();
		this.parser.addClasses(this.classnames, nodes, relations, recursive);
		return new NodeRelation(nodes, relations);

	}

	private void twoWayRelations(Set<String> relations){
		List r1 =	Arrays.asList(relations.toArray());
		Set<String> toRemove = new HashSet<String>();
		Set<String> toAdd = new HashSet<String>();
		for(int i =0; i < r1.size(); i++){
			String relation1 = (String)r1.get(i);
			for(int j =i; j < r1.size(); j++){
				String relation2 = (String)r1.get(j);
				if(relation1 != relation2){ 
					String[] rel1 = relation1.split(" ");
					String[] rel2 = relation2.split(" ");
					
					//if(relation1.contains("Dummy")||relation2.contains("Dummy")) System.out.println(relation1 + " " + relation2);
					
					if(rel1.length >= 5 && rel2.length >= 5 && 
							( (rel1[0]).equals(rel2[4])||("L"+rel1[0]).equals(rel2[4])) && 
							(rel1[4].equals("L"+rel2[0]) || rel1[4].equals(rel2[0]) )&& 
							rel1[2].equals(rel2[2])){
//						String num1 = " 1 ";
//						String num2 = " 1 ";
//						if(rel1[3].equals("*")||rel2[1].equals("*")){ num1 = " * ";}
//						if(rel2[3].equals("*")||rel1[1].equals("*")){ num2 = " * ";}
						toAdd.add(rel1[0] + " "+rel1[1]+"..."+rel1[3]+" " + "both" + rel1[2].trim() + " "+rel2[1]+"..."+rel2[3]+" "  + rel1[4]);
						toRemove.add(relation1);
						toRemove.add(relation2);
					}
				}
			}
		}
		relations.addAll(toAdd);
		relations.removeAll(toRemove);
	}
	
	private void simplifyRelations(Set<String> relations) {
		HashMap<String,Integer> checkmap=new HashMap<>();
		checkmap.put("usea", 0);
		checkmap.put("hasa", 1);
		checkmap.put("implements", 2);
		checkmap.put("extends", 3);
		Set<String> toRemove = new HashSet<String>();
		Set<String> toAdd = new HashSet<String>();
		for (String s : relations) {
			String[] sls= s.split(" ");
				for (String r : relations) {
					String[] rls = r.split(" ");
					if (sls.length == 5 && rls.length == 5 && r != s && sls[0].equals(rls[0]) && sls[4].equals(rls[4])){
							int rnum=checkmap.get(rls[2]);
							int snum=checkmap.get(sls[2]);
							if(rnum>snum){
								toRemove.add(s);
							}
							else if(rnum==snum){
								String sleft=sls[1];
								String sright=sls[3];
								String rleft=rls[1];
								String rright=rls[3];
								if(!sleft.equals(rleft)){
									rls[1]="*";
								}
								if(!sright.equals(rright)){
									rls[3]="*";
								}
								toAdd.add(rls[0]+" "+rls[1]+" "+rls[2]+" "+rls[3]+" "+rls[4]);
								toRemove.add(r);
								toRemove.add(s);
								
							}
							else {
								toRemove.add(r);
							}
					

				}
			}

		}
		relations.addAll(toAdd);
		relations.removeAll(toRemove);
		
		
	}

	private class NodeRelation {
		Set<ClassNode> nodes;
		Set<String> relations;

		public NodeRelation(Set<ClassNode> nodes, Set<String> relations) {
			this.relations = relations;
			this.nodes = nodes;
		}

		public Set<ClassNode> getNodes() {
			return this.nodes;
		}

		public Set<String> getRelations() {
			return this.relations;
		}

	}

}
