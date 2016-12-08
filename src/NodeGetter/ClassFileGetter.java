package NodeGetter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
public class ClassFileGetter implements FileGetter {


	@Override
	public void addClasses(ArrayList<String> classnames,ArrayList<ClassNode> nodes) throws IOException {
		for(String cname:classnames){
			ClassReader reader = new ClassReader(cname);
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.EXPAND_FRAMES);
			nodes.add(classNode);
		}
		
		
	}

}
