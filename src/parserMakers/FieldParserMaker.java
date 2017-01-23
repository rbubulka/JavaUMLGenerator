package parserMakers;

import parsers.FieldParser.*;

public class FieldParserMaker {
	private boolean privateFields = false;
	private boolean protectedFields = false;
	private boolean publicFields = false;
	private boolean dependecies = false;
	
	private static volatile FieldParserMaker config;

	public static FieldParserMaker getInstance() {
		if (config == null) {
			synchronized (FieldParserMaker.class) {
				if (config == null) {
					config = new FieldParserMaker();
				}
			}
		}
		return config;
	}

	public void setPrivateFields() {
		this.privateFields = !this.privateFields;
	}

	public void setProtectedFields() {
		this.protectedFields = !this.protectedFields;
	}

	public void setPublicFields() {
		this.publicFields = !this.publicFields;
	}

	public void setDependecies() {
		this.dependecies = !this.dependecies;
	}
	
	public FieldsParser makeParser(){
		FieldsParser parser = null;
		if(dependecies){
			if(this.privateFields){
				parser = new PublicFieldDependencyParser(parser);
			}
			if(this.protectedFields){
				parser = new ProtectedFieldDependencyParser(parser);
			}
			if(this.privateFields){
				parser = new PrivateFieldDependencyParser(parser);
			}
		}
		
		if(this.privateFields){
			parser = new PublicFieldsParser(parser);
		}
		if(this.protectedFields){
			parser = new ProtectedFieldsParser(parser);
		}
		if(this.privateFields){
			parser = new PrivateFieldsParser(parser);
		}
		return parser;
		
	}
	
	

}
