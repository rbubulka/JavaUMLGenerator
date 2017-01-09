package parserMakers;

import parsers.MethodParser.*;

public class MethodParserMaker {
	private boolean privateMethods = false;
	private boolean protectedMethods = false;
	private boolean publicMethods = false;
	private boolean dependecies = false;
	private boolean instructions = false;
	
	private static volatile MethodParserMaker config;

	public static MethodParserMaker getInstance() {
		if (config == null) {
			synchronized (MethodParserMaker.class) {
				if (config == null) {
					config = new MethodParserMaker();
				}
			}
		}
		return config;
	}

	public void setPrivateFields(boolean privateFields) {
		this.privateMethods = privateFields;
	}

	public void setProtectedFields(boolean protectedFields) {
		this.protectedMethods = protectedFields;
	}

	public void setPublicFields(boolean publicFields) {
		this.publicMethods = publicFields;
	}

	public void setDependecies(boolean dependecies) {
		this.dependecies = dependecies;
	}
	public void setInstructions(boolean instruction) {
		this.instructions = instruction;
	}
	public MethodsParser makeParser(){
		MethodsParser parser = null;
		if(instructions){
			if(this.privateMethods){
				parser = new PublicMIDParser(parser);
			}
			if(this.protectedMethods){
				parser = new ProtectedMIDParser(parser);
			}
			if(this.privateMethods){
				parser = new PrivateMIDParser(parser);
			}
		}
		if(dependecies){
			if(this.privateMethods){
				parser = new PublicMRDParser(parser);
			}
			if(this.protectedMethods){
				parser = new ProtectedMRDParser(parser);
			}
			if(this.privateMethods){
				parser = new PrivateMRDParser(parser);
			}
		}
		
		if(this.privateMethods){
			parser = new PublicMethodsParser(parser);
		}
		if(this.protectedMethods){
			parser = new ProtectedMethodsParser(parser);
		}
		if(this.privateMethods){
			parser = new PrivateMethodsParser(parser);
		}
		return parser;
		
	}
	
	

}
