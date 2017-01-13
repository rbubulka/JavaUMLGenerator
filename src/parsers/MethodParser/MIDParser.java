package parsers.MethodParser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public abstract class MIDParser extends MethodsParser {

	public MIDParser(MethodsParser other) {
		super(other);
	}

	@Override
	public String parse(List methods, Set<String> relations, String classname) {
		StringBuilder result = new StringBuilder();
		
		for(MethodNode md:(List<MethodNode>)methods){
			InsnList insnls=md.instructions;
		
			for(int i = 0; i < insnls.size(); i++){
				AbstractInsnNode insn = insnls.get(i);
				if (insn instanceof MethodInsnNode){
					
					MethodInsnNode mc = (MethodInsnNode) insn;
					String[] desc=mc.desc.split("\\)");
					String rt=desc[desc.length-1];
				
					if (rt.contains("<")) {
						addCollectionDependency(relations, classname, rt);
					} else {
						addDependency(relations, classname, rt, false);
					}
				}
				else if (insn instanceof VarInsnNode){
					
					VarInsnNode varInsn = (VarInsnNode) insn;
					if(varInsn.getOpcode()==Opcodes.ALOAD){
						try{
						LocalVariableNode lvn=getLocalVariableNode(varInsn, md);
						String lvnsign=lvn.signature;
						String lvndesc=lvn.desc;
						if (lvnsign.contains("<")) {
							addCollectionDependency(relations, classname, lvnsign);
						} else {
							if(lvndesc.contains("[")){
								
								addDependency(relations, classname, lvndesc.replace(";", "").substring(1), true);
							}else{
								
							addDependency(relations, classname, lvndesc.replace(";", ""), false);}
						}
						
						}
						catch(Exception e){
							
						}
						
					}
				}
				
				
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		for (MethodNode mn : (List<MethodNode>) methods) {
//			List<LocalVariableNode> variables = (List<LocalVariableNode>)mn.localVariables;
////			int lastIndex = 0;
////			List<LocalVariableNode> toRemove = new ArrayList<LocalVariableNode>();
////			for(int i = 0; i < variables.size(); i++){
////				if(variables.get(i).index < lastIndex){
////					int diff =  lastIndex - variables.get(i).index;
////					for(int j = 0; j < i; j++) toRemove.add(variables.get(i-(j+1));
////				}
////				lastIndex = variables.get(i).index;
////			}			
//			
//			if (mn.localVariables != null) {
//				for (LocalVariableNode local : variables)
//					if (local != null && local.signature != null) {
//						if (local.signature.contains("<")) {
//							addCollectionDependency(relations, classname, local.signature);
//						} else {
//							addDependency(relations, classname, local.signature, false);
//						}
//					}
//			}
//		}
		
		
		
		
		
		
		if (otherparser != null)
			result.append(this.otherparser.parse(methods, relations, classname));
		return result.toString();
	}

	private void addCollectionDependency(Set<String> relations, String classname, String local) {
		if (!local.contains("<")) {
			addDependency(relations, classname, local, true);
		} else {
			//System.out.println(local);
			String collectionType = "";
			if(local.contains(">")){ collectionType = local.substring(local.indexOf("<")+1, local.lastIndexOf(">"));}
			else{
				collectionType = local.substring(local.indexOf("<")+1);
			}
			
			String[] types = collectionType.split(";");
			for (String type : types) {
				if (type.contains("<")) {
					addCollectionDependency(relations, classname, type);
				} else {
					addDependency(relations, classname, type, true);
				}
			}
		}
	}

	private void addDependency(Set<String> relations, String classname, String type, boolean isCollection) {
		String num = "1 ";
		if (isCollection) num = "* ";
		relations.add(classname + " 1 usea " + num +type.trim().replaceAll("\\[", ""));
	}
	
	private LocalVariableNode getLocalVariableNode(VarInsnNode varInsnNode, MethodNode methodNode) {
       //from http://stackoverflow.com/questions/4324321/java-local-variables-how-do-i-get-a-variable-name-or-type-using-its-index
		int varIdx = varInsnNode.var;
        int instrIdx = getInstrIndex(varInsnNode);
        List<?> localVariables = methodNode.localVariables;
        for (int idx = 0; idx < localVariables.size(); idx++) {
            LocalVariableNode localVariableNode = (LocalVariableNode) localVariables.get(idx);
            if (localVariableNode.index == varIdx) {
                int scopeEndInstrIndex = getInstrIndex(localVariableNode.end);
                if (scopeEndInstrIndex >= instrIdx) {
                    // still valid for current line
                    return localVariableNode;
                }
            }
        }
        throw new RuntimeException("Variable with index " + varIdx + " and scope end >= " + instrIdx
                + " not found for method " + methodNode.name + "!");
    }
	 private int getInstrIndex(AbstractInsnNode insnNode) {
		 //from http://stackoverflow.com/questions/4324321/java-local-variables-how-do-i-get-a-variable-name-or-type-using-its-index
			  
		 try {
	            Field indexField = AbstractInsnNode.class.getDeclaredField("index");
	            indexField.setAccessible(true);
	            Object indexValue = indexField.get(insnNode);
	            return ((Integer) indexValue).intValue();
	        } catch (Exception exc) {
	            throw new RuntimeException(exc);
	        }
	    }

}
