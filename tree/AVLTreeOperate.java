package tree;

import java.util.LinkedList;
import java.util.Queue;

public class AVLTreeOperate {
	
	

	public static void main(String[] args) {
		// 插入的结点
//		int[] data = {6,2,8,1,4,3};
		int[] data = {3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9};
        
		AVLTreeOperate avl_operate = new AVLTreeOperate();
		AVLTreeNode avl_tree = avl_operate.constructTree(data);
		System.out.println("初始化树完成");
		System.out.println("高度:" + avl_tree.height);
		avl_operate.printTree(avl_tree);
		AVLTreeNode value_node = avl_operate.searchValue(avl_tree, 2);
		if(value_node == null) {
			System.out.println("树中不包含此节点");
		}else {
			System.out.println("树中包含这个节点");
		}
		avl_operate.deleteNode(avl_tree, 12);
		value_node = avl_operate.searchValue(avl_tree, 2);
		if(value_node == null) {
			System.out.println("树中不包含此节点");
		}else {
			System.out.println("树中包含这个节点");
		}
		avl_operate.printTree(avl_tree);
		avl_operate.deleteNode(avl_tree, 12);
	}
    
	// 这个和之前不能一样了，直接使用search方法找，因为它需要时刻调整
	private AVLTreeNode deleteNode(AVLTreeNode avl_tree, int value) {
		if(avl_tree == null) {
			System.out.println("树中不包含这个值");
		}else {
			if(avl_tree.node_value > value) {
				avl_tree.left_node = deleteNode(avl_tree.left_node,value);
				// 如果不平衡的话，需要调整
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
				// 等于这个值,这个和BST树的操作是一样的，就是分三种情况，有没有左右子树的
				if(avl_tree.left_node != null && avl_tree.right_node != null) {
					int min_value = findMin(avl_tree.right_node);
					avl_tree.node_value = min_value;
					avl_tree.right_node = deleteNode(avl_tree.right_node, min_value);
				}else {
					// 只要这么一句话就行
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

	// 广度层次遍历，使用的是队列
	private void printTree(AVLTreeNode avl_tree) {
		// 使用的队列
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

	// 初始化AVL树
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
				// 插入的是左子树，那么检查左子树就好,如果不平衡，需要调整
				if(getHeight(rootNode.left_node) - getHeight(rootNode.right_node) > 1) {
					// 插入的位置是左子树的左节点，使用LL旋转
					if(value < rootNode.left_node.node_value) {
						rootNode = LLrotate(rootNode);
					}else {
						// 如果插入的位置是左子树的右节点，那么LR旋转
						rootNode = LRrotate(rootNode);
					}
				}
			}else if(rootNode.node_value < value) {
				rootNode.right_node = insertNode(rootNode.right_node, value);
				// 不平衡的话，需要进行调整
				if(getHeight(rootNode.right_node) - getHeight(rootNode.left_node) > 1) {
					// 插入的位置是右子树的右节点的话，就需要进行RR
					if(value > rootNode.right_node.node_value) {
						rootNode = RRrotate(rootNode);
					}else {
						// 如果是右子树的左节点，就需要进行RL
						rootNode = RLrotate(rootNode);
					}
				}
			}else {
				System.out.println("不能插入，已经有了相同节点");
			}
		}else {
			rootNode = new AVLTreeNode(value);			
		}
		
		// 更新节点高度
		rootNode.height = Math.max(getHeight(rootNode.left_node), getHeight(rootNode.right_node)) + 1;
		
		return rootNode;
	}

	private int getHeight(AVLTreeNode node) {
		if(node != null) {
			return node.height;
		}
		return 0;
	}

	// RL旋转
	private AVLTreeNode RLrotate(AVLTreeNode rootNode) {
		rootNode.right_node = LLrotate(rootNode.right_node);
		return RRrotate(rootNode);
	}

	// LR旋转
	private AVLTreeNode LRrotate(AVLTreeNode rootNode) {
		// 这里确实就是先将左子树进行RR，之后再LL，不知道怎么起名的
		rootNode.left_node = RRrotate(rootNode.left_node);
		return LLrotate(rootNode);
	}

	// RR旋转 
	private AVLTreeNode RRrotate(AVLTreeNode rootNode) {
		AVLTreeNode rightNode = rootNode.right_node;
		rootNode.right_node = rightNode.left_node;
		rightNode.left_node = rootNode;
		
		// 更新节点的高度
		rootNode.height = Math.max(getHeight(rootNode.left_node), getHeight(rootNode.right_node)) + 1;
		rightNode.height = Math.max(getHeight(rightNode.left_node), getHeight(rightNode.right_node)) + 1;
		
		return rightNode;
	}
	
    // LL旋转
	private AVLTreeNode LLrotate(AVLTreeNode rootNode) {
		AVLTreeNode leftNode = rootNode.left_node;
		rootNode.left_node = leftNode.right_node;
		leftNode.right_node = rootNode;
		
		// 更新节点的高度
		rootNode.height = Math.max(getHeight(rootNode.left_node), getHeight(rootNode.right_node)) + 1;
		leftNode.height = Math.max(getHeight(leftNode.left_node), getHeight(leftNode.right_node)) + 1;
		
		return leftNode;
	}

	
	
	
}
