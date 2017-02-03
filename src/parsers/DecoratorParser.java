package parsers;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import parsers.ClassParser.ClassParser;

public class DecoratorParser extends ClassParser {

	public DecoratorParser(ClassParser other) {
		super(other);
	}
	
	@Override
	public  String parse(List nodes, Set<String> relations, List<HashMap<String, String>> classinfo){
		StringBuilder result = new StringBuilder();
		for(ClassNode cn : (List<ClassNode>) nodes ){
			//go all the way up inheritence until does not share all methods
			//if cn.methodNodes methodname=<init> contains arguement.getclass == cn.classname mark true takes self
			//else if <init> contains cn.interface mark true takes super
			//if cn.fields contains interface.type then mark true selffield
			//if takesself&&selffield then component 
			//if takessuper&&selffield then decorator
			
			
		}
		if(otherparser !=  null)result.append(this.otherparser.parse(nodes, relations, classinfo));
		return result.toString();
	}
}
