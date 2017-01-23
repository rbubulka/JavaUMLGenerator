package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;

import NodeGetter.ClassFileGetter;
import NodeGetter.FileGetter;
import outputMakers.GVMaker;
import outputMakers.OutputMaker;
import parserMakers.*;
import parsers.ClassParser.*;
import parsers.FieldParser.*;
import parsers.MethodParser.*;
import parsers.MethodParser.PublicMethodsParser;
import utilities.NodeRelation;
import static utilities.CheckIfInBlacklist.checkIfInBlacklist;

public class UMLGenerator {

	private FileGetter parser = new ClassFileGetter();
	private MethodsParser methodparser = new PublicMethodsParser(null);
	private FieldsParser fieldparser = new PublicFieldsParser(null);
	private ClassParser classparser = new PublicClassParser(null);
	private OutputMaker outputmaker = new GVMaker();
	private String output = ".\\Documents\\output.dot";
	private String input = null;
	private boolean recursive = false;
	private ArrayList<String> classnames = new ArrayList<String>();
	private ArrayList<String> whitelist = new ArrayList<String>();
	private ArrayList<String> blacklist = new ArrayList<String>();

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
			} else if (a.contains("-i=")) {
				this.input = a.substring(3);
			} else {
				this.classnames.add(a);
			}

		}

		Properties pro = new Properties();

		FileInputStream in = new FileInputStream(input);
		pro.load(in);
		in.close();

		for (Object o : pro.keySet()) {
			String s = (String) o;
			if (s.equals("publicClass") && pro.getProperty(s).equals("true")) {
				ClassParserMaker.getInstance().setPublicFields(true);
			} else if (s.equals("protectedClass") && pro.getProperty(s).equals("true")) {
				ClassParserMaker.getInstance().setProtectedFields(true);
			} else if (s.equals("FD") && pro.getProperty(s).equals("true")) {
				FieldParserMaker.getInstance().setDependecies(true);
			} else if (s.equals("publicField") && pro.getProperty(s).equals("true")) {
				FieldParserMaker.getInstance().setPublicFields(true);
			} else if (s.equals("protectedField") && pro.getProperty(s).equals("true")) {
				FieldParserMaker.getInstance().setProtectedFields(true);
			} else if (s.equals("privateField") && pro.getProperty(s).equals("true")) {
				FieldParserMaker.getInstance().setPrivateFields(true);
			} else if (s.equals("fieldDependency") && pro.getProperty(s).equals("true")) {
				FieldParserMaker.getInstance().setDependecies(true);
			} else if (s.equals("publicMethod") && pro.getProperty(s).equals("true")) {
				MethodParserMaker.getInstance().setPublicFields(true);
			} else if (s.equals("protectedMethod") && pro.getProperty(s).equals("true")) {
				MethodParserMaker.getInstance().setProtectedFields(true);
			} else if (s.equals("privateMethod") && pro.getProperty(s).equals("true")) {
				MethodParserMaker.getInstance().setPrivateFields(true);
			} else if (s.equals("MRD") && pro.getProperty(s).equals("true")) {
				MethodParserMaker.getInstance().setDependecies(true);
			} else if (s.equals("MID") && pro.getProperty(s).equals("true")) {
				MethodParserMaker.getInstance().setInstructions(true);
			} else if (s.equals("GVM") && pro.getProperty(s).equals("true")) {
				outputmaker = new GVMaker();
			} else if (s.equals("recursive") && pro.getProperty(s).equals("true")) {
				this.recursive = true;
			} else if (s.equals("whitelist") && !pro.getProperty(s).equals("")) {
				for (String str : pro.getProperty(s).split(",")) {
					whitelist.add(str);
				}

			} else if (s.equals("blacklist") && !pro.getProperty(s).equals("")) {
				for (String str : pro.getProperty(s).split(",")) {
					blacklist.add(str);
				}
			} else if (s.equals("lambda") && pro.getProperty(s).equals("true")) {
				MethodParserMaker.getInstance().setLambda(true);
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
		List<HashMap<String, String>> parsedstring = nptu.doParse(nodelist, relations);

		HashSet<String> toremove = new HashSet<>();
		HashSet<String> toadd = new HashSet<>();
		for (String r : relations) {
			String s = r.replaceAll(";", "");
			toremove.add(r);
			toadd.add(s);

		}
		relations.removeAll(toremove);
		relations.addAll(toadd);

		this.simplifyRelations(relations);
		this.twoWayRelations(relations);
		this.cleanblacklist(relations);
		this.outputmaker.fileWrite(this.output, parsedstring, relations);

	}

	public NodeRelation getNodes(boolean recursive) throws IOException {
		HashSet<ClassNode> nodes = new HashSet<ClassNode>();
		Set<String> relations = new HashSet<>();
		this.classnames.addAll(whitelist);
		this.parser.addClasses(this.classnames, nodes, relations, recursive, blacklist);
		return new NodeRelation(nodes, relations);

	}

	private void twoWayRelations(Set<String> relations) {
		List r1 = Arrays.asList(relations.toArray());
		Set<String> toRemove = new HashSet<String>();
		Set<String> toAdd = new HashSet<String>();
		for (int i = 0; i < r1.size(); i++) {
			String relation1 = (String) r1.get(i);
			for (int j = i; j < r1.size(); j++) {
				String relation2 = (String) r1.get(j);
				if (relation1 != relation2) {
					String[] rel1 = relation1.split(" ");
					String[] rel2 = relation2.split(" ");

					if (rel1.length >= 5 && rel2.length >= 5
							&& ((rel1[0]).equals(rel2[4]) || ("L" + rel1[0]).equals(rel2[4]))
							&& (rel1[4].equals("L" + rel2[0]) || rel1[4].equals(rel2[0])) && rel1[2].equals(rel2[2])) {
						toAdd.add(rel1[0] + " " + rel1[1] + "..." + rel1[3] + " " + "both" + rel1[2].trim() + " "
								+ rel2[1] + "..." + rel2[3] + " " + rel1[4]);
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
		HashMap<String, Integer> checkmap = new HashMap<>();
		checkmap.put("usea", 0);
		checkmap.put("hasa", 1);
		checkmap.put("implements", 2);
		checkmap.put("extends", 3);
		Set<String> toRemove = new HashSet<String>();
		Set<String> toAdd = new HashSet<String>();
		Object[] rel = relations.toArray();
		for (int i = 0; i < rel.length; i++) {
			String s = (String) rel[i];
			String[] sls = s.split(" ");
			for (int j = i; j < rel.length; j++) {
				String r = (String) rel[j];
				String[] rls = r.split(" ");
				if (sls.length == 5 && rls.length == 5 && r != s && sls[0].equals(rls[0])
						&& (sls[4].equals(rls[4]) || sls[4].equals(rls[4].substring(1))
								|| sls[4].substring(1).equals(rls[4])
								|| sls[4].substring(1).equals(rls[4].substring(1)))) {
					int rnum = checkmap.get(rls[2]);
					int snum = checkmap.get(sls[2]);
					if (rnum > snum) {
						toRemove.add(s);
					} else if (rnum == snum) {
						String sleft = sls[1];
						String sright = sls[3];
						String rleft = rls[1];
						String rright = rls[3];
						if (!sleft.equals(rleft)) {
							rls[1] = "*";
						}
						if (!sright.equals(rright)) {
							rls[3] = "*";
						}
						toAdd.add(rls[0] + " " + rls[1] + " " + rls[2] + " " + rls[3] + " " + rls[4]);
						toRemove.add(r);
						toRemove.add(s);

					} else {
						toRemove.add(r);
					}
				}
			}

		}
		relations.addAll(toAdd);
		relations.removeAll(toRemove);

	}

	private void cleanblacklist(Set<String> relations) {
		ArrayList<String> toRemove = new ArrayList<>();

		for (String s : relations) {
			if (checkIfInBlacklist(s, blacklist)) {
				toRemove.add(s);

			}
		}
		relations.removeAll(toRemove);
	}
}
