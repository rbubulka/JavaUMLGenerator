package parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

import parsers.ClassParser.ClassParser;

public class CompositionOverInheritance extends ClassParser{

	public CompositionOverInheritance(ClassParser other) {
		super(other);
	}
	@Override
	public  String parse(List nodes, Set<String> relations){
		StringBuilder result = new StringBuilder();
		loop:
		for(ClassNode cn : (List<ClassNode>) nodes ){
			List<String> ls=cn.interfaces;
			ls.add(cn.superName);
			List<ClassNode> nodels=new ArrayList<>();
			for(String classname:ls){
				ClassReader readertemp;
				try {
					readertemp = new ClassReader(classname);
					ClassNode classNodetemp = new ClassNode();
					readertemp.accept(classNodetemp, ClassReader.EXPAND_FRAMES);
					nodels.add(classNodetemp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			for(ClassNode supercn:nodels){
				for(MethodNode supermd:(List<MethodNode>)supercn.methods){
					for(MethodNode md:(List<MethodNode>)cn.methods){
						if(md.desc.equals(supermd.desc)){
							result.append(", color= \"orange\"");
							break loop;
						}
					}
				}
			}
		}
		
		if(otherparser !=  null)result.append(this.otherparser.parse(nodes, relations));
		return result.toString();
	}

}
