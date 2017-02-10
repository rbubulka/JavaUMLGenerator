To run our uml generator you should pass it a space separated series of arguemnts and class names. Anything that is not an exactly a listed argument case sensitive will be considered a class name. Please format your class names with their exact path to their location and separate each level by a .
Do not comma separate the list of class names. 
The default behavior of the generator will be to parse all of the given classes's  fields and methods specified as public, private and protected for all public classes. The default output file is a dot file for GraphViz called output.dot in the Documents file of the project. Each of the flags will act as a switch and so for a given flag it will either turn  off or on the intended functionality of that flag, if a flag is passed in twice the functionality of it will be changed twice result in an ultimately unchanged operation. 
The following argument flags are valid and will cause their listed behaviors
		-publicMethod			Will cause the generator to not parse the public methods
		-protectedMethod	 	will cause the generator to not parse the protected methods
		-privateMethod 			will cause the generator to not private the public methods. 
		-publicField			Will cause the generator to not parse the public Fields
		-protectedField	 		will cause the generator to not parse the protected Fields
		-privateField 			will cause the generator to not parse the private Fields. 		
		-publicClass			will cause the generator to not parse the public classes
		-protectedClasss		will cause the generator to parse protected classes
		-recursive				will cause the generator to not parse all of the ancestors of the files
		-lambda					will cause the gnerator to parse the lambda functions of the classes
		-FD						will cause the generator to parse relations which show the other classes the class has as fields
		-MRD					will cause the generator to parse relations which show the other classes the class's methods retrun
		-MID					will cause the generator to parse the relations which show the otherclasses the class utilizes in its methods
		-whitelist=				will cause the generator to parse the comma separated list of classes in addition to any specified classes
		-blacklist=				will cause the generator to never parse any class in the comma separated list of classes regardless of where it is in files being parsed. 
		-pattern=				will allow the generator to use custom parser files which are designed by external parties so long as they extend the projects parser class in some fashion. This should be a common separated fully qualified classname for the java class which is the parsers
		-o=						followed by a file path will specify where to print the output file. 
		