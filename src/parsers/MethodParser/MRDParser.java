package parsers.MethodParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.MethodNode;

public abstract class MRDParser extends MethodsParser {

	public MRDParser(MethodsParser other) {
		super(other);
		// TODO Auto-generated constructor stub
	}
	@Override
	public  String parse(List methods, Set<String> relations,String name){
		StringBuilder result= new StringBuilder();
		for(MethodNode md:(List<MethodNode>)methods){
			if((md.access&opcode)>0){
//				result.append(text+" " + md.name +" "+ md.signature + "\n");
				String start=md.desc;
				String[] startarray=start.split("\\)");
				String arg=startarray[0].replaceAll("\\(", "");
			
				String returtype=startarray[1];
				String[] argls=arg.split(";");
				List<String> temp=new ArrayList<>();
				for(String s:argls){
					temp.add(s);
				}
				temp.add(returtype);
				for(String s:temp){
					String hasNumber="1";
					if(s.length()>0){
					if(s.charAt(0)=='['){
						hasNumber="*";
					}
					relations.add(name+ " 1 usea "+hasNumber+" "+splitclassname(s));
				}}
			}
		}
		if(otherparser !=  null)result.append(this.otherparser.parse(methods, relations, name));
		return result.toString();
	}
	
	public String splitclassname(String in) {
		String[] result = in.split("/");
		return result[result.length - 1].replaceAll("//W", "").replaceAll(";", "").replaceAll("\\[", "");
	}

}
