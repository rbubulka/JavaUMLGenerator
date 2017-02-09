package parsers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import parsers.ClassParser.ClassParser;

public class DIPParser extends ClassParser {

	public DIPParser(ClassParser other) {
		super(other);
	}

	@Override
	public String parse(List nodes, Set<String> relations,  List<HashMap<String, String>> classinfo){
		StringBuilder result = new StringBuilder();
		for (ClassNode cn : (List<ClassNode>) nodes) {
			List<FieldNode> fields = cn.fields;
			for(FieldNode field: fields){
				ClassReader cr = null;
				
				try {
					cr = new ClassReader(field.desc.replaceAll("/", ".").substring(1).replaceAll(";", ""));
				} catch (IOException e) {
					e.printStackTrace();
				}
				ClassNode fieldclass = new ClassNode();
				cr.accept(fieldclass, ClassReader.EXPAND_FRAMES);
				if(!((fieldclass.access&Opcodes.ACC_ABSTRACT) >0)||((fieldclass.access&Opcodes.ACC_INTERFACE) >0)){
					for (HashMap<String, String> hashmap : classinfo) {
						if (hashmap.get("Class").contains(cn.name)) {
							String details = hashmap.get("Details");
							if (details == null)
								details = "";
							if (!details.contains("fillcolor")) {
								hashmap.put("Details",
										details + ",fillcolor=\"deeppink\"" + ",style=\"filled\"");

							}
						}
					}
					
					
					
					break;
				}
			}
			//make pink
		}
	return result.toString();	
	}
}
 