package NodeGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import static utilities.CheckIfInBlacklist.checkIfInBlacklist;
public class ClassFileGetter implements FileGetter {

	@Override
	public void addClasses(ArrayList<String> classnames, Set<ClassNode> nodes, Set<String> relations,
			boolean recursive,List<String> blacklist) throws IOException {
		for (String cname : classnames) {
			if(!blacklist.contains(cname)){
				System.out.println(cname);
			ClassReader reader = new ClassReader(cname);
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.EXPAND_FRAMES);
			nodes.add(classNode);}
		}
		HashSet<String> visited = new HashSet<String>();
		Stack<ClassNode> st = new Stack<>();
		st.addAll(nodes);
		while (!st.isEmpty()) {
			ClassNode v = st.pop();
			if (!visited.contains(v.name)) {
				visited.add(v.name);
				nodes.add(v);
				for (String cns : (List<String>) v.interfaces) {
					if (cns != null && !checkIfInBlacklist(cns, blacklist)) {
						if (recursive) {
							System.out.println(cns.replaceAll("/", "."));
							ClassReader cr = new ClassReader(cns.replaceAll("/", "."));
							ClassNode cn = new ClassNode();
							cr.accept(cn, ClassReader.EXPAND_FRAMES);
							st.push(cn);
						}
						relations.add(v.name + " 1 implements 1 " + cns);

					}}
					if (v.superName != null&& !checkIfInBlacklist(v.superName, blacklist)) {
						if (recursive) {
							System.out.println(v.superName.replaceAll("/", "."));
							ClassReader readertemp = new ClassReader(v.superName.replaceAll("/", "."));
							ClassNode classNodetemp = new ClassNode();
							readertemp.accept(classNodetemp, ClassReader.EXPAND_FRAMES);
							st.push(classNodetemp);
						}
						relations.add(v.name + " 1 extends 1 " + v.superName);
					}
				}
			
		}
		
	}

	
}
