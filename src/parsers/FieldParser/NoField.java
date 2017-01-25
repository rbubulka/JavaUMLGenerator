package parsers.FieldParser;

import java.util.List;
import java.util.Set;

public class NoField extends FieldsParser {

	public NoField(FieldsParser other) {
		super(other);
	}
	@Override
	public String parse(List fields, Set<String> relations){
		return "";
	}
}
