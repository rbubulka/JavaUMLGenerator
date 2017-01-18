package utilities;

import java.util.Arrays;
import java.util.List;

public class SignatureInterpreter {
	public void interpret(String s, List<String> res){

	if(!s.contains("<")){
		List<String> strs= Arrays.asList(s.split(";"));
		res.addAll(strs);
	}
	else{
		int count=0;
		String temp="";
		for(int i=0;i<s.length();i++){
			char c=s.charAt(i);
			switch (c) {
	         case '<':
	            count++;
	            String container=temp;
	            temp="";
	             break;
	         case '>':
	        	 count--;
	        	 interpret(temp, res);
	        	 temp="";
	        	 break;
	         case ';':
	        	 if (count%2==0){
	        		 interpret(temp, res);
	        		 temp="";
	        	 }else{
	        	 temp+=c;
	        	 }
	        	 break;
	         default:
	        	 temp+=c;
	        	 break;
	             
	     }
		}
	}
		
	}
}
