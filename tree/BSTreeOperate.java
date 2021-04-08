package tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BSTreeOperate {
	
	public static void main(String[] args) {
		// ����Ľ��
		int[] data = {6,2,8,1,4,3};
		BSTreeOperate bsto = new BSTreeOperate();
		TreeNode rootNode = bsto.constructTree(data);
		bsto.printTree(rootNode);
//		TreeNode value_node = bsto.searchValue(rootNode, 2);
//		if(value_node == null) {
//			System.out.println("���в������˽ڵ�");
//		}else {
//			System.out.println("���а�������ڵ�");
//		}
//		bsto.deleteNode(rootNode, 2);
//		value_node = bsto.searchValue(rootNode, 2);
//		if(value_node == null) {
//			System.out.println("���в������˽ڵ�");
//		}else {
//			System.out.println("���а�������ڵ�");
//		}
//		bsto.deleteNode(rootNode, 2);
	}

	// ����BST�� 
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

	// ��BST���в���ڵ� 
	private TreeNode insertNode(TreeNode node, int data) {
		if(node == null) {
			// ��߲���ֱ�Ӹ�ֵ�ģ����ǳ�ʼ��
			node = new TreeNode(data);
		}else if(node.node_value == data){
			System.out.println("�Ѿ�����ͬ�Ľڵ����");
		}else if(node.node_value > data) {
			node.left_node = insertNode(node.left_node,data);
		}else if(node.node_value < data) {
			node.right_node = insertNode(node.right_node,data);
		}
		return node;
	}
	
//	// ����BST���еĽ�㣬�ݹ�д��
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
	
	// ����BST���еĽ�㣬�ǵݹ�д��
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
    
    // ɾ��BST���еĽڵ�
    // 1. �����Ҷ�ڵ㣬��ôֱ�ӱ��null
    // 2. ���ֻ����������������������ôֱ������������������������
    // 3. ������Ҷ��У���ô�ȴ����������ҳ�ֱ�Ӻ�̣�Ҳ����������������С��ֵ��
    //              �����ȴ����������ҳ�ֱ��ǰ����Ҳ����������������ֵ��
    private void deleteNode(TreeNode rootNode, int delete_value) {
		TreeNode deleteNode = searchValue(rootNode,delete_value);
		if(deleteNode == null) {
			System.out.println("����û�иýڵ�Ĵ���");
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

	// ������,�ֱ����Morris��������Ȳ�α�������ȱ�����ǰ�����򣬺���
    
    // ʹ��������������Morris���������ǻ�ı����Ľṹ��ֱ�ӱ�ɵ�������������ʽ
//    private void printTree(TreeNode node) {
//    	TreeNode current_node = node;
//    	while(current_node != null) {
//    		// ���Ӳ�Ϊ�յĻ������ҵ������������ҽڵ�ָ���Լ�
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
    
    // �����Ǹ���ʹ�ö�����ǿ�иı䣬����ȡ��������������
    private void printTree(TreeNode node) {
    	TreeNode current_node = node;
    	while(current_node != null) {
    		// ���Ӳ�Ϊ�յĻ������ҵ������������ҽڵ�ָ���Լ�
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
    
    
    // ��Ȳ�α�����ʹ�õ��Ƕ���
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
    
    // ��ȱ�����ʹ�õ���ջ��ǰ������÷ǵݹ�ʵ��
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
    
    // ǰ��������ݹ�
//    private void printTree(TreeNode node) {
//    	if(node != null) {
//    		System.out.print(node.node_value + ", ");
//    		printTree(node.left_node);
//        	printTree(node.right_node);
//    	}
//    }
    
    // ����������ǵݹ�
    // ˼�������ջ���Ȱѵ�ǰ�ڵ��������ѹ��ȥ��֮���ó��ڵ㣬�ٰ�������ѹ��ȥ
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
    
    // ����������ݹ�
//    private void printTree(TreeNode node) {
//    	if(node != null) {
//    		printTree(node.left_node);
//    		System.out.print(node.node_value + ", ");
//        	printTree(node.right_node);
//    	}
//    }
    
    // ����������ǵݹ�
    // ʹ��˫ջ������˼����ʵ������ݹ��ࡣ
    // һ��ջ�Ǳ���ȫ�����ݵģ�һ��ջ�ǰ������ó������ҵ�������������,�ҵ�֮��ȫ���������ջ��
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
//    	// ���ջ�е�Ԫ�ؾ��Ǻ���
//    	while(!output_stack.empty()) {
//    		TreeNode temp_node = output_stack.pop();
//    		System.out.print(temp_node.node_value + ", ");
//    	}
//    }
    
    // ����������ǵݹ�
    // ʹ��һ��ջ�����һ����־�������־����˵���������Ƿ񱻷����ˡ�
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
//    				// ����������
//    				node = right_node;
//    			}
//    		}
//    	}
//    	
//    }
    
    
    
    
    // ����������ݹ�
//    private void printTree(TreeNode node) {
//    	if(node != null) {
//    		printTree(node.left_node);
//        	printTree(node.right_node);
//        	System.out.print(node.node_value + ", ");
//    	}
//    }
    
    
    	
}
