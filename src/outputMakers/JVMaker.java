package outputMakers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class JVMaker implements OutputMaker {

	public void fileWrite(String filePath, List<HashMap<String, String>> classdetails, List<String> relations) throws IOException {

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
			classDetailString.append("\"" + splitclassname(splited[1]) + "\" [\n");
			classDetailString.append("shape=\"record\",\n");
			classDetailString.append("label= \"{");
			// check if abstract
			String temp1 = "";
			String temp2 = "";
			if (splited[5].toLowerCase().trim().equals("true")&&splited[3].toLowerCase().trim().equals("false")) {
				temp1 = "\\<I\\>";
				temp2 = "\\</I\\>";
			}
			// check if interface
			if (splited[3].equals("true")) {
				classDetailString.append("\\<\\<interface\\>\\>\\n");
			}
			// add in class name to the uml object
			classDetailString.append(temp1 + splitclassname(splited[1]) + temp2);
			classDetailString.append("|\\l");
			// field
			String fieldinfo = map.get("Field");
			String[] fields = fieldinfo.split("\n");
			for (String field : fields) {
				if (field != null && !field.equals("")) {
					String[] splitedfield = field.split(" ");
					classDetailString.append(ppp.get(splitedfield[0]));
					classDetailString.append(splitclassname(splitedfield[1]) + " : " + splitclassname(splitedfield[2]) + "\\l");
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
					classDetailString.append(splitedmethod[1].replaceAll("<", "\\\\<").replaceAll(">", "\\\\>"));
					if(!splitedmethod[2].equals("null")){
					String[] inputAndOther=splitedmethod[2].split(Pattern.quote(")"));
					String[] inputs=inputAndOther[0].split(";");
					classDetailString.append("(");
					for(String input:inputs){
	classDetailString.append(this.splitclassname(input));}
//					classDetailString.append(inputAndOther[0].replaceAll(";", " "));
					classDetailString.append(")");
					classDetailString.append(" : ");
					classDetailString.append(this.splitclassname(inputAndOther[1]));}
					classDetailString.append("\\l");
				}
			}
			
			// close label
			classDetailString.append("}\"");
			// close class
			classDetailString.append("];\n\n");

		}

		HashMap<String, String> details = new HashMap<>();
		details.put("implements", "[arrowhead = \"onormal\", style = \"dashed\"]");
		details.put("extends", "[arrowhead=\"onormal\"]");
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
	}

	private StringBuilder generateRelationsString(List<String> relations, HashMap<String, String> details) {
		StringBuilder relationstring = new StringBuilder();
		for (String arrowstring : relations) {
			String[] ls = arrowstring.split(" ");
			relationstring.append(splitclassname(ls[0]) + " -> " + splitclassname(ls[2]) + " " + details.get(ls[1]) + ";\n");

		}
		return relationstring;
	}

	public String splitclassname(String in) {
		String[] result = in.split("/");
		return result[result.length - 1].replaceAll("\\W", "");
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
