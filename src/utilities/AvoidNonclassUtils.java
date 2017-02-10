package utilities;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class AvoidNonclassUtils {
	
	private static String[] nonclass = {"B","C","D","F","I","J","S","Z","V",">","*", "JI",")V",")I",")Z","IIIII",")F","ZZ","JJ","BB","SS","CC","FF","DD","TT",")J","ZJ","IZ",")TT",")TE",")C","BII","CII","II","TE"};
	private static List<String> classes = Arrays.asList(nonclass);
	public static boolean isAClass(String signature){
		//Pattern pattern = new Pattern("");
		if(signature == null||signature.equals("")) return false;
		int index = 0;
		while(signature.charAt(index)== '['){
			index++;
		}
		return(!classes.contains(signature.substring(index)));
	}
}
