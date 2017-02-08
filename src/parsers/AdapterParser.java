package parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
			List<ClassNode> interfacenodes = new ArrayList<ClassNode>();
			List<String> methodNames = new ArrayList<String>();
			Map<String, Boolean> adapteesmap = new HashMap<String, Boolean>();
			for (FieldNode fn : (List<FieldNode>) cn.fields) {
				adapteesmap.put(fn.desc, true);
			}
			for (MethodNode method : (List<MethodNode>) cn.methods) {
				methodNames.add(method.name);
			}
			for (String interfacestring : interfaces) {
				System.out.println("hbfkdsabfa");
				ClassReader cr = null;
				try {
					cr = new ClassReader(interfacestring);
				} catch (IOException e) {
					e.printStackTrace();
				}
				ClassNode interfacenode = new ClassNode();
				cr.accept(interfacenode, ClassReader.EXPAND_FRAMES);
				interfacenodes.add(interfacenode);
				System.out.println(interfacenodes.size());
			}
			for (ClassNode node : interfacenodes) {
				System.out.println("loop1");
				for (MethodNode method : (List<MethodNode>) node.methods) {
					System.out.println("loop2");
					for (String field : adapteesmap.keySet()) {
						System.out.println("loop3");
						List<String> attributes = new ArrayList<String>();
						for (Attribute at : (List<Attribute>) method.attrs) {
							System.out.println("loop4");
							System.out.println(at.type);
							attributes.add(at.type);
						}
						if (!attributes.contains(field)) {
							adapteesmap.put(field, false);
						}
					}
				}

			}

			// for every argument in <init> we must go through the method nodes
			// and find which one is used in every method or the method throws
			// an exception which matches the super
			// go to super make red mark as target, go to matching
			// overusedfieldclass and mark as adaptee

		}
		if (otherparser != null)
			result.append(this.otherparser.parse(nodes, relations, classinfo));
		return result.toString();
	}
}
