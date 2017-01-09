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

	public void setPrivateFields(boolean privateFields) {
		this.privateFields = privateFields;
	}

	public void setProtectedFields(boolean protectedFields) {
		this.protectedFields = protectedFields;
	}

	public void setPublicFields(boolean publicFields) {
		this.publicFields = publicFields;
	}

	public void setDependecies(boolean dependecies) {
		this.dependecies = dependecies;
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
