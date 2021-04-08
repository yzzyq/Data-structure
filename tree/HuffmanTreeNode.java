package tree;

public class HuffmanTreeNode implements Comparable<HuffmanTreeNode>{
	int weight;
	HuffmanTreeNode left_node;
	HuffmanTreeNode right_node;
	int length_node;

	public HuffmanTreeNode(int weight) {
		this.weight = weight;
		length_node = 0;
	}
	
	public HuffmanTreeNode(int weight,HuffmanTreeNode leftNode,HuffmanTreeNode rightNode) {
		this.weight = weight;
		length_node = 0;
		left_node = leftNode;
		right_node = rightNode;
	}

	@Override
	public int compareTo(HuffmanTreeNode node) {
		if(this.weight >= node.weight) {
			return 1;
		}
		
		return -1;
	}
	
}
