package outputMakers;

import java.util.HashMap;
import java.util.List;

public class JVMaker implements OutputMaker {
	

	public void fileWrite(String fileName,  List<HashMap<String, String>> classdetails, List<String> relations) {
//		for(HashMap<String,String> x:classdetails){
//			for(String s:  x.keySet()){
//				System.out.println(s+"     "+x.get(s));
//			}
//		}
//		System.out.println("\n");
//		for(String s:relations){
//			System.out.println(s);
//		}
	
		StringBuilder classstring=new StringBuilder();
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
		String[] splited=classinfo.split(" ");
		if(splited[3].equals("true")){
			classstring.append("<<interface>>\n");
		}
		
		String temp1="";
		String temp2="";
		if(splited[5].equals("true")){
			temp1="<I>";
			temp2="</I>";
		}
		classstring.append(temp1+splitclassname(splited[1])+temp2);
		classstring.append("|");
		//field
		String fieldinfo=map.get("Field");
		if(fieldinfo!=null&&!fieldinfo.equals("")){
		String[] splitedfield=fieldinfo.split(" ");
		classstring.append(ppp.get(splitedfield[0]));
		classstring.append(splitedfield[1]+":"+splitclassname(splitedfield[2])+"\n");
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		classstring.append("name"+"{" );
		classstring.append("shape=\"record\",\n");
		classstring.append("label="+"babab");
		
		classstring.append("};\n");
		
		System.out.println(classstring.toString());
		}
		
		
		
		HashMap<String,String> details=new HashMap<>();
		details.put("implement", "[arrowhead = onormal, style = \"dashed\"]");
		details.put("extends", "[arrowhead=onormal]");
		for(String arrowstring:relations){
			String[] ls=arrowstring.split(" ");
			StringBuilder relationstring=new StringBuilder();
			relationstring.append(ls[0]+" -> "+ls[2]+" "+details.get(ls[1])+"\n");

		}
	
		
		
		
		
	}
	
	
	public String splitclassname(String in){
		String[] result=in.split("/");
		return result[result.length-1].replaceAll("\\W", "");
	}
	
	
	
}
