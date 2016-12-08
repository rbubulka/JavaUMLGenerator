package NodeGetter;

import java.io.IOException;
import java.util.ArrayList;

import org.objectweb.asm.tree.ClassNode;

public interface FileGetter {




	public void addClasses(ArrayList<String> classnames,ArrayList<ClassNode> nodes) throws IOException;
	
}
