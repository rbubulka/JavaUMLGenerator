package outputMakers;

import java.util.HashMap;
import java.util.List;

public class JVMaker implements OutputMaker {
	

	public void fileWrite(String fileName,  List<HashMap<String, String>> classdetails, List<String> relations) {
	
		StringBuilder classDetailString=new StringBuilder();
		HashMap<String,String> ppp=new HashMap<>();
		ppp.put("public", "+");
		ppp.put("private", "-");
		ppp.put("protected", "#");
		for(HashMap<String,String> map:classdetails){
		//class
		String classinfo=map.get("Class");
		if(classinfo==null||classinfo.equals("")){
			continue;
		}
		//split the class info on for [ppp, classname,interface,isInterface,abstract,isAbstract]
		String[] splited=classinfo.split(" ");
		classDetailString.append("\""+splitclassname(splited[1])+"\" [\n" );
		classDetailString.append("shape=\"record\",\n");
		classDetailString.append("label= \"{");
		//check if abstract
		String temp1="";
		String temp2="";
		if(splited[5].equals("true")){
			temp1="<I>";
			temp2="</I>";
		}
		//check if interface
				if(splited[3].equals("true")){
					classDetailString.append("<<interface>>\n");
				}
		//add in class name to the uml object
		classDetailString.append(temp1+splitclassname(splited[1])+temp2);
		classDetailString.append("|");
		//field
		String fieldinfo=map.get("Field");
		if(fieldinfo!=null&&!fieldinfo.equals("")){
		String[] splitedfield=fieldinfo.split(" ");
		classDetailString.append(ppp.get(splitedfield[0]));
		classDetailString.append(splitedfield[1]+":"+splitclassname(splitedfield[2])+"\n");
		}
		//close label
		classDetailString.append("}\"");
		//close class
		classDetailString.append("];\n\n");
		
		}
		
		
		
		HashMap<String,String> details=new HashMap<>();
		details.put("implements", "[arrowhead = onormal, style = \"dashed\"]");
		details.put("extends", "[arrowhead=onormal]");
		StringBuilder relationstring = generateRelationsString(relations, details);
	
		System.out.println(classDetailString.toString());
		System.out.println(relationstring.toString());
		
	}


	private StringBuilder generateRelationsString(List<String> relations, HashMap<String, String> details) {
		StringBuilder relationstring=new StringBuilder();
		for(String arrowstring:relations){
			String[] ls=arrowstring.split(" ");
			relationstring.append(ls[0]+" -> "+ls[2]+" "+details.get(ls[1])+"\n");

		}
		return relationstring;
	}
	
	
	public String splitclassname(String in){
		String[] result=in.split("/");
		return result[result.length-1].replaceAll("\\W", "");
	}
	
	

	public void printcheck(String fileName,  List<HashMap<String, String>> classdetails, List<String> relations){
		for(HashMap<String,String> x:classdetails){
			for(String s:  x.keySet()){
				System.out.println(s+"     "+x.get(s));
			}
		}
		System.out.println("\n");
		for(String s:relations){
			System.out.println(s);
		}
	}
}
