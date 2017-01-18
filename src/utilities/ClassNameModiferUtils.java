package utilities;

public class ClassNameModiferUtils {
	public static String splitclassname(String in) {
		if(!in.contains("/")) return in;
		String[] result = in.split("/");
		String temp=result[result.length-1];
		if(temp.charAt(0)!='"' && temp.charAt(temp.length()-1)=='"'){
			return "\""+result[result.length - 1].replaceAll("//W", "").replaceAll(";", "");
		}
		return result[result.length - 1].replaceAll("//W", "").replaceAll(";", "").replaceAll("\\[", "");
	}

}
