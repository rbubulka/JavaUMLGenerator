package utilities;

import java.util.Arrays;
import java.util.*;

public class SignatureInterpreter {
	public static void interpret(String s, List<String> res,HashMap<String,Boolean> hash,Boolean in) {

		if (s.contains("(")) {
			String arg = s.substring(s.indexOf("(") + 1, s.lastIndexOf(")"));
			String news = s.substring(s.lastIndexOf(")"));
			if (!arg.equals("")) {
				interpret(arg, res,hash,false);
			}
			interpret(news, res,hash,false);
		} else {

			if (!s.contains("<")) {
				List<String> strs = Arrays.asList(s.split(";"));
				res.addAll(strs);
				for(String sss:strs){
					if(sss.contains("[")){
						hash.put(sss,true);
					}else{
					hash.put(sss, in);
				}}
			} else {
				int count = 0;
				String temp = "";
				for (int i = 0; i < s.length(); i++) {
					char c = s.charAt(i);
					switch (c) {
					case '<':
						count++;
						String container = temp;
						temp = "";
						break;
					case '>':
						count--;
						interpret(temp, res,hash,true);
						temp = "";
						break;
					case ';':
						if (count % 2 == 0) {
							interpret(temp, res,hash,false);
							temp = "";
						} else {
							temp += c;
						}
						break;
					default:
						temp += c;
						break;

					}
				}
			}
		}

	}
}
