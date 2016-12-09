package parsers;

import java.util.HashMap;
import java.util.List;

public interface Parser {
	public String parse(List nodes);
	public String parseinfo(int Opcode, String text,List node);
}
