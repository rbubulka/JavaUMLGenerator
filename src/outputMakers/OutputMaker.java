package outputMakers;

import java.util.HashMap;
import java.util.List;

public interface OutputMaker {
	public void fileWrite(String fileName, List<HashMap<String, String>> parsedstring, List<String> relations);
}
