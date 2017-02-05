package parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import parsers.ClassParser.ClassParser;

public class DecoratorParser extends ClassParser {

	public DecoratorParser(ClassParser other) {
		super(other);
	}

	@Override
	public String parse(List nodes, Set<String> relations, List<HashMap<String, String>> classinfo) {
		StringBuilder result = new StringBuilder();
		for (ClassNode cn : (List<ClassNode>) nodes) {
			ClassNode current = cn;
			List<MethodNode> thismls = (List<MethodNode>) current.methods;
			Set<String> paths = new HashSet<String>();
			Set<String> comp = new HashSet<String>();
			boolean ifDe = helper(cn, paths, comp);
			if (ifDe) {
				
				for (String p : comp) {
					for (HashMap<String, String> hashmap : classinfo) {
						if (hashmap.get("Class").contains(p)) {
							String details = hashmap.get("Details");
							if(details == null) details  = "";
							if (!details.contains("fillcolor")) {
								hashmap.put("Details",
										details + ",write=component" + ",fillcolor=\"green\"" + ",style=\"filled\"");

							}
						}
					}
				}
				for (String p : paths) {

					for (HashMap<String, String> hashmap : classinfo) {
						
						if (hashmap.get("Class").contains(p)) {
							String details = hashmap.get("Details");
							if(details == null) details = "";
							if (!details.contains("fillcolor")) {
//								System.out.println(hashmap.get("Class"));
								hashmap.put("Details",
										details + ",write=decorator" + ",fillcolor=\"green\"" + ",style=\"filled\"");
//								System.out.println("Details"+hashmap.get("Details"));
//								System.out.println("end");
							}
						}
					}
				}
				
			}

		}
		if (otherparser != null)
			result.append(this.otherparser.parse(nodes, relations, classinfo));
		return result.toString();
	}

	private boolean helper(ClassNode cn, Set<String> path, Set<String> comp) {
		List<MethodNode> methodls = cn.methods;
		Set<String> typels = new HashSet<String>();
		// get all the classes passed as arguments in the constructor
		for (MethodNode md : methodls) {
			if (md.name.contains("<init>")) {
				String desc = md.desc;
				desc.substring(desc.indexOf("(") + 1, desc.indexOf(")"));
				if (!desc.equals("")) {
					typels.addAll(new ArrayList(Arrays.asList(desc.split(";"))));
					typels.remove("");
				}

			}
		}
		ClassNode currentNode = cn;
		path.add(cn.name);
		while (true) {
			// check if not at object
			String supername = currentNode.superName;
			if (supername == null) {
				return false;
			}
			// get all the interfaces of the current parent to theoriginal class
			Set<String> interfacels = new HashSet<String>();
			for (String temp : (List<String>) currentNode.interfaces) {

				interfacels.add(temp);
			}
			interfacels.add(currentNode.name);
			// check if the original class takes one of the interfaces as an
			// arguemtn
			for (String interfacename : interfacels) {
				for (String tp : typels) {
					if (tp.contains(interfacename) || interfacename.contains(tp)) { 
						// if it does then is a decorator
						comp.add(interfacename);
						return true;
					}
				}
			}
			// if not go to super
			ClassReader cr = null;
			try {
				cr = new ClassReader(supername);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ClassNode next = new ClassNode();
			cr.accept(next, ClassReader.EXPAND_FRAMES);
			path.add(currentNode.name);
			currentNode = next;
		}
	}
}
