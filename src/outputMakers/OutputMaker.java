package outputMakers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface OutputMaker {
	public void fileWrite(String fileName, List<HashMap<String, String>> parsedstring, Set<String> relations) throws FileNotFoundException, IOException;
}
