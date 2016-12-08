package NodeGetter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
public class ClassFileGetter implements FileGetter {


	@Override
	public void addClasses(ArrayList<String> classnames,Set<ClassNode> nodes, List<String> relations) throws IOException {
		for(String cname:classnames){
			ClassReader reader = new ClassReader(cname);
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.EXPAND_FRAMES);
			nodes.add(classNode);
		}
		Set<String> visited=new TreeSet<String>();
		Stack<ClassNode> st=new Stack<>();
		st.addAll(nodes);
		while(!st.isEmpty()){
			ClassNode v=st.pop();
			if (!visited.contains(v.name)){
				visited.add(v.name);
				nodes.add(v);
				for(ClassNode cn: (List<ClassNode>)v.interfaces){
					st.push(cn);
					relations.add(v.name + "implements" + cn.name);
					ClassReader readertemp = new ClassReader(v.superName);
					ClassNode classNodetemp = new ClassNode();
					readertemp.accept(classNodetemp, ClassReader.EXPAND_FRAMES);
					st.push(classNodetemp);
					relations.add(v.name + "extends" + v.superName);
				}
			}
		}
		
		
		
		
	}

}
