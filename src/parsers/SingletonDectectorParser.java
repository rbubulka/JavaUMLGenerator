package parsers;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.sun.org.apache.xpath.internal.compiler.OpCodes;
import com.sun.xml.internal.ws.org.objectweb.asm.Type;

import parsers.ClassParser.ClassParser;

public class SingletonDectectorParser extends ClassParser {

	public SingletonDectectorParser(ClassParser other) {
		super(other);
	}

	@Override
	public String parse(List nodes, Set<String> relations) {
		StringBuilder result = new StringBuilder();
		boolean hasSelf = false;
		boolean returnsSelf = false;
		for (ClassNode cn : (List<ClassNode>) nodes) {
			for (FieldNode fn : (List<FieldNode>) cn.fields) {
				if ((fn.access & Opcodes.ACC_STATIC) > 0 && fn.desc.contains(cn.name)) {
					hasSelf = true;
					break;
				}
			}
			for (MethodNode mn : (List<MethodNode>) cn.methods) {
				if (Type.getReturnType(mn.desc).getClassName().replaceAll("\\.", "/").contains(cn.name)) {
					returnsSelf = true;
					break;
				}
			}
		}
		if (hasSelf && returnsSelf) {
			StringBuilder other;
			if (otherparser != null) {
				other = new StringBuilder(this.otherparser.parse(nodes, relations));
				if (other.toString().contains("color=")) {
					int index = other.toString().indexOf("color=");
					other.insert(index + 6, "blue:");
					result = other;
				} else {
					result.append(",color=blue");
				}
			}
			else{
				result.append(",color=blue");
			}
			result.append(",write=singleton");
		}
		else{
			if(otherparser !=  null)result.append(this.otherparser.parse(nodes, relations));
		}
		return result.toString();
	}

}
