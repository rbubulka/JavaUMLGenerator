import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.objectweb.asm.tree.ClassNode;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import NodeGetter.ClassFileGetter;
import NodeGetter.FileGetter;
import parsers.*;
import printing.Printer;

public class UMLGenerator {

	private FileGetter parser=new ClassFileGetter();
//	private Printer gvprinter;
	private MethodsParser methodparser=new PublicMethodsParser(null);
	private FieldsParser fieldparser=new PublicFieldsParser(null);
	private ClassParser classparser=new PublicClassParser(null);

	private ArrayList<String> classnames=new ArrayList<String>();
	private HashMap<String,Object> argsmap=new HashMap<String,Object>(){{
	    put("-publicMethod",new PublicMethodsParser(null));
	    put("-protectedMethod",new ProtectedMethodsParser(new PublicMethodsParser(null)));
	    put("-privateMethod",new PrivateMethodsParser(new ProtectedMethodsParser(new PublicMethodsParser(null))));
	    put("-publicField",new PublicFieldsParser(null));
	    put("-protectedField",new ProtectedFieldsParser(new PublicFieldsParser(null)));
	    put("-privateField",new PrivateFieldsParser(new ProtectedFieldsParser(new PublicFieldsParser(null))));
	    put("-publicClass",new PublicClassParser(null));
	    put("-protectedClass",new ProtectedClassParser(new PublicClassParser(null)));
	}};

	public UMLGenerator(String[] args) throws Exception{
		
		for(String a:args){
			if(!this.argsmap.containsKey(a)){
				this.classnames.add(a);
			}
			else{
				if(a.contains("Method")){
					methodparser=(MethodsParser) this.argsmap.get(a);
				}
				else if(a.contains("Field")){
					fieldparser=(FieldsParser) this.argsmap.get(a);
				}
				else if(a.contains("Class")){
					classparser=(ClassParser) this.argsmap.get(a);
				}
				else{
					throw new Exception("wrong args");
				}
				
				
			}
			NodeRelation nodeRelations = this.getNodes();
			Set<ClassNode> nodelist= nodeRelations.getNodes();
			List<String> relations = nodeRelations.getRelations();

			NodeParseToUML nptu=new NodeParseToUML(this.methodparser, this.fieldparser, this.classparser, null);
			List<HashMap<String,String>> parsedstring=nptu.doParse(nodelist);
			
		}
		
		
	}

		
		
	
	public NodeRelation getNodes() throws IOException{
		TreeSet<ClassNode> nodes=new TreeSet<ClassNode>();
		ArrayList<String> relations = new ArrayList<>();
		this.parser.addClasses(this.classnames,nodes, relations);
		return new NodeRelation(nodes, relations);
		
	}
	private class NodeRelation {
		Set<ClassNode> nodes;
		List<String> relations;
		public NodeRelation(Set<ClassNode> nodes, List<String> relations){
			this.relations = relations;
			this.nodes =  nodes;
		}
		
		public Set<ClassNode> getNodes(){
			return this.nodes;
		}
		public List<String> getRelations(){
			return this.relations;
		}
		
	}

}
