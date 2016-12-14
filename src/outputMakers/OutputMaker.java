package outputMakers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface OutputMaker {
	public void fileWrite(String fileName, List<HashMap<String, String>> parsedstring, List<String> relations) throws FileNotFoundException, IOException;
}
