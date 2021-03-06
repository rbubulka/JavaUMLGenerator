package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import NodeGetter.ClassFileGetter;
import NodeGetter.FileGetter;
import outputMakers.GVMaker;
import outputMakers.OutputMaker;
import parserMakers.*;
import parsers.Parser;
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
	private List<Parser> otherparser = new ArrayList<>();
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
				ClassParserMaker.getInstance().setPublicClass();
			} else if (a.equals("-protectedClass")) {
				ClassParserMaker.getInstance().setProtectedClass();
			} else if (a.equals("-FD")) {
				FieldParserMaker.getInstance().setDependecies();
			} else if (a.equals("-publicField")) {
				FieldParserMaker.getInstance().setPublicFields();
			} else if (a.equals("-protectedField")) {
				FieldParserMaker.getInstance().setProtectedFields();
			} else if (a.equals("-privateField")) {
				FieldParserMaker.getInstance().setPrivateFields();
			} else if (a.equals("-fieldDependency")) {
				FieldParserMaker.getInstance().setDependecies();
			} else if (a.equals("-publicMethod")) {
				MethodParserMaker.getInstance().setPublicMethods();
			} else if (a.equals("-protectedMethod")) {
				MethodParserMaker.getInstance().setProtectedMethods();
			} else if (a.equals("-privateMethod")) {
				MethodParserMaker.getInstance().setPrivateMethods();
			} else if (a.equals("-MRD")) {
				MethodParserMaker.getInstance().setDependecies();
			} else if (a.equals("-MID")) {
				MethodParserMaker.getInstance().setInstructions();
			} else if (a.equals("-GVM")) {
				outputmaker = new GVMaker();
			} else if (a.equals("-recursive")) {
				this.recursive = true;
			} else if (a.contains("-o=")) {
				this.output = a.substring(3);
			} else if (a.contains("-i=")) {
				this.input = a.substring(3);
			} else if (a.contains("-e=")) {
				for (String str : a.substring(3).split(",")) {
					ClassLoader reader = new ClassLoader() {
					};
					Class<?> clazz = reader.loadClass(str);
					Constructor<?> con = clazz.getConstructor(ClassParser.class);
					ClassParser[] ls = new ClassParser[1];
					this.otherparser.add((ClassParser) con.newInstance(ls));
				}
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
				ClassParserMaker.getInstance().setPublicClass();
			} else if (s.equals("protectedClass") && pro.getProperty(s).equals("true")) {
				ClassParserMaker.getInstance().setProtectedClass();
			} else if (s.equals("FD") && pro.getProperty(s).equals("true")) {
				FieldParserMaker.getInstance().setDependecies();
			} else if (s.equals("publicField") && pro.getProperty(s).equals("true")) {
				FieldParserMaker.getInstance().setPublicFields();
			} else if (s.equals("protectedField") && pro.getProperty(s).equals("true")) {
				FieldParserMaker.getInstance().setProtectedFields();
			} else if (s.equals("privateField") && pro.getProperty(s).equals("true")) {
				FieldParserMaker.getInstance().setPrivateFields();
			} else if (s.equals("fieldDependency") && pro.getProperty(s).equals("true")) {
				FieldParserMaker.getInstance().setDependecies();
			} else if (s.equals("publicMethod") && pro.getProperty(s).equals("true")) {
				MethodParserMaker.getInstance().setPublicMethods();
			} else if (s.equals("protectedMethod") && pro.getProperty(s).equals("true")) {
				MethodParserMaker.getInstance().setProtectedMethods();
			} else if (s.equals("privateMethod") && pro.getProperty(s).equals("true")) {
				MethodParserMaker.getInstance().setPrivateMethods();
			} else if (s.equals("MRD") && pro.getProperty(s).equals("true")) {
				MethodParserMaker.getInstance().setDependecies();
			} else if (s.equals("MID") && pro.getProperty(s).equals("true")) {
				MethodParserMaker.getInstance().setInstructions();
			} else if (s.equals("GVM") && pro.getProperty(s).equals("true")) {
				outputmaker = new GVMaker();
			} else if (s.equals("recursive") && pro.getProperty(s).equals("true")) {
				this.recursive = true;
			} else if (s.equals("whitelist") && !pro.getProperty(s).equals("false")) {
				for (String str : pro.getProperty(s).split(",")) {
					whitelist.add(str);
				}

			} else if (s.equals("blacklist") && !pro.getProperty(s).equals("false")) {
				for (String str : pro.getProperty(s).split(",")) {
					blacklist.add(str);
				}
			} else if (s.equals("lambda") && pro.getProperty(s).equals("true")) {
				MethodParserMaker.getInstance().setLambda(true);
			} else if (s.equals("pattern") && !pro.getProperty(s).equals("false")) {
				for (String str : pro.getProperty(s).split(",")) {
					ClassLoader reader = new ClassLoader() {
					};
					Class<?> clazz = reader.loadClass(str);
					Constructor<?> con = clazz.getConstructor(ClassParser.class);
					ClassParser[] ls = new ClassParser[1];
					this.otherparser.add((ClassParser) con.newInstance(ls));
				}
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
		NodeParseToUML nptu = new NodeParseToUML(this.methodparser, this.fieldparser, this.classparser,
				this.otherparser);
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
		this.simplifyRelations(relations);
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
		List<Object> r1 = Arrays.asList(relations.toArray());
		Set<String> toRemove = new HashSet<String>();
		Set<String> toAdd = new HashSet<String>();
		for (int i = 0; i < r1.size(); i++) {
			String relation1 = (String) r1.get(i);
			String rpre1 = "";
			if (relation1.charAt(0) == ')' && relation1.charAt(1) == 'L') {
				relation1 = relation1.substring(2);
				rpre1 = ")L";
			}

			for (int j = i; j < r1.size(); j++) {
				String relation2 = (String) r1.get(j);
				String rpre2 = "";
				if (relation2.charAt(0) == ')' && relation2.charAt(1) == 'L') {
					relation2 = relation2.substring(2);
					rpre2 = ")L";
				}
				if (relation1 != relation2) {
					String[] rel1 = relation1.split(" ");
					String[] rel2 = relation2.split(" ");
					String color = "";
					if (rel1.length > 5) {

						color = rel1[5];
					}
					if (rel2.length > 5) {
						color = rel2[5];
					}
					String extra = "";
					if (rel1.length >= rel2.length) {
						for (int k = 6; k < rel1.length; k++) {
							extra += " " + rel1[k];
						}
					} else {
						for (int k = 6; k < rel2.length; k++) {
							extra += " " + rel2[k];
						}
					}
					if (rel1.length >= 5 && rel2.length >= 5
							&& ((rel1[0]).equals(rel2[4]) || ("L" + rel1[0]).equals(rel2[4]))
							&& (rel1[4].equals("L" + rel2[0]) || rel1[4].equals(rel2[0]))
							&& (rel1[2].equals(rel2[2]) && !rel1[2].contains("both"))) {
						toAdd.add(rel1[0] + " " + rel1[1].charAt(0) + "..." + rel1[3].charAt(0) + " " + "both"
								+ rel1[2].trim() + " " + rel2[1].charAt(0) + "..." + rel2[3].charAt(0) + " " + rel1[4]
								+ color + extra);
						toRemove.add(rpre1 + relation1);
						toRemove.add(rpre2 + relation2);
					}
				}
			}
		}
		relations.addAll(toAdd);
		relations.removeAll(toRemove);

		HashSet<String> toRemove2 = new HashSet<>();
		for (String s : relations) {
			for (String ss : relations) {
				if (s != ss && s.contains(ss)) {
					toRemove2.add(ss);
				}
			}
		}
		relations.removeAll(toRemove2);

	}

	private void simplifyRelations(Set<String> relations) {
		HashMap<String, Integer> checkmap = new HashMap<>();
		checkmap.put("usea", 1);
		checkmap.put("bothusea", 2);
		checkmap.put("hasa", 3);
		checkmap.put("bothhasa", 4);
		checkmap.put("implements", 5);
		checkmap.put("bothimplements", 6);
		checkmap.put("extends", 7);
		checkmap.put("bothextends", 8);
		Set<String> toRemove = new HashSet<String>();
		Set<String> toAdd = new HashSet<String>();
		Object[] rel = relations.toArray();
		for (int i = 0; i < rel.length; i++) {
			String s = (String) rel[i];
			String[] sls = s.split(" ");
			for (int j = i; j < rel.length; j++) {
				String r = (String) rel[j];
				String[] rls = r.split(" ");
				if (sls.length >= 5 && rls.length >= 5 && r != s
						&& (((sls[0].contains(rls[0]) || rls[0].contains(sls[0]))
								&& (sls[4].contains(rls[4]) || rls[4].contains(sls[4])))
								|| (sls[0].contains(rls[4]) || rls[4].contains(sls[0]))
										&& (sls[4].contains(rls[0]) || rls[0].contains(sls[4]))
										&& sls[2].contains("both") && rls[2].contains("both"))) {

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
						String extra = "";
						if (rls.length >= sls.length) {
							for (int k = 5; k < rls.length; k++) {
								extra += " " + rls[k];
							}
						} else {
							for (int k = 5; k < sls.length; k++) {
								extra += " " + sls[k];
							}
						}
						toAdd.add(rls[0] + " " + rls[1] + " " + rls[2] + " " + rls[3] + " " + rls[4] + extra);
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
