package tree;

import java.util.LinkedList;
import java.util.Queue;

public class RBTreeOperate {

	public static void main(String[] args) {
		// ����Ľ��
		int[] data = {6,2,8,1,4,3};
//		int[] data = {3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9};
        
		RBTreeOperate rbt_operate = new RBTreeOperate();
		RBTNode rootNode = rbt_operate.constructTree(data);
		System.out.println("��ʼ�������");
		rbt_operate.printTree(rootNode);
		RBTNode value_node = rbt_operate.searchValue(rootNode, 2);
		if(value_node == null) {
			System.out.println("���в������˽ڵ�");
		}else {
			System.out.println("���а�������ڵ�");
		}
		rbt_operate.deleteNode(rootNode, 2);
		value_node = rbt_operate.searchValue(rootNode, 2);
		if(value_node == null) {
			System.out.println("���в������˽ڵ�");
		}else {
			System.out.println("���а�������ڵ�");
		}
		rbt_operate.printTree(rootNode);
		rbt_operate.deleteNode(rootNode, 2);

	}
    
	
	// ɾ���ڵ�
	private RBTNode deleteNode(RBTNode rootNode, int value) {
		if(searchValue(rootNode, value) == null) {
			System.out.println("���в������˽ڵ�");
			return rootNode;
		}
		
		// ��Ϊ������������Ƕ����Ǻ�ڵ㣬��������2-�ڵ㣬�������к�ڵ㣬���ܱ�ɫ��
		if(!isRed(rootNode.left_node) && !isRed(rootNode.right_node)) {
			rootNode.color_node = true;
		}
		rootNode = deleteProcess(rootNode,value);
		if(rootNode.color_node) {
			rootNode.color_node = false;
		}
		return rootNode;
	}


	private RBTNode deleteProcess(RBTNode rootNode, int value) {
		
		if(rootNode.node_value > value) {
			// ������λ������ߵ�2-�ڵ�
			if(!isRed(rootNode.left_node) && !isRed(rootNode.left_node.left_node)) {
				rootNode = moveRedLeft(rootNode);
			}
			rootNode.left_node = deleteProcess(rootNode.left_node, value);
		}else if(rootNode.node_value < value) {
			// �����Ȱ�3-�ڵ�ĺ�ɫ�ڵ��Ƶ��ұ�ȥ��������Ϊ������ƶ��Ļ����ͻ���ֶ��Ǻ�ɫ�����
			// 4-�ڵ㲻��Ҫ�ƶ�
			if(isRed(rootNode.left_node) && !isRed(rootNode.right_node)) {
				rootNode = LLrotate(rootNode);
			}
			
			if(!isRed(rootNode.right_node) && !isRed(rootNode.right_node.left_node)) {
				rootNode = moveRedRight(rootNode);
			}
			rootNode.right_node = deleteProcess(rootNode.right_node, value);
		}else {
			// ��Ϊ���Ľڵ�����ɫ�������ܼ򵥵Ĵ��棬��Ҫ������ɫ�ı仯
			// 1. �ڵ���Ҷ�ڵ㣬ֱ��ɾ��
			// 2. �ڵ�����ڵ㣬ֱ������ڵ����ɾ���������о���LL��ת
			// 3. �ڵ����ҽڵ㣬ѡ����С�Ĵ���ɾ���ڵ�
			// 2. �ڵ�����ڵ���ҽڵ㣬���нڵ���ѡ����С�Ĵ���ɾ���ڵ㡣
			
			// �Ƚ���ɫ�ڵ�������ƣ�Ȼ��ͳһ�����ұ߼���
			if(isRed(rootNode.left_node) && !isRed(rootNode.right_node)) {
				rootNode = LLrotate(rootNode);
			}
			
			// ���1�����2�Ľ��
			if(rootNode.node_value == value && rootNode.right_node == null) {
				return null;
			}
			
			// ������Ҫ�ж��ǲ���2-�ڵ㣬��Ϊ���ܴ�2-�ڵ���ɾ��
			if(!isRed(rootNode.left_node) && !isRed(rootNode.right_node)) {
				rootNode = moveRedRight(rootNode);
			}
			
			// ��������͵��������
			if(rootNode.node_value == value) {
				int min_value = findMin(rootNode.right_node);
				rootNode.node_value = min_value;
				// ɾ������������Сֵ
//				rootNode.right_node = deleteNode(rootNode.right_node, min_value);
				// ���ﻹ�ǵ���дһ����ã���Ϊ���ʱ��ĸ��ڵ㲻��Ҫ��ɺ�ɫ��
				rootNode.right_node = deleteMin(rootNode.right_node);
			}else {
				// ������Ϊ����LL��ת�����½ڵ�仯������
				rootNode.right_node = deleteProcess(rootNode.right_node, value);
			}
		}
		
		// ����������һ�����㣬��Ҫ����
		return fixup(rootNode);
	}
	
	public RBTNode deleteMin(RBTNode cn) {
		// ����������������ڵ�û�У���ô�ҽڵ�һ��Ҳû�С�
		if (cn.left_node == null) return null;
		
		if (!isRed(cn.left_node) && !isRed(cn.left_node.left_node))  
			cn = moveRedLeft(cn);
		
		cn.left_node = deleteMin(cn.left_node);
		
		return fixup(cn);
	}



