package parserMakers;

import parsers.ClassParser.*;

public class ClassParserMaker {
	private boolean protectedClass = false;
	private boolean publicClass = false;
	
	private static volatile ClassParserMaker config;

	public static ClassParserMaker getInstance() {
		if (config == null) {
			synchronized (ClassParserMaker.class) {
				if (config == null) {
					config = new ClassParserMaker();
				}
			}
		}
		return config;
	}

	public void setProtectedFields(boolean protectedFields) {
		this.protectedClass = protectedFields;
	}

	public void setPublicFields(boolean publicFields) {
		this.publicClass = publicFields;
	}

	public ClassParser makeParser(){
		ClassParser parser = null;
		if(this.protectedClass){
			parser = new ProtectedClassParser(parser);
		}
		if(this.publicClass){
			parser = new PublicClassParser(parser);
		}
		return parser;
		
	}
	
	

}
