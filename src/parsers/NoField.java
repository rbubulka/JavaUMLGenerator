package parsers;

import java.util.List;

public class NoField extends ClassParser {

	public NoField(ClassParser other) {
		super(other);
	}
	@Override
	public  String parse(List field){
		return "";
	}
}