	private RBTNode fixup(RBTNode rootNode) {
		if(!isRed(rootNode.left_node) && isRed(rootNode.right_node)) {
			rootNode = RRrotate(rootNode);
		}
		
		if(isRed(rootNode.left_node) && isRed(rootNode.left_node.left_node)) {
			rootNode = LLrotate(rootNode);
		}
		
		return rootNode;
	}



	private int findMin(RBTNode node) {
		int min_value = node.node_value;
		while(node.left_node != null) {
			node = node.left_node;
			min_value = node.node_value;
		}
		return min_value;
	}



	private RBTNode moveRedRight(RBTNode rootNode) {
		rootNode = colorFilp(rootNode);
		if(isRed(rootNode.left_node.left_node)) {
			rootNode = LLrotate(rootNode);
			rootNode = colorFilp(rootNode);
		}
		return rootNode;
	}


    // 
	private RBTNode moveRedLeft(RBTNode rootNode) {
		rootNode = colorFilp(rootNode);
		if(isRed(rootNode.right_node.left_node)) {
			rootNode = RLrotate(rootNode);
			rootNode = colorFilp(rootNode);
		}
		
		return rootNode;
	}



	private RBTNode searchValue(RBTNode rootNode, int value) {
		
		while(rootNode != null) {
			if(rootNode.node_value > value) {
				rootNode = rootNode.left_node;
			}else if(rootNode.node_value < value){
				rootNode = rootNode.right_node;
			}else {
				return rootNode;
			}
		}
		return null;
	}


	// ��α���
	private void printTree(RBTNode rootNode) {
		Queue<RBTNode> node_queue = new LinkedList<RBTNode>();
		node_queue.offer(rootNode);
		
		while(!node_queue.isEmpty()) {
			RBTNode temp_node = node_queue.poll();
			System.out.print(temp_node.node_value + ", ");
			if(temp_node.color_node) {
				System.out.print("red ---> ");
			}else {
				System.out.print("black ---> ");
			}
			if(temp_node.left_node != null) node_queue.offer(temp_node.left_node);
			if(temp_node.right_node != null) node_queue.offer(temp_node.right_node);
		}	
		System.out.println();
	}

	private RBTNode constructTree(int[] data) {
		RBTNode rootNode = new RBTNode(data[0]);
		rootNode.color_node = false;
		int index = 1;
		while(index < data.length) {
			rootNode = insertNode(rootNode, data[index]);
			rootNode.color_node = false;
			System.out.println(data[index]);
			index++;
		}
		return rootNode;
	}

	private RBTNode insertNode(RBTNode node, int value) {
		
		if(node != null) {
			// �ж��費��Ҫ��ɫ����4-�ڵ�ͱ�ɫ
			if(isRed(node.left_node) && isRed(node.right_node)) {
				node = splitFour(node);
			}
			if(value < node.node_value) {
				node.left_node = insertNode(node.left_node, value);
			}else if(value > node.node_value) {
				node.right_node = insertNode(node.right_node, value);
			}else {
				System.out.println("�����Ѿ�����ͬ�Ľڵ�");
			}
		}else {
			node = new RBTNode(value);
		}
		
		// ֻ��Ҫ�ж�����������У����Ƿ�Ҫ����ת����������ת
		if(!isRed(node.left_node) && isRed(node.right_node)) node = RRrotate(node);
		if(isRed(node.left_node) && isRed(node.left_node.left_node)) node = LLrotate(node);
		
		return node;
	}
	
	// ��ɫ
	private RBTNode colorFilp(RBTNode rootNode) {
		rootNode.color_node = !rootNode.color_node;
		rootNode.left_node.color_node = !rootNode.left_node.color_node;
		rootNode.right_node.color_node = !rootNode.right_node.color_node;
		return rootNode;
	}
	
    // RL��ת
	private RBTNode RLrotate(RBTNode rootNode) {
		rootNode.right_node = LLrotate(rootNode.right_node);
		return RRrotate(rootNode);
	}
	
	// ����ת
	private RBTNode LLrotate(RBTNode node) {
		RBTNode leftNode = node.left_node;
		node.left_node = leftNode.right_node;
		leftNode.right_node = node;
		
		// ��ɫ
		leftNode.color_node = !leftNode.color_node;
		node.color_node = !node.color_node;
		
		return leftNode;
	}

	// ����ת
	private RBTNode RRrotate(RBTNode node) {
		RBTNode rightNode = node.right_node;
		node.right_node = rightNode.left_node;
		rightNode.left_node = node;
		
		// ��ɫ
		rightNode.color_node = !rightNode.color_node;
		node.color_node = !node.color_node;
		
		return rightNode;
	}

	// ���Ѳ�����Ҳ���Ǳ�ɫ
	private RBTNode splitFour(RBTNode node) {
		node.color_node = !node.color_node;
		node.left_node.color_node = !node.left_node.color_node;
		node.right_node.color_node = !node.right_node.color_node;
		return node;
	}

	// �ж��Ƿ��Ǻ�ɫ��ΪʲôҪ�������������ΪҪ��֤����ڵ��Ƿ����
	private boolean isRed(RBTNode node) {
		if(node != null) {
			return node.color_node;
		}
		return false;
	}

}
