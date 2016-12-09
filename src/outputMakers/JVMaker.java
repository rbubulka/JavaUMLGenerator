package outputMakers;

import java.util.HashMap;
import java.util.List;

public class JVMaker implements OutputMaker {
	

	public void fileWrite(String fileName,  List<HashMap<String, String>> classdetails, List<String> relations) {
		System.out.println(fileName);
		for(HashMap<String,String> x:classdetails){
			for(String s:  x.keySet()){
				System.out.println(s+"     "+x.get(s));
			}
		}
		System.out.println("\n");
		for(String s:relations){
			System.out.println(s);
		}
		HashMap<String,String> details=new HashMap<>();
		details.put("implement", "[arrowhead = onormal, style = \"dashed\"]");
		details.put("extends", "[arrowhead=onormal]");
		for(String arrowstring:relations){
			String[] ls=arrowstring.split(" ");
			StringBuilder sb=new StringBuilder();
			sb.append(ls[0]+" -> "+ls[2]+" "+details.get(ls[1])+"\n");
			System.out.println(sb.toString());
		}
	
		
		
		
		
	}
}
