package parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import org.objectweb.asm.Type;

import jdk.management.resource.internal.TotalResourceContext;
import parsers.ClassParser.ClassParser;

public class CompositionOverInheritance extends ClassParser{

	public CompositionOverInheritance(ClassParser other) {
		super(other);
	}
	@Override
	public  String parse(List nodes, Set<String> relations){
		StringBuilder result = new StringBuilder();
		Set<String> toAdd=new HashSet<>();
		Set<String> toRemove=new HashSet<>();
		for(ClassNode cn : (List<ClassNode>) nodes ){
			List<String> ls=cn.interfaces;
			if(cn.superName!=null){
			ls.add(cn.superName);}
			List<ClassNode> nodels=new ArrayList<>();
			for(String classname:ls){
				ClassReader readertemp;
				try {
					readertemp = new ClassReader(classname);
					
					ClassNode classNodetemp = new ClassNode();
					readertemp.accept(classNodetemp, ClassReader.EXPAND_FRAMES);
					nodels.add(classNodetemp);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			for(ClassNode supercn:nodels){
					loop:
				for(MethodNode supermd:(List<MethodNode>)supercn.methods){
					if(supermd.name.equals("<init>")) continue;
					for(MethodNode md:(List<MethodNode>)cn.methods){
						if((md.desc.equals(supermd.desc))&&(md.name.equals(supermd.name))){
							if(!result.toString().contains("color=orange")){
							result.append("color=orange");}
							
							for(String relation:relations){
								String[] rels=relation.split(" ");
								if(rels[0].equals(cn.name)&&rels[4].equals(supercn.name)){
									toAdd.add(relation+" ,color=\"orange\"");
									toRemove.add(relation);
								}
							}
							break loop;
						}
					}
				}
			}
		}
		relations.removeAll(toRemove);
		relations.addAll(toAdd);
		if(otherparser !=  null){
			StringBuilder other = new StringBuilder( this.otherparser.parse(nodes, relations));
			if(other.toString().contains("color=")){
				int index = other.toString().indexOf("color=");
				other.insert(index+6, "orange:");
				result = other;
			}else{
			result.append(other);}
			};
		return result.toString();
	}

}
