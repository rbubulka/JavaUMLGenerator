package parsers.FieldParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.FieldNode;

import com.sun.javafx.scene.traversal.Hueristic2D;

import utilities.AvoidNonclassUtils;
import static utilities.SignatureInterpreter.interpret;
public abstract class FieldDependencyParser extends FieldsParser {

	public FieldDependencyParser(FieldsParser other) {
		super(other);
	}

	@Override
	public String parse(List fields, Set<String> relations, String classname,  List<HashMap<String, String>> classinfo) {
		StringBuilder result = new StringBuilder();
		for (FieldNode fn : (List<FieldNode>) fields) {
			if ((fn.access & opcode) > 0) {
				
				if (fn.signature != null) { // && fn.signature.contains("<")
//					addCollectionDependency(relations, classname, fn.signature);
//				} else {
					List<String> strs = new ArrayList<String>();
					HashMap<String,Boolean> hm=new HashMap<>();
					
					interpret(fn.signature,strs,hm,false);
					for(String s: strs)	{addDependency(relations, classname, s, hm.get(s));
					}
				}
				if(fn.signature==null){
					if(fn.desc.contains("[")){
					
						addDependency(relations, classname, fn.desc.replace(";", "").substring(1), true);
					}else{
					addDependency(relations, classname, fn.desc.replace(";", ""), false);}
				}
			}
		}
		if (otherparser != null)
			result.append(this.otherparser.parse(fields, relations, classname, classinfo));
		return result.toString();

	}


	private void addDependency(Set<String> relations, String classname, String type, boolean isCollection) {
		if(AvoidNonclassUtils.isAClass(type)){
		String num = "1 ";
		if (isCollection) {
			num = "* ";
		}
		relations.add(classname + " 1 hasa " + num + type.trim().replaceAll(";", ""));}
	}

}
