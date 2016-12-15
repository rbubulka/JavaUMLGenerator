To run our uml generator you should pass it a space separated series of arguemnts and class names. Anything that is not an exactly a listed argument case sensitive will be considered a class name. 
The default behavior of the generator will be to parse only the given classes's public fields and methods if the class is public. The default output file is a dot file for GraphViz called output.dot in the Documents file of the project. 
The following argument flags are valid and will cause their listed behaviors
		-publicMethod			Will cause the generator to get only the public methods
		-protectedMethod	 	will cause the generator to get the protected and public methods
		-privateMethod 			will cause the generator to get the private, protected, and public methods. 
		-noMethod				will cause the generator to get no methods
		-publicField			Will cause the generator to get only the public Fields
		-protectedField	 		will cause the generator to get the protected and public Fields
		-privateField 			will cause the generator to get the private, protected, and public Fields. 
		-noField				will cause the generator to get no Fields		
		-publicClass			will cause the generator to get only the public classes
		-protectedClasss		will cause the generator to only get the protected classes
		-GVMaker				will cause the generator to produce a GraphViz file
		-recursive				will cause the generator to parse all of the ancestors of the files
		-o=						followed by a file path will specify where to print the output file. 
		
		
		
Our Contributions:
	We pair programmed everything. Songyu was the primary driver when working on getting all the information from the nodes, Remy was the primary driver when working on parsing node information into a dot format. 
	