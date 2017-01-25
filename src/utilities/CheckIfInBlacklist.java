package utilities;

import java.util.List;

public class CheckIfInBlacklist {

	public static boolean checkIfInBlacklist(String className, List<String> blacklist){
		for(String s: blacklist){
			String fixed = s.replace(".", "/");
			if(className.contains(fixed))return true;
		}
		return false;
		
	}
}
