package tree;

public class AVLTreeNode extends TreeNode{

	int height;
	AVLTreeNode left_node;
	AVLTreeNode right_node;
	
	public AVLTreeNode(int node_value) {
		super(node_value);
		this.height = 0;
	}
	

}
