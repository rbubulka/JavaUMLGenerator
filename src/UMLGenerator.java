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
import parserMakers.*;
import parsers.*;
import parsers.ClassParser.*;
import parsers.FieldParser.*;
import parsers.MethodParser.*;
import parsers.MethodParser.PublicMethodsParser;
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


	public UMLGenerator(String[] args) throws Exception {
		for (String a : args) {
			if (a.equals("-publicClass")) {
				ClassParserMaker.getInstance().setPublicFields(true);
			} else if(a.equals("-protectedClass")){
				ClassParserMaker.getInstance().setProtectedFields(true);
			} else if(a.equals("-fieldDependency")){
				FieldParserMaker.getInstance().setDependecies(true);
			} else if (a.equals("-publicField")) {
				FieldParserMaker.getInstance().setPublicFields(true);
			} else if(a.equals("-protectedField")){
				FieldParserMaker.getInstance().setProtectedFields(true);
			} else if(a.equals("-privateField")){
				FieldParserMaker.getInstance().setPrivateFields(true);
			} else if(a.equals("-fieldDependency")){
				FieldParserMaker.getInstance().setDependecies(true);
			} else if (a.equals("-publicMethod")) {
				MethodParserMaker.getInstance().setPublicFields(true);
			} else if(a.equals("-protectedMethod")){
				MethodParserMaker.getInstance().setProtectedFields(true);
			} else if(a.equals("-privateMethod")){
				MethodParserMaker.getInstance().setPrivateFields(true);
			} else if(a.equals("-MRD")){
				MethodParserMaker.getInstance().setDependecies(true);
			} else if(a.equals("MID")){
				MethodParserMaker.getInstance().setInstructions(true);
			}
			else if (a.equals("-GVM")) {
				outputmaker = new GVMaker();
			} else if (a.equals("-recursive")) {
				this.recursive = true;
			} else if (a.contains("-o=")) {
				this.output = a.substring(3);
			} else {
				this.classnames.add(a);
			}

		}
		MethodsParser mp = MethodParserMaker.getInstance().makeParser();
		if(mp != null){
			 this.methodparser = mp;
		}
		FieldsParser fp = FieldParserMaker.getInstance().makeParser();
		if(fp != null){
			this.fieldparser = fp;
		}
		ClassParser cp = ClassParserMaker.getInstance().makeParser();
		if(cp != null){
			this.classparser = cp;
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
		this.parser.addClasses(this.classnames, nodes, relations, recursive);
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
