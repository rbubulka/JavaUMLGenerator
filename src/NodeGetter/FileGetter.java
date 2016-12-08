package NodeGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;

public interface FileGetter {

	void addClasses(ArrayList<String> classnames, Set<ClassNode> nodes, List<String> relations) throws IOException;
	
}
