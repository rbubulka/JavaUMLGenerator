package parsers;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import parsers.ClassParser.ClassParser;

public class AdapterParser extends ClassParser {

	public AdapterParser(ClassParser other) {
		super(other);
	}
	
	@Override
	public  String parse(List nodes, Set<String> relations, List<HashMap<String, String>> classinfo){
		StringBuilder result = new StringBuilder();
		for(ClassNode cn : (List<ClassNode>) nodes ){
			
			//check if has interface
			//for every argument in <init> we must go through the method nodes and find which one is used in every method or the method throws an exception which matches the super
			//go to super make red mark as target, go to matching overusedfieldclass and mark as adaptee
			
		}
		if(otherparser !=  null)result.append(this.otherparser.parse(nodes, relations, classinfo));
		return result.toString();
	}
}
