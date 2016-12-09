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
	private MethodsPpp methodppp=new PublicMethodsPpp(null);
	private FieldsPpp fieldppp=new PublicFieldsPpp(null);

	private ArrayList<String> classnames=new ArrayList<String>();
	private HashMap<String,Object> argsmap=new HashMap<String,Object>(){{
	    put("-publicMethod",new PublicMethodsPpp(null));
	    put("-protectedMethod",new ProtectedMethodsPpp(new PublicMethodsPpp(null)));
	    put("-privateMethod",new PrivateMethodsPpp(new ProtectedMethodsPpp(new PublicMethodsPpp(null))));
	    put("-publicField",new PublicFieldsPpp(null));
	    put("-protectedField",new ProtectedFieldsPpp(new PublicFieldsPpp(null)));
	    put("-privateField",new PrivateFieldsPpp(new ProtectedFieldsPpp(new PublicFieldsPpp(null))));
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
			String infostring=parseNodes(nodelist);
		}
		
		
	}
	public String parseNodes(Set<ClassNode> nodes ){
		StringBuilder result=new StringBuilder();
		for(ClassNode cn : nodes){
		if((cn.access&Opcodes.ACC_PUBLIC)>0){
			result.append("classinfo\n"+"public "+cn.name+"\n");}
		else{
			result.append("classinfo\n"+"protected "+cn.name+"\n");}
		
			result.append("fieldinfo\n"+this.fieldppp.parse(cn.fields)+"\n");
			result.append("methodinfo\n"+this.methodppp.parse(cn.methods)+"\n");
			result.append("end\n");
		}
		return result.toString();
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
