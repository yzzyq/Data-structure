package tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BTreeOperate {

	public static void main(String[] args) {
		// 插入的结点
		int[] data = { 6, 2, 8, 1, 4};
		BTreeOperate bto = new BTreeOperate();
		BTreeNode rootNode = bto.constructTree(data);
		bto.printTree(rootNode);
		BTreeNode value_node = bto.searchValue(rootNode, 2);
		if (value_node == null) {
			System.out.println("树中不包含此节点");
		} else {
			System.out.println("树中包含这个节点");
		}
		bto.deleteNode(rootNode, 2);
		bto.printTree(rootNode);
		value_node = bto.searchValue(rootNode, 2);
		if (value_node == null) {
			System.out.println("树中不包含此节点");
		} else {
			System.out.println("树中包含这个节点");
		}
		bto.deleteNode(rootNode, 2);
	}
	
	// 删除操作的入口
	public BTreeNode deleteNode(BTreeNode rootNode, int value) {
		rootNode = deleteProcess(rootNode,value);
		if(rootNode.keys.size() == 0 && rootNode.b_node.size() == 1) {
			rootNode = rootNode.b_node.get(0);
		}
		return rootNode;
	}

	// 删除,操作比较复杂，一共有十种情况，
	private BTreeNode deleteProcess(BTreeNode rootNode, int value) {
		// 首先确定值是不是在当前节点中
		int index = rootNode.containKey(value);
		int min_num = (int)rootNode.m / 2 - 1;
		if(index != -1) {
			// 如果在当前节点，并且是叶子节点，那么直接删掉
			if(rootNode.isLeaf) {
				rootNode.keys.remove(index);
				return rootNode;
			}else {
				BTreeNode left_node = rootNode.b_node.get(index);
				BTreeNode right_node = rootNode.b_node.get(index + 1);
				// 如果在当前节点，但是不是叶子节点，那么看左右子节点谁的Key个数大于最小值，就找出一个最值来代替当前节点的key
				// 查看左节点是否大于最小值
				if(left_node.keys.size() > min_num) {
					// 找出该节点的直接前驱代替这个节点
					int max_value = findMax(left_node);
					rootNode.keys.set(index, max_value);
					left_node = deleteNode(left_node,max_value);
					return rootNode;
				}else if(right_node.keys.size() > min_num) {
					// 查看右节点是否大于最小值
					// 找出该节点的直接后继代替这个节点
					int min_value = findMin(right_node);
					rootNode.keys.set(index, min_value);
					right_node = deleteNode(right_node,min_value);
					return rootNode;
				}else {
					
					// 如果左右都是最小数量的节点数，先将要删除的key插入,为了方便后面插入子树
					left_node.keys.add(value);
					
					// 再将俩个节点的key合并
					for(int one_key:right_node.keys) {
						left_node.keys.add(one_key);
					}
					if(!right_node.isLeaf) {
						for(BTreeNode one_node:right_node.b_node) {
							left_node.b_node.add(one_node);
						}
					}
					
					// 直接删除key的值
					rootNode.keys.remove(index);
					// 删除右节点
					rootNode.b_node.remove(index+1);
                    left_node = deleteNode(left_node, value);     
					return rootNode;
				}
			}
		}else {
			if(rootNode.isLeaf) {
				System.out.println("删除失败，数不在该树中");
				return rootNode;
			}
			
			// 如果不在当前节点中，那么需要检查子节点的值的个数是否大于最小数量
			int pos = rootNode.searchInsertPos(value);
			BTreeNode current_path = rootNode.b_node.get(pos);
			if(current_path.keys.size() == min_num) {
				// 子节点是最小数量，那么需要先进行处理，再往下走
				// 看兄弟是否可借，进行借数
				// 如果有右兄弟，那么借右兄弟的
				if(pos < rootNode.keys.size() && rootNode.b_node.get(pos + 1).keys.size() > min_num) {
					// 借右兄弟的
					BTreeNode right_node = rootNode.b_node.get(pos + 1);
					current_path.keys.add(rootNode.keys.get(pos));
					if(!right_node.isLeaf) current_path.b_node.add(right_node.b_node.remove(0));
					rootNode.keys.set(pos, right_node.keys.remove(0));
					current_path = deleteNode(current_path,value);
					return rootNode;
				}else if(pos == rootNode.keys.size() && rootNode.b_node.get(pos - 1).keys.size() > min_num){
					// 借左兄弟的
					BTreeNode left_node = rootNode.b_node.get(pos - 1);
					current_path.keys.add(0, rootNode.keys.get(pos - 1));
					if(!left_node.isLeaf) current_path.b_node.add(0, left_node.b_node.remove(left_node.keys.size()));
					rootNode.keys.set(pos - 1, left_node.keys.remove(left_node.keys.size()-1));
					current_path = deleteNode(current_path,value);
					return rootNode;
				}else {
					// 左右兄弟都不可借，合并操作
					// 有右兄弟
					if(pos < rootNode.keys.size()) {
						BTreeNode right_node = rootNode.b_node.get(pos + 1);
						current_path.keys.add(rootNode.keys.get(pos));
						for(int one_key:right_node.keys) {
							current_path.keys.add(one_key);
						}
						if(!right_node.isLeaf) {
							for(BTreeNode one_node:right_node.b_node) {
								current_path.b_node.add(one_node);
							}
						}
						rootNode.keys.remove(pos);
						rootNode.b_node.remove(pos+1);
						current_path = deleteProcess(current_path, value);
						return rootNode;
					}else {
						// 只有左兄弟
						BTreeNode left_node = rootNode.b_node.get(pos - 1);
						current_path.keys.add(0,rootNode.keys.get(pos));
						for(int one_index = left_node.keys.size() - 1;one_index >= 0;one_index--) {
							current_path.keys.add(0,left_node.keys.get(one_index));
						}
						if(!left_node.isLeaf) {
							for(int one_index = left_node.b_node.size() - 1;one_index >= 0;one_index--) {
								current_path.b_node.add(0,left_node.b_node.get(one_index));
							}
						}
						rootNode.keys.remove(pos);
						rootNode.b_node.remove(pos-1);
						current_path = deleteProcess(current_path, value);
						return rootNode;
					}
					
				}
				
			}else {
				current_path = deleteProcess(current_path, value);
				return rootNode;
			}
			
		}
		
	}

	private int findMin(BTreeNode right_node) {
		int min_value = right_node.keys.get(0);
		while(right_node.b_node.size() > 0) {
			right_node = right_node.b_node.get(0);
			min_value = right_node.keys.get(0);
		}
		return min_value;
	}

	private int findMax(BTreeNode left_node) {
		int max_value = left_node.keys.get(left_node.keys.size() - 1);
		while(left_node.b_node.size() > 0) {
			left_node = left_node.b_node.get(left_node.b_node.size());
			max_value = left_node.keys.get(left_node.keys.size() - 1);
		}
		
		return max_value;
	}

	// 查找
	private BTreeNode searchValue(BTreeNode rootNode, int value) {
		Iterator iter = rootNode.keys.iterator();
		int index = 0;
		boolean notResult = true;
		while(iter.hasNext() && notResult) {
			int current_key = (int) iter.next();
			if(value == current_key) {
				notResult= false;
				return rootNode;
			}else if(value < current_key && !rootNode.isLeaf) {
				notResult= false;
				return searchValue(rootNode.b_node.get(index),value);
			}
			index++;
		}
		if(notResult && !rootNode.isLeaf) {
			return searchValue(rootNode.b_node.get(index),value);
		}
		
		return null;
	}

	private BTreeNode constructTree(int[] data) {
		BTreeNode rootNode = new BTreeNode();
		int index = 0;
		while(index < data.length) {
			// 检查根节点是否需要分裂
			if(rootNode.keys.size() == rootNode.m-1) {
				BTreeNode newRoot = new BTreeNode();
				newRoot.isLeaf = false;
				newRoot.b_node.add(0, rootNode);
				rootNode = splitNode(newRoot,rootNode,0);
			}
			rootNode = insertNode(rootNode, data[index]);
			System.out.println(data[index]);
			index++;
		}
		return rootNode;
	}

	// 插入操作 
	private BTreeNode insertNode(BTreeNode rootNode, int value) {
		
		if(rootNode.isLeaf) {
			// 找到的话，直接插入即可，因为叶节点没有子节点，所以不用考虑子节点的去向		
			rootNode.insertkey(value);
		}else {
			// 如果所走的子节点满了就需要进行分裂操作
			Iterator it = rootNode.keys.iterator();
			boolean notFind = true;
			int insert_index = 0;
			while(it.hasNext() && notFind) {
				int current_key = (int) it.next();
				if(value < current_key) {
					// 找到插入位置
					notFind = false;
					// 检查子节点是否满了
					if(rootNode.b_node.get(insert_index).keys.size() == rootNode.m - 1) {
						rootNode = splitNode(rootNode,rootNode.b_node.get(insert_index),insert_index);
					}
					
					// 重新判断这个值的大小
					if(value > rootNode.keys.get(insert_index)) {
						BTreeNode current_node = rootNode.b_node.get(insert_index + 1);
						current_node = insertNode(current_node,value);
					}else {
						BTreeNode current_node = rootNode.b_node.get(insert_index);
						current_node = insertNode(current_node,value);
					}
					
				}else if(value == current_key) {
					// 树中有相同的值
					System.out.println("树中有相同的值");
				}else {
					insert_index++;
				}
			}
			
			// 插入到最右边
			if(notFind) {
				// 检查子节点是否满了
				if(rootNode.b_node.get(insert_index).keys.size() == rootNode.m - 1) {
					rootNode = splitNode(rootNode,rootNode.b_node.get(insert_index),insert_index);
				}
				
				// 重新判断这个值的大小
				if(value > rootNode.keys.get(insert_index)) {
					BTreeNode current_node = rootNode.b_node.get(insert_index + 1);
					current_node = insertNode(current_node,value);
				}
				BTreeNode current_node = rootNode.b_node.get(insert_index);
				current_node = insertNode(current_node,value);
			}
				
		}
		
		return rootNode;
	}

	// 分裂操作
	private BTreeNode splitNode(BTreeNode rootNode, BTreeNode childNode, int insert_index) {
		int middle = childNode.m / 2 - 1;
		// 之前的节点做左节点，新定义的节点做右节点
		BTreeNode right_leaf = new BTreeNode();
		right_leaf.isLeaf = childNode.isLeaf;
		rootNode.b_node.add(insert_index + 1, right_leaf);
		// 定义好右节点
		if(!childNode.isLeaf) right_leaf.b_node.add(0, childNode.b_node.remove(childNode.m-1));
		int key_index = childNode.m - 2;
		while(key_index > middle) {
			right_leaf.keys.add(0, childNode.keys.remove(key_index));
			if(!childNode.isLeaf) right_leaf.b_node.add(0, childNode.b_node.remove(key_index));
			key_index--;
		}
		// 父节点和分裂的节点合并
		rootNode.keys.add(insert_index, childNode.keys.remove(middle));
		
		return rootNode;
	}
	
	// 层次遍历
	private void printTree(BTreeNode rootNode) {
		Queue<BTreeNode> node_queue = new LinkedList<BTreeNode>();
		node_queue.offer(rootNode);

		while (!node_queue.isEmpty()) {
			BTreeNode temp_node = node_queue.poll();
			int start_index = 0;
			for(;start_index < temp_node.keys.size();start_index++) {
				System.out.print(temp_node.keys.get(start_index) +" | ");
				if(!temp_node.isLeaf) node_queue.offer(temp_node.b_node.get(start_index));
			}
			if(!temp_node.isLeaf) node_queue.offer(temp_node.b_node.get(start_index));
			System.out.println();
		}
	}

}
