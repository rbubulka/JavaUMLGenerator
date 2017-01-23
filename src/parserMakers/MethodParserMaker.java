package parserMakers;

import parsers.MethodParser.*;

public class MethodParserMaker {
	private boolean privateMethods = false;
	private boolean protectedMethods = false;
	private boolean publicMethods = false;
	private boolean dependecies = false;
	private boolean instructions = false;
	private boolean lambda=false;
	
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

	public void setPrivateMethods() {
		this.privateMethods = !this.privateMethods;
	}
	public void setLambda(boolean lambda) {
		this.lambda = !this.lambda;
	}

	public void setProtectedMethods() {
		this.protectedMethods = !this.protectedMethods;
	}

	public void setPublicMethods() {
		this.publicMethods = !this.publicMethods;
	}

	public void setDependecies() {
		this.dependecies = !this.dependecies;
	}
	public void setInstructions() {
		this.instructions = !this.instructions;
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
		if(!lambda){
			parser=new LambdaParser(parser);
		}
		return parser;
		
	}
	
	

}
