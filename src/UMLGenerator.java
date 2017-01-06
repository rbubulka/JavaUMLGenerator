import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.objectweb.asm.tree.ClassNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import NodeGetter.ClassFileGetter;
import NodeGetter.FileGetter;
import outputMakers.GVMaker;
import outputMakers.OutputMaker;
import parsers.*;
import printing.Printer;

public class UMLGenerator {

	private FileGetter parser = new ClassFileGetter();
	private MethodsParser methodparser = new PublicMethodsParser(null);
	private FieldsParser fieldparser = new PublicFieldsParser(null);
	private ClassParser classparser = new PublicClassParser(null);
	private OutputMaker outputmaker = new GVMaker();
	private String output = ".\\Documents\\output.dot";
	private boolean recursive = false;

	private ArrayList<String> classnames = new ArrayList<String>();
	private HashMap<String, Object> argsmap = new HashMap<String, Object>() {
		{
			put("-publicMethod", new PublicMethodsParser(null));
			put("-protectedMethod", new ProtectedMethodsParser(new PublicMethodsParser(null)));
			put("-privateMethod", new PrivateMethodsParser(new ProtectedMethodsParser(new PublicMethodsParser(null))));
			put("-noMethod", new NoMethod(null));
			put("-publicField", new PublicFieldsParser(null));
			put("-protectedField", new ProtectedFieldsParser(new PublicFieldsParser(null)));
			put("-privateField", new PrivateFieldsParser(new ProtectedFieldsParser(new PublicFieldsParser(null))));
			put("-noField", new NoField(null));
			put("-publicClass", new PublicClassParser(null));
			put("-protectedClass", new ProtectedClassParser(new PublicClassParser(null)));
			put("-GVMaker", new GVMaker());
			put("-recursive", null);
			put("-o=", null);
		}
	};

	public UMLGenerator(String[] args) throws Exception {

		for (String a : args) {
			if (this.argsmap.containsKey(a)) {
				if (a.contains("Method")) {
					methodparser = (MethodsParser) this.argsmap.get(a);
				} else if (a.contains("Field")) {
					fieldparser = (FieldsParser) this.argsmap.get(a);
				} else if (a.contains("Class")) {
					classparser = (ClassParser) this.argsmap.get(a);
				} else if (a.contains("GVM")) {
					outputmaker = (GVMaker) this.argsmap.get(a);
				} else if(a.contains("-recursive")){
					this.recursive = true;
				}
			} else if(a.contains("-o=")){
					this.output = a.substring(3);				
			}else{
			
				this.classnames.add(a);
			}

		}
		NodeRelation nodeRelations = this.getNodes(this.recursive);
		Set<ClassNode> nodelist = nodeRelations.getNodes();
		Set<String> relations = nodeRelations.getRelations();

		NodeParseToUML nptu = new NodeParseToUML(this.methodparser, this.fieldparser, this.classparser, null);
		List<HashMap<String, String>> parsedstring = nptu.doParse(nodelist, relations);
		this.outputmaker.fileWrite(this.output, parsedstring, relations);

	}

	public NodeRelation getNodes(boolean recursive) throws IOException {
		HashSet<ClassNode> nodes = new HashSet<ClassNode>();
		Set<String> relations = new HashSet<>();
		this.parser.addClasses(this.classnames, nodes, relations,recursive);
		return new NodeRelation(nodes, relations);

	}

	private class NodeRelation {
		Set<ClassNode> nodes;
		Set<String> relations;

		public NodeRelation(Set<ClassNode> nodes, Set<String> relations) {
			this.relations = relations;
			this.nodes = nodes;
		}

		public Set<ClassNode> getNodes() {
			return this.nodes;
		}

		public Set<String> getRelations() {
			return this.relations;
		}

	}

}
