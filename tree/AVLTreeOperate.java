package tree;

import java.util.LinkedList;
import java.util.Queue;

public class AVLTreeOperate {
	
	

	public static void main(String[] args) {
		// ����Ľ��
//		int[] data = {6,2,8,1,4,3};
		int[] data = {3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9};
        
		AVLTreeOperate avl_operate = new AVLTreeOperate();
		AVLTreeNode avl_tree = avl_operate.constructTree(data);
		System.out.println("��ʼ�������");
		System.out.println("�߶�:" + avl_tree.height);
		avl_operate.printTree(avl_tree);
		AVLTreeNode value_node = avl_operate.searchValue(avl_tree, 2);
		if(value_node == null) {
			System.out.println("���в������˽ڵ�");
		}else {
			System.out.println("���а�������ڵ�");
		}
		avl_operate.deleteNode(avl_tree, 12);
		value_node = avl_operate.searchValue(avl_tree, 2);
		if(value_node == null) {
			System.out.println("���в������˽ڵ�");
		}else {
			System.out.println("���а�������ڵ�");
		}
		avl_operate.printTree(avl_tree);
		avl_operate.deleteNode(avl_tree, 12);
	}
    
	// �����֮ǰ����һ���ˣ�ֱ��ʹ��search�����ң���Ϊ����Ҫʱ�̵���
	private AVLTreeNode deleteNode(AVLTreeNode avl_tree, int value) {
		if(avl_tree == null) {
			System.out.println("���в��������ֵ");
		}else {
			if(avl_tree.node_value > value) {
				avl_tree.left_node = deleteNode(avl_tree.left_node,value);
				// �����ƽ��Ļ�����Ҫ����
				if(getHeight(avl_tree.right_node) - getHeight(avl_tree.left_node) > 1) {
					if(getHeight(avl_tree.right_node.right_node) > getHeight(avl_tree.right_node.left_node)) {
						avl_tree = RRrotate(avl_tree);
					}else {
						avl_tree = RLrotate(avl_tree);
					}
				}
			}else if(avl_tree.node_value < value) {
				avl_tree.right_node = deleteNode(avl_tree.right_node,value);
				
				if(getHeight(avl_tree.left_node) - getHeight(avl_tree.right_node) > 1) {
					if(getHeight(avl_tree.left_node.left_node) > getHeight(avl_tree.left_node.right_node)) {
						avl_tree = LLrotate(avl_tree);
					}else {
						avl_tree = LRrotate(avl_tree);
					}
				}
			}else {
				// �������ֵ,�����BST���Ĳ�����һ���ģ����Ƿ������������û������������
				if(avl_tree.left_node != null && avl_tree.right_node != null) {
					int min_value = findMin(avl_tree.right_node);
					avl_tree.node_value = min_value;
					avl_tree.right_node = deleteNode(avl_tree.right_node, min_value);
				}else {
					// ֻҪ��ôһ�仰����
					avl_tree = (avl_tree.left_node != null) ? avl_tree.left_node:avl_tree.right_node; 
				}
			}
		}
		return avl_tree;
	}

	private int findMin(AVLTreeNode node) {
		int min_value = node.node_value;
		while(node.left_node != null) {
			node = node.left_node;
			min_value = node.node_value;
		}
		return min_value;
	}

	private AVLTreeNode searchValue(AVLTreeNode avl_tree, int value) {
		
		while(avl_tree != null) {
			if(avl_tree.node_value == value) return avl_tree;
			if(avl_tree.node_value > value) {
				avl_tree = avl_tree.left_node;
			}else {
				avl_tree = avl_tree.right_node;
			}
		}
		
		return null;
	}

	// ��Ȳ�α�����ʹ�õ��Ƕ���
	private void printTree(AVLTreeNode avl_tree) {
		// ʹ�õĶ���
		Queue<AVLTreeNode> print_queue = new LinkedList<AVLTreeNode>();
		print_queue.offer(avl_tree);
		
		while(!print_queue.isEmpty()) {
			AVLTreeNode temp_node = print_queue.poll();
			System.out.print(temp_node.node_value + ", ");
			if(temp_node.left_node != null) print_queue.offer(temp_node.left_node);
			if(temp_node.right_node != null) print_queue.offer(temp_node.right_node);
		}
		System.out.println();
	}

