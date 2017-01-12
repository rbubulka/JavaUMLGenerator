package outputMakers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import printing.*;

public class GVMaker implements OutputMaker {
	private Printer printer=new GVFilePrinter();;
	public void fileWrite(String filePath, List<HashMap<String, String>> classdetails, Set<String> relations) throws IOException {

		StringBuilder classDetailString = new StringBuilder();
		HashMap<String, String> ppp = new HashMap<>();
		ppp.put("public", "+");
		ppp.put("private", "-");
		ppp.put("protected", "#");
		for (HashMap<String, String> map : classdetails) {
			// class
			String classinfo = map.get("Class");
			if (classinfo == null || classinfo.equals("")) {
				continue;
			}
			// split the class info on for [ppp,
			// classname,interface,isInterface,abstract,isAbstract]
			String[] splited = classinfo.split(" ");
//			classDetailString.append("\"" + splited[1].replaceAll("<", "&#60;").replaceAll(">", "&#62;").replaceAll("/", "&#47;") + "\" [\n");
			classDetailString.append("\"" + splitclassname(splited[1]) + "\" [\n");
			classDetailString.append("shape=\"record\",\n");
			classDetailString.append("label= <{");
			// check if abstract
			String temp1 = "";
			String temp2 = "";
			if (splited[5].toLowerCase().trim().equals("true")&&splited[3].toLowerCase().trim().equals("false")) {
					temp1 = "<i>";
					temp2 = "</i>";
			}
			// check if interface
			if (splited[3].equals("true")) {
				classDetailString.append("&#60;&#60;interface&#62;&#62;<br/>");
			}
			// add in class name to the uml object
//			classDetailString.append(temp1 + splited[1].replaceAll("<", "&#60;").replaceAll(">", "&#62;").replaceAll("/", "&#47;") + temp2);
			classDetailString.append(temp1 + splitclassname(splited[1]) + temp2);
			classDetailString.append("|");
			// field
			String fieldinfo = map.get("Field");
			String[] fields = fieldinfo.split("\n");
			for (String field : fields) {
				if (field != null && !field.equals("")) {
					String[] splitedfield = field.split(" ");
					classDetailString.append(ppp.get(splitedfield[0]));
					classDetailString.append(splitedfield[2].replaceAll("<", "&#60;").replaceAll(">", "&#62;").replaceAll("/", "&#47;") + " : " + splitedfield[1].replaceAll("<", "&#60;").replaceAll(">", "&#62;").replaceAll("/", "&#47;") + "<br align=\"left\"/>");
//					classDetailString.append(splitclassname(splitedfield[1]) + " : " + splitclassname(splitedfield[2]) + "<br align=\"left\"/>");
				}
				
			}
			
			classDetailString.append("|");
			//methods
			String methodinfo=map.get("Method");
			String[] methods=methodinfo.split("\n");
			for(String method:methods){
				if(method!=null && !method.equals("")){
					String[] splitedmethod=method.split(" ");
					classDetailString.append(ppp.get(splitedmethod[0]));
					for(int i=1;i<splitedmethod.length;i++){
						String s=splitedmethod[i];
					
						if (!s.equals("null")){
						
						 
						classDetailString.append(s.replaceAll("<", "&#60;").replaceAll(">", "&#62;").replaceAll("/", "&#47;")+" ");}
					}
//					classDetailString.append(ppp.get(splitedmethod[0]));
//					classDetailString.append(splitedmethod[1].replaceAll("<", "&#60;").replaceAll(">", "&#62;").replaceAll("/", "&#47;"));
//					if(!splitedmethod[2].equals("null")){
//					String[] inputAndOther=splitedmethod[2].split(Pattern.quote(")"));
//					String[] inputs=inputAndOther[0].split(";");
//					classDetailString.append("(");
//					for(String input:inputs){
//	classDetailString.append(input.replaceAll("<", "&#60;").replaceAll(">", "&#62;").replaceAll("/", "&#47;"));}
////					classDetailString.append(this.splitclassname(input));}
////					classDetailString.append(inputAndOther[0].replaceAll(";", " "));
//					classDetailString.append(")");
//					classDetailString.append(" : ");
//					classDetailString.append(inputAndOther[1].replaceAll("<", "&#60;").replaceAll(">", "&#62;").replaceAll("/", "&#47;"));}
////					classDetailString.append(this.splitclassname(inputAndOther[1]));}
					classDetailString.append("<br align=\"left\"/>");
				}
			}
			
			// close label
			classDetailString.append("}>");
			// close class
			classDetailString.append("];\n\n");

		}

		HashMap<String, String> details = new HashMap<>();
		details.put("implements", "[arrowhead = \"onormal\", style = \"dashed\"");
		details.put("extends", "[arrowhead=\"onormal\"");
		details.put("hasa", "[arrowhead=\"vee\"");
		details.put("usea", "[arrowhead=\"vee\", style=\"dashed\"");
		details.put("bothhasa", "[arrowhead=\"vee\", dir=\"both\"");
		details.put("bothusea", "[arrowhead=\"vee\", style=\"dashed\", dir=\"both\"");
		StringBuilder relationstring = generateRelationsString(relations, details);

//		 System.out.println(classDetailString.toString());
		// System.out.println(relationstring.toString());
		File file = new File(filePath);
		FileOutputStream fop = new FileOutputStream(file);
		if (!file.exists()) {
			file.createNewFile();
		}
		byte[] contentInBytes = ("digraph G { rankdir=BT;\n"+classDetailString.toString()+relationstring.toString()+"}").getBytes();

		fop.write(contentInBytes);
		fop.flush();
		fop.close();
		//printer.print(filePath);
		
	}

	private StringBuilder generateRelationsString(Set<String> relations, HashMap<String, String> details) {
		StringBuilder relationstring = new StringBuilder();
		for (String arrowstring : relations) {
			String[] ls = arrowstring.split(" ");
//			relationstring.append(ls[0].replaceAll("<", "&#60;").replaceAll(">", "&#62;").replaceAll("/", "&#47;") + " -> " + ls[2].replaceAll("<", "&#60;").replaceAll(">", "&#62;").replaceAll("/", "&#47;") + " " + details.get(ls[1]) + ";\n");
			relationstring.append(splitclassname(ls[0]).replaceAll("\\$", "") + " -> " + splitclassname(ls[4]).replaceAll("\\$", "") + " " + details.get(ls[2]) + ",label=\""+ls[1]+"..."+ls[3]+"\" "+"];\n");
			

		}
		return relationstring;
	}

	public String splitclassname(String in) {
		String[] result = in.split("/");
		String temp=result[result.length-1];
		if(temp.charAt(0)!='"' && temp.charAt(temp.length()-1)=='"'){
			return "\""+result[result.length - 1].replaceAll("//W", "");
		}
		return result[result.length - 1].replaceAll("//W", "");
	}

	public void printcheck(String fileName, List<HashMap<String, String>> classdetails, List<String> relations) {
		for (HashMap<String, String> x : classdetails) {
			for (String s : x.keySet()) {
				System.out.println(s + "     " + x.get(s));
			}
		}
		System.out.println("\n");
		for (String s : relations) {
			System.out.println(s);
		}
	}
}
