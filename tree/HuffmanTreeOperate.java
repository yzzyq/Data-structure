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
    
	// �õ�WPL
	private int getWPL(HuffmanTreeNode rootNode) {
		// ֻҪҶ�ӽڵ��WPL����
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

	// ʹ�ö�����������
	private HuffmanTreeNode createTree(int[] data) {
		List<HuffmanTreeNode> huffman_queue = new ArrayList<HuffmanTreeNode>();
		HuffmanTreeNode rootNode = null;
		// �Ƚ�ȫ��ֵ����ɽڵ�
		for(int one_data:data) {
			HuffmanTreeNode node = new HuffmanTreeNode(one_data);
			huffman_queue.add(node);
		}
		// ��������
		huffman_queue.sort(Comparator.naturalOrder());
		
		while(!huffman_queue.isEmpty()) {
			// ѡ����СȨ�ص������㣬���һ���µĽڵ�
			HuffmanTreeNode leftNode = huffman_queue.remove(0);
			HuffmanTreeNode rightNode = huffman_queue.remove(0);
			HuffmanTreeNode parentNode = new HuffmanTreeNode(leftNode.weight + rightNode.weight,
					                                         leftNode,
					                                         rightNode);
			// ֻҪ�����л���ֵ���ͼ���
			if(!huffman_queue.isEmpty()) {
				huffman_queue.add(parentNode);
				huffman_queue.sort(Comparator.naturalOrder());
			}else {
				// ������û��ֵ�ˣ���ô�͸���·�����ȼ���
				rootNode = updateLength(parentNode);
			}
		}
		return rootNode;
	}
	
	private HuffmanTreeNode updateLength(HuffmanTreeNode node) {
		// ����������
		if(node.left_node != null) {
			node.left_node.length_node = node.length_node + 1;
			node.left_node = updateLength(node.left_node);
		}
		// ����������
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
			System.out.print(queue_element.weight + "��" + queue_element.length_node + ", ");
		}
		System.out.println();
	}

}
