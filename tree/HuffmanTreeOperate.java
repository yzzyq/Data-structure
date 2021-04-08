package tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class HuffmanTreeOperate {

	public static void main(String[] args) {
		int[] data = { 6, 2, 8, 1, 4};
		HuffmanTreeOperate hfto = new HuffmanTreeOperate();
		HuffmanTreeNode rootNode = hfto.createTree(data);
        int wpl = hfto.getWPL(rootNode);
        System.out.println("WPL:" + wpl);
        hfto.printTree(rootNode);
	}
    
	// 得到WPL
	private int getWPL(HuffmanTreeNode rootNode) {
		// 只要叶子节点的WPL即可
		if(rootNode.left_node == null && rootNode.right_node == null) {
			return rootNode.length_node*rootNode.weight;
		}else {
			int left_wpl = 0;
			int right_wpl = 0;
			if(rootNode.left_node != null) {
				left_wpl = getWPL(rootNode.left_node);
			}
			
			if(rootNode.right_node != null) {
				right_wpl = getWPL(rootNode.right_node);
			}
			return left_wpl + right_wpl;
		}
		
	}

	// 使用队列来构造树
	private HuffmanTreeNode createTree(int[] data) {
		List<HuffmanTreeNode> huffman_queue = new ArrayList<HuffmanTreeNode>();
		HuffmanTreeNode rootNode = null;
		// 先将全部值都变成节点
		for(int one_data:data) {
			HuffmanTreeNode node = new HuffmanTreeNode(one_data);
			huffman_queue.add(node);
		}
		// 对象排序
		huffman_queue.sort(Comparator.naturalOrder());
		
		while(!huffman_queue.isEmpty()) {
			// 选出最小权重的俩个点，组成一个新的节点
			HuffmanTreeNode leftNode = huffman_queue.remove(0);
			HuffmanTreeNode rightNode = huffman_queue.remove(0);
			HuffmanTreeNode parentNode = new HuffmanTreeNode(leftNode.weight + rightNode.weight,
					                                         leftNode,
					                                         rightNode);
			// 只要队列中还有值，就继续
			if(!huffman_queue.isEmpty()) {
				huffman_queue.add(parentNode);
				huffman_queue.sort(Comparator.naturalOrder());
			}else {
				// 队列中没有值了，那么就更新路径长度即可
				rootNode = updateLength(parentNode);
			}
		}
		return rootNode;
	}
	
	private HuffmanTreeNode updateLength(HuffmanTreeNode node) {
		// 更新左子树
		if(node.left_node != null) {
			node.left_node.length_node = node.length_node + 1;
			node.left_node = updateLength(node.left_node);
		}
		// 更新右子树
		if(node.right_node != null) {
			node.right_node.length_node = node.length_node + 1;
			node.right_node = updateLength(node.right_node);
		}
		
		return node;
	}

	private void printTree(HuffmanTreeNode node) {
    	Queue<HuffmanTreeNode> tree_queue = new LinkedList<HuffmanTreeNode>(); 
		tree_queue.offer(node);
		
		while(!tree_queue.isEmpty()) {
			HuffmanTreeNode queue_element = tree_queue.poll();
			if(queue_element.left_node != null) tree_queue.offer(queue_element.left_node);
			if(queue_element.right_node != null) tree_queue.offer(queue_element.right_node);
			System.out.print(queue_element.weight + "、" + queue_element.length_node + ", ");
		}
		System.out.println();
	}

}
