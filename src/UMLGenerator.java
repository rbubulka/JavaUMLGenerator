import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.objectweb.asm.tree.ClassNode;

import NodeGetter.ClassFileGetter;
import NodeGetter.FileGetter;
import parsers.*;
import printing.Printer;

public class UMLGenerator {

	private FileGetter parser=new ClassFileGetter();
//	private Printer gvprinter;
	private MethodsPpp methodppp=new PublicMethodsPpp();
	private FieldsPpp fieldppp=new PublicFieldsPpp();

	private ArrayList<String> classnames=new ArrayList<String>();
	private HashMap<String,Object> argsmap=new HashMap<String,Object>(){{
	    put("-publicMethod",new PublicMethodsPpp());
	    put("-protectedMethod",new ProtectedMethodsPpp());
	    put("-privateMethod",new PrivateMethodsPpp());
	    put("-publicField",new PublicFieldsPpp());
	    put("-protectedField",new ProtectedFieldsPpp());
	    put("-privateField",new PrivateFieldsPpp());
	}};

	public UMLGenerator(String[] args) throws Exception{
		
		for(String a:args){
			if(!this.argsmap.containsKey(a)){
				this.classnames.add(a);
			}
			else{
				if(a.contains("Method")){
					methodppp=(MethodsPpp) this.argsmap.get(a);
				}
				else if(a.contains("Field")){
					fieldppp=(FieldsPpp) this.argsmap.get(a);
				}
				else{
					throw new Exception("wrong args");
				}
				
				
			}
			NodeRelation nodeRelations = this.getNodes();
			Set<ClassNode> nodelist= nodeRelations.getNodes();
			List<String> relations = nodeRelations.getRelations();
		}
		
		
	}
	public void parseNodes(ArrayList<ClassNode> nodelist){
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
