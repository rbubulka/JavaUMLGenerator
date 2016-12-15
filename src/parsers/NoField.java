package parsers;

import java.util.List;

public class NoField extends FieldsParser {

	public NoField(FieldsParser other) {
		super(other);
	}
	@Override
	public String parse(List fields){
		return "";
	}
}
