package tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BSTreeOperate {
	
	public static void main(String[] args) {
		// 插入的结点
		int[] data = {6,2,8,1,4,3};
		BSTreeOperate bsto = new BSTreeOperate();
		TreeNode rootNode = bsto.constructTree(data);
		bsto.printTree(rootNode);
//		TreeNode value_node = bsto.searchValue(rootNode, 2);
//		if(value_node == null) {
//			System.out.println("树中不包含此节点");
//		}else {
//			System.out.println("树中包含这个节点");
//		}
//		bsto.deleteNode(rootNode, 2);
//		value_node = bsto.searchValue(rootNode, 2);
//		if(value_node == null) {
//			System.out.println("树中不包含此节点");
//		}else {
//			System.out.println("树中包含这个节点");
//		}
//		bsto.deleteNode(rootNode, 2);
	}

	// 构造BST树 
	private TreeNode constructTree(int[] data) {
		TreeNode rootNode = new TreeNode(data[0]);
		int start_index = 1;
		
		while(start_index < data.length) {
			System.out.println(start_index);
		    insertNode(rootNode,data[start_index]);
		    start_index++;
		}	
		return rootNode;
	}

	// 往BST树中插入节点 
	private TreeNode insertNode(TreeNode node, int data) {
		if(node == null) {
			// 这边不是直接赋值的，而是初始化
			node = new TreeNode(data);
		}else if(node.node_value == data){
			System.out.println("已经有相同的节点存在");
		}else if(node.node_value > data) {
			node.left_node = insertNode(node.left_node,data);
		}else if(node.node_value < data) {
			node.right_node = insertNode(node.right_node,data);
		}
		return node;
	}
	
//	// 查找BST树中的结点，递归写法
//	private TreeNode searchValue(TreeNode node, int value) {
//		
//		if(node == null || node.node_value == value) {
//			return node;
//		}
//		
//		if(node.node_value < value) {
//			return searchValue(node.right_node,value);
//		}else{
//			return searchValue(node.left_node,value);
//		}
//	}
	
	// 查找BST树中的结点，非递归写法
    private TreeNode searchValue(TreeNode node, int value) {
		while(node != null) {
			if(node.node_value == value) {
				return node;
			}
			
			if(node.node_value < value) {
				node = node.right_node;
			}else if(node.node_value > value) {
				node = node.left_node;
			}
		}
		return node;
	}
    
    // 删除BST树中的节点
    // 1. 如果是叶节点，那么直接变成null
    // 2. 如果只有左子树或者右子树，那么直接用左子树或者右子树代替
    // 3. 如果左右都有，那么先从右子树中找出直接后继，也就是右子树当中最小的值，
    //              或者先从右子树中找出直接前驱，也就是左子树中最大的值。
    private void deleteNode(TreeNode rootNode, int delete_value) {
		TreeNode deleteNode = searchValue(rootNode,delete_value);
		if(deleteNode == null) {
			System.out.println("树中没有该节点的存在");
		}else {
			if (deleteNode.left_node == null && deleteNode.right_node == null) {
				deleteNode = null;
			} else if (deleteNode.left_node != null && deleteNode.right_node != null) {
				int min_value = findMin(deleteNode.right_node);
				deleteNode.node_value = min_value;
				deleteNode(deleteNode.right_node, min_value);
			} else {
				deleteNode = deleteNode.left_node == null ? deleteNode.right_node : deleteNode.left_node;
			}
		}
	}
    
    private int findMin(TreeNode node) {
    	int min_value = node.node_value;
		while(node.left_node != null) {
			node = node.left_node;
			min_value = node.node_value;
		}
		return min_value;
	}

	// 遍历树,分别采用Morris遍历，广度层次遍历，深度遍历，前序，中序，后序
    
    // 使用线索二叉树的Morris遍历，但是会改变树的结构，直接变成单项升序链表形式
//    private void printTree(TreeNode node) {
//    	TreeNode current_node = node;
//    	while(current_node != null) {
//    		// 左孩子不为空的话，就找到左子树的最右节点指向自己
//    		if(current_node.left_node != null) {
//    			TreeNode leftNode = current_node.left_node;
//    			while(leftNode.right_node != null) {
//    				leftNode = leftNode.right_node;
//    			}
//    			leftNode.right_node = current_node;
//    			leftNode = current_node.left_node;
//    			current_node.left_node = null;
//    			current_node = leftNode;
//    		}else {
//    			System.out.print(current_node.node_value + ", ");
//    			current_node = current_node.right_node;
//    		}
//    	}
//	}
    
    // 上面那个会使得二叉树强行改变，不可取，因此有了这个。
    private void printTree(TreeNode node) {
    	TreeNode current_node = node;
    	while(current_node != null) {
    		// 左孩子不为空的话，就找到左子树的最右节点指向自己
    		if(current_node.left_node == null) {
    			System.out.print(current_node.node_value + ", ");
    			current_node = current_node.right_node;	
    		}else {
    			TreeNode leftNode = current_node.left_node;
    			while(leftNode.right_node != null && leftNode.right_node != current_node) {
    				leftNode = leftNode.right_node;
    			}
    			
    			if(leftNode.right_node == null) {   				
    				leftNode.right_node = current_node;
        			current_node = current_node.left_node;
    			}else {
    				System.out.print(current_node.node_value + ", ");
    				leftNode.right_node = null;
    				current_node = current_node.right_node;
    			}
    		}
    	}
	}
    
    
    // 广度层次遍历，使用的是队列
//    private void printTree(TreeNode node) {
//    	Queue<TreeNode> tree_queue = new LinkedList<TreeNode>(); 
//		tree_queue.offer(node);
//		
//		while(!tree_queue.isEmpty()) {
//			TreeNode queue_element = tree_queue.poll();
//			if(queue_element.left_node != null) tree_queue.offer(queue_element.left_node);
//			if(queue_element.right_node != null) tree_queue.offer(queue_element.right_node);
//			System.out.print(queue_element.node_value + ", ");
//		}
//		System.out.println();
//	}
    
    // 深度遍历，使用的是栈，前序遍历得非递归实现
//    private void printTree(TreeNode node) {
//    	Stack<TreeNode> tree_stack = new Stack<TreeNode>();
//    	tree_stack.push(node);
//    	while(!tree_stack.empty()) {
//    		TreeNode stack_element = tree_stack.pop();
//			if(stack_element.right_node != null) tree_stack.push(stack_element.right_node);
//			if(stack_element.left_node != null) tree_stack.push(stack_element.left_node);
//			System.out.print(stack_element.node_value + ", ");
//    	}
//    	System.out.println();
//    }
    
    // 前序遍历，递归
//    private void printTree(TreeNode node) {
//    	if(node != null) {
//    		System.out.print(node.node_value + ", ");
//    		printTree(node.left_node);
//        	printTree(node.right_node);
//    	}
//    }
    
    // 中序遍历，非递归
    // 思想就是用栈，先把当前节点的左子树压进去，之后拿出节点，再把右子树压进去
//    private void printTree(TreeNode node) {
//    	Stack<TreeNode> tree_stack = new Stack<TreeNode>();
//    	
//    	while(node != null || !tree_stack.empty()) {
//    		if(node != null) {
//    			tree_stack.push(node);
//    			node = node.left_node;
//    		}else {
//    			TreeNode current_node = tree_stack.pop();
//    			System.out.print(current_node.node_value + ", ");
//    			node = current_node.right_node;
//    		}
//    	}	
//    }
    
    // 中序遍历，递归
//    private void printTree(TreeNode node) {
//    	if(node != null) {
//    		printTree(node.left_node);
//    		System.out.print(node.node_value + ", ");
//        	printTree(node.right_node);
//    	}
//    }
    
    // 后序遍历，非递归
    // 使用双栈遍历，思想其实和中序递归差不多。
    // 一个栈是保存全部数据的，一个栈是把数据拿出来，找到它的左子树的,找到之后全部放入输出栈中
//    private void printTree(TreeNode node) {
//    	Stack<TreeNode> tree_stack = new Stack<TreeNode>();
//    	Stack<TreeNode> output_stack = new Stack<TreeNode>();
//    	
//    	while(node != null || !tree_stack.empty()) {
//    		if(node != null) {
//    			tree_stack.push(node);
//    			output_stack.push(node);
//    			node = node.right_node;
//    		}else {
//    			TreeNode current_node = tree_stack.pop();
//    			node = current_node.left_node;
//    		}
//    	}
//    	
//    	// 输出栈中的元素就是后序
//    	while(!output_stack.empty()) {
//    		TreeNode temp_node = output_stack.pop();
//    		System.out.print(temp_node.node_value + ", ");
//    	}
//    }
    
    // 后序遍历，非递归
    // 使用一个栈，外加一个标志，这个标志就是说明右子树是否被访问了。
//    private void printTree(TreeNode node) {
//    	Stack<TreeNode> tree_stack = new Stack<TreeNode>();
//    	TreeNode previsited_node = null;
//    	while(node != null || !tree_stack.empty()) {
//    		if(node != null) {
//    			tree_stack.push(node);
//    			node = node.left_node;
//    		}else {
//    			TreeNode right_node = tree_stack.peek().right_node;
//    			if(right_node == null || right_node == previsited_node) {
//    				TreeNode current_node = tree_stack.pop();
//    				System.out.print(current_node.node_value + ", ");
//    				previsited_node = current_node;
//    				node = null;
//    			}else {
//    				// 处理右子树
//    				node = right_node;
//    			}
//    		}
//    	}
//    	
//    }
    
    
    
    
    // 后序遍历，递归
//    private void printTree(TreeNode node) {
//    	if(node != null) {
//    		printTree(node.left_node);
//        	printTree(node.right_node);
//        	System.out.print(node.node_value + ", ");
//    	}
//    }
    
    
    	
}
