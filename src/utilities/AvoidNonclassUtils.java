package utilities;

import java.util.Arrays;
import java.util.List;

public class AvoidNonclassUtils {
	
	private static String[] nonclass = {"B","C","D","F","I","J","S","Z","V",">","*", "JI",")V",")I"};
	private static List<String> classes = Arrays.asList(nonclass);
	public static boolean isAClass(String signature){
		if(signature == null||signature.equals("")) return false;
		int index = 0;
		while(signature.charAt(index)== '['){
			index++;
		}
		return(!classes.contains(signature.substring(index)));
	}
}
