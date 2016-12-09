package parsers;

import java.util.List;

public abstract class FieldsPpp implements Parser {
	
	protected FieldsPpp otherPpp;
	public FieldsPpp(FieldsPpp other){
		this.otherPpp = other;
	}
	
	
	public String parse(List fields) {
		// TODO Auto-generated method stub
		return null;
	}
}
