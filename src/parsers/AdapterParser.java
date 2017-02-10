package parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import parsers.ClassParser.ClassParser;

public class AdapterParser extends ClassParser {

	public AdapterParser(ClassParser other) {
		super(other);
	}

	@Override
	public String parse(List nodes, Set<String> relations, List<HashMap<String, String>> classinfo) {
		StringBuilder result = new StringBuilder();
		for (ClassNode cn : (List<ClassNode>) nodes) {
			// check if has interface
			List<String> interfaces = cn.interfaces;
			if (interfaces.size() > 0) {
				List<ClassNode> interfacenodes = new ArrayList<ClassNode>();
				List<String> methodNames = new ArrayList<String>();
				Map<String, Boolean> adapteesmap = new HashMap<String, Boolean>();
				for (FieldNode fn : (List<FieldNode>) cn.fields) {
					adapteesmap.put(fn.desc, false);
				}
				for (MethodNode method : (List<MethodNode>) cn.methods) {
					if (method.name.contains("<init>")) {
						String argstring = method.desc.substring(method.desc.indexOf("(") + 1,
								method.desc.lastIndexOf(")"));
						String[] args = argstring.split(";");
						if (args.length == 1) {
							for (String key : adapteesmap.keySet()) {
								if (key.contains(args[0]) || args[0].contains(key)) {
									adapteesmap.put(key, true);
								}
							}
						}
					}
					methodNames.add(method.name);
				}
				for (String interfacestring : interfaces) {
					ClassReader cr = null;
					try {
						cr = new ClassReader(interfacestring);
					} catch (IOException e) {
						e.printStackTrace();
					}
					ClassNode interfacenode = new ClassNode();
					cr.accept(interfacenode, ClassReader.EXPAND_FRAMES);
					interfacenodes.add(interfacenode);
				}
				// for (ClassNode node : interfacenodes) {
				// System.out.println("loop1");
				// for (MethodNode method : (List<MethodNode>) node.methods) {
				// System.out.println("loop2");
				// for (String field : adapteesmap.keySet()) {
				// System.out.println("loop3");
				// List<String> attributes = new ArrayList<String>();
				// for (Attribute at : (List<Attribute>) method.attrs) {
				// System.out.println("loop4");
				// System.out.println(at.type);
				// attributes.add(at.type);
				// }
				// if (!attributes.contains(field)) {
				// adapteesmap.put(field, false);
				// }
				// }
				// }
				//
				// }

				for (HashMap<String, String> hashmap : classinfo) {
					if (hashmap.get("Class").contains(cn.name)) {
						String details = hashmap.get("Details");
						if (details == null)
							details = "";
						if (!details.contains("fillcolor")) {
							hashmap.put("Details",
									details + ",write=adapter" + ",fillcolor=\"red\"" + ",style=\"filled\"");

						}
					}
				}

				for (HashMap<String, String> hashmap : classinfo) {
					for (ClassNode innode : interfacenodes) {
						if (hashmap.get("Class").contains(innode.name)) {
							String details = hashmap.get("Details");
							if (details == null)
								details = "";
							if (!details.contains("fillcolor")) {
								hashmap.put("Details",
										details + ",write=target" + ",fillcolor=\"red\"" + ",style=\"filled\"");

							}
						}
					}
				}

				for (HashMap<String, String> hashmap : classinfo) {
					for (String fi : adapteesmap.keySet()) {
						if (!adapteesmap.get(fi)) {
							continue;
						}
						if (hashmap.get("Class").contains(fi)) {
							String details = hashmap.get("Details");
							if (details == null)
								details = "";
							if (!details.contains("fillcolor")) {
								hashmap.put("Details",
										details + ",write=adaptee" + ",fillcolor=\"red\"" + ",style=\"filled\"");

							}
						}
					}
				}
				for (String fi : adapteesmap.keySet()) {
					if (!adapteesmap.get(fi)) {
						continue;
					}
				Set<String> toRemove = new HashSet<String>();
				Set<String> toAdd = new HashSet<String>();
			for (String rel : relations) {
				String[] splitted = rel.split(" ");
				if((splitted[4].contains(cn.name)||cn.name.contains(splitted[4]))
						&&(splitted[0].contains(fi)||fi.contains(splitted[0]))){
					toRemove.add(rel);				
					if(rel.split(" ").length == 5){
						rel=rel.concat(" color=\"black\"");
					}

					toAdd.add(rel.concat(" <<adapts>>"));
				}
			}
			relations.removeAll(toRemove);
			relations.addAll(toAdd);
			

				// for every argument in <init> we must go through the method
				// nodes
				// and find which one is used in every method or the method
				// throws
				// an exception which matches the super
				// go to super make red mark as target, go to matching
				// overusedfieldclass and mark as adaptee

			}}
		}
		if (otherparser != null)
			result.append(this.otherparser.parse(nodes, relations, classinfo));
		return result.toString();
	}
}
