package utilities;

import java.util.Set;

import org.objectweb.asm.tree.ClassNode;

public class NodeRelation {
	Set<ClassNode> nodes;
	Set<String> relations;

	public NodeRelation(Set<ClassNode> nodes, Set<String> relations) {
		this.relations = relations;
		this.nodes = nodes;
	}

	public Set<ClassNode> getNodes() {
		return this.nodes;
	}

	public Set<String> getRelations() {
		return this.relations;
	}

}