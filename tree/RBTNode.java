package tree;

public class RBTNode extends TreeNode{
	
	RBTNode left_node;
	RBTNode right_node;
	boolean color_node;
	
	public RBTNode(int node_value) {
		super(node_value);
		// true�����ɫ��false�����ɫ,����Ϊ�˷����жϽڵ��Ƿ��Ǻ�ɫ
		color_node = true;
	}
}
