package tree;

public class RBTNode extends TreeNode{
	
	RBTNode left_node;
	RBTNode right_node;
	boolean color_node;
	
	public RBTNode(int node_value) {
		super(node_value);
		// true代表红色，false代表黑色,这是为了方便判断节点是否是红色
		color_node = true;
	}
}