	// ��ʼ��AVL��
	private AVLTreeNode constructTree(int[] data) {
		AVLTreeNode rootNode = new AVLTreeNode(data[0]);
	    int start_index = 1;
	    while(start_index < data.length) {
	    	System.out.println(rootNode.height);
	    	rootNode = insertNode(rootNode, data[start_index]);
//	    	System.out.println(rootNode.height);
	        start_index++;
	    }
		return rootNode;
	}

	private AVLTreeNode insertNode(AVLTreeNode rootNode, int value) {
		
		if(rootNode != null) {
			if(rootNode.node_value > value) {
				rootNode.left_node = insertNode(rootNode.left_node, value);
				// �����������������ô����������ͺ�,�����ƽ�⣬��Ҫ����
				if(getHeight(rootNode.left_node) - getHeight(rootNode.right_node) > 1) {
					// �����λ��������������ڵ㣬ʹ��LL��ת
					if(value < rootNode.left_node.node_value) {
						rootNode = LLrotate(rootNode);
					}else {
						// ��������λ�������������ҽڵ㣬��ôLR��ת
						rootNode = LRrotate(rootNode);
					}
				}
			}else if(rootNode.node_value < value) {
				rootNode.right_node = insertNode(rootNode.right_node, value);
				// ��ƽ��Ļ�����Ҫ���е���
				if(getHeight(rootNode.right_node) - getHeight(rootNode.left_node) > 1) {
					// �����λ�������������ҽڵ�Ļ�������Ҫ����RR
					if(value > rootNode.right_node.node_value) {
						rootNode = RRrotate(rootNode);
					}else {
						// ���������������ڵ㣬����Ҫ����RL
						rootNode = RLrotate(rootNode);
					}
				}
			}else {
				System.out.println("���ܲ��룬�Ѿ�������ͬ�ڵ�");
			}
		}else {
			rootNode = new AVLTreeNode(value);			
		}
		
		// ���½ڵ�߶�
		rootNode.height = Math.max(getHeight(rootNode.left_node), getHeight(rootNode.right_node)) + 1;
		
		return rootNode;
	}

	private int getHeight(AVLTreeNode node) {
		if(node != null) {
			return node.height;
		}
		return 0;
	}

	// RL��ת
	private AVLTreeNode RLrotate(AVLTreeNode rootNode) {
		rootNode.right_node = LLrotate(rootNode.right_node);
		return RRrotate(rootNode);
	}

	// LR��ת
	private AVLTreeNode LRrotate(AVLTreeNode rootNode) {
		// ����ȷʵ�����Ƚ�����������RR��֮����LL����֪����ô������
		rootNode.left_node = RRrotate(rootNode.left_node);
		return LLrotate(rootNode);
	}

	// RR��ת 
	private AVLTreeNode RRrotate(AVLTreeNode rootNode) {
		AVLTreeNode rightNode = rootNode.right_node;
		rootNode.right_node = rightNode.left_node;
		rightNode.left_node = rootNode;
		
		// ���½ڵ�ĸ߶�
		rootNode.height = Math.max(getHeight(rootNode.left_node), getHeight(rootNode.right_node)) + 1;
		rightNode.height = Math.max(getHeight(rightNode.left_node), getHeight(rightNode.right_node)) + 1;
		
		return rightNode;
	}
	
    // LL��ת
	private AVLTreeNode LLrotate(AVLTreeNode rootNode) {
		AVLTreeNode leftNode = rootNode.left_node;
		rootNode.left_node = leftNode.right_node;
		leftNode.right_node = rootNode;
		
		// ���½ڵ�ĸ߶�
		rootNode.height = Math.max(getHeight(rootNode.left_node), getHeight(rootNode.right_node)) + 1;
		leftNode.height = Math.max(getHeight(leftNode.left_node), getHeight(leftNode.right_node)) + 1;
		
		return leftNode;
	}

	
	
	
}
