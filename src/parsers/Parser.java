package parsers;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface Parser {
	public String parse(List nodes, Set<String> relations, List<HashMap<String, String>> classinfo);
	public void setParser(Parser other);
}
