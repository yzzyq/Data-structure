package tree;

import java.util.LinkedList;
import java.util.Queue;

public class RBTreeOperate {

	public static void main(String[] args) {
		// 插入的结点
		int[] data = {6,2,8,1,4,3};
//		int[] data = {3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9};
        
		RBTreeOperate rbt_operate = new RBTreeOperate();
		RBTNode rootNode = rbt_operate.constructTree(data);
		System.out.println("初始化树完成");
		rbt_operate.printTree(rootNode);
		RBTNode value_node = rbt_operate.searchValue(rootNode, 2);
		if(value_node == null) {
			System.out.println("树中不包含此节点");
		}else {
			System.out.println("树中包含这个节点");
		}
		rbt_operate.deleteNode(rootNode, 2);
		value_node = rbt_operate.searchValue(rootNode, 2);
		if(value_node == null) {
			System.out.println("树中不包含此节点");
		}else {
			System.out.println("树中包含这个节点");
		}
		rbt_operate.printTree(rootNode);
		rbt_operate.deleteNode(rootNode, 2);

	}
    
	
	// 删除节点
	private RBTNode deleteNode(RBTNode rootNode, int value) {
		if(searchValue(rootNode, value) == null) {
			System.out.println("树中不包含此节点");
			return rootNode;
		}
		
		// 因为处理的条件就是顶点是红节点，因而如果是2-节点，并且先有红节点，才能变色。
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
			// 检查查找位置在左边的2-节点
			if(!isRed(rootNode.left_node) && !isRed(rootNode.left_node.left_node)) {
				rootNode = moveRedLeft(rootNode);
			}
			rootNode.left_node = deleteProcess(rootNode.left_node, value);
		}else if(rootNode.node_value < value) {
			// 首先先把3-节点的红色节点移到右边去，这是因为如果不移动的话，就会出现都是黑色的情况
			// 4-节点不需要移动
			if(isRed(rootNode.left_node) && !isRed(rootNode.right_node)) {
				rootNode = LLrotate(rootNode);
			}
			
			if(!isRed(rootNode.right_node) && !isRed(rootNode.right_node.left_node)) {
				rootNode = moveRedRight(rootNode);
			}
			rootNode.right_node = deleteProcess(rootNode.right_node, value);
		}else {
			// 因为它的节点有颜色，并不能简单的代替，还要考虑颜色的变化
			// 1. 节点是叶节点，直接删除
			// 2. 节点有左节点，直接用左节点代替删除，代码中就是LL旋转
			// 3. 节点有右节点，选出最小的代替删除节点
			// 2. 节点有左节点和右节点，从有节点中选出最小的代替删除节点。
			
			// 先将红色节点进行右移，然后统一处理右边即可
			if(isRed(rootNode.left_node) && !isRed(rootNode.right_node)) {
				rootNode = LLrotate(rootNode);
			}
			
			// 情况1和情况2的结合
			if(rootNode.node_value == value && rootNode.right_node == null) {
				return null;
			}
			
			// 依旧需要判断是不是2-节点，因为不能从2-节点中删除
			if(!isRed(rootNode.left_node) && !isRed(rootNode.right_node)) {
				rootNode = moveRedRight(rootNode);
			}
			
			// 处理第三和第四种情况
			if(rootNode.node_value == value) {
				int min_value = findMin(rootNode.right_node);
				rootNode.node_value = min_value;
				// 删除右子树的最小值
//				rootNode.right_node = deleteNode(rootNode.right_node, min_value);
				// 这里还是单独写一个最好，因为这个时候的根节点不需要变成黑色。
				rootNode.right_node = deleteMin(rootNode.right_node);
			}else {
				// 处理因为上面LL旋转而导致节点变化的问题
				rootNode.right_node = deleteProcess(rootNode.right_node, value);
			}
		}
		
		// 别忘了这是一个左倾，需要调整
		return fixup(rootNode);
	}
	
	public RBTNode deleteMin(RBTNode cn) {
		// 这是左倾红黑树，左节点没有，那么右节点一定也没有。
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


	// 层次遍历
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
			// 判断需不需要变色，有4-节点就变色
			if(isRed(node.left_node) && isRed(node.right_node)) {
				node = splitFour(node);
			}
			if(value < node.node_value) {
				node.left_node = insertNode(node.left_node, value);
			}else if(value > node.node_value) {
				node.right_node = insertNode(node.right_node, value);
			}else {
				System.out.println("树中已经有相同的节点");
			}
		}else {
			node = new RBTNode(value);
		}
		
		// 只需要判断俩种情况就行，看是否要左旋转还是左右旋转
		if(!isRed(node.left_node) && isRed(node.right_node)) node = RRrotate(node);
		if(isRed(node.left_node) && isRed(node.left_node.left_node)) node = LLrotate(node);
		
		return node;
	}
	
	// 变色
	private RBTNode colorFilp(RBTNode rootNode) {
		rootNode.color_node = !rootNode.color_node;
		rootNode.left_node.color_node = !rootNode.left_node.color_node;
		rootNode.right_node.color_node = !rootNode.right_node.color_node;
		return rootNode;
	}
	
    // RL旋转
	private RBTNode RLrotate(RBTNode rootNode) {
		rootNode.right_node = LLrotate(rootNode.right_node);
		return RRrotate(rootNode);
	}
	
	// 右旋转
	private RBTNode LLrotate(RBTNode node) {
		RBTNode leftNode = node.left_node;
		node.left_node = leftNode.right_node;
		leftNode.right_node = node;
		
		// 变色
		leftNode.color_node = !leftNode.color_node;
		node.color_node = !node.color_node;
		
		return leftNode;
	}

	// 左旋转
	private RBTNode RRrotate(RBTNode node) {
		RBTNode rightNode = node.right_node;
		node.right_node = rightNode.left_node;
		rightNode.left_node = node;
		
		// 变色
		rightNode.color_node = !rightNode.color_node;
		node.color_node = !node.color_node;
		
		return rightNode;
	}

	// 分裂操作，也就是变色
	private RBTNode splitFour(RBTNode node) {
		node.color_node = !node.color_node;
		node.left_node.color_node = !node.left_node.color_node;
		node.right_node.color_node = !node.right_node.color_node;
		return node;
	}

	// 判断是否是红色，为什么要有这个函数，因为要保证这个节点是否存在
	private boolean isRed(RBTNode node) {
		if(node != null) {
			return node.color_node;
		}
		return false;
	}

}
