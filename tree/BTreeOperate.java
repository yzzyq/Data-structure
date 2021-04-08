package tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BTreeOperate {

	public static void main(String[] args) {
		// ����Ľ��
		int[] data = { 6, 2, 8, 1, 4};
		BTreeOperate bto = new BTreeOperate();
		BTreeNode rootNode = bto.constructTree(data);
		bto.printTree(rootNode);
		BTreeNode value_node = bto.searchValue(rootNode, 2);
		if (value_node == null) {
			System.out.println("���в������˽ڵ�");
		} else {
			System.out.println("���а�������ڵ�");
		}
		bto.deleteNode(rootNode, 2);
		bto.printTree(rootNode);
		value_node = bto.searchValue(rootNode, 2);
		if (value_node == null) {
			System.out.println("���в������˽ڵ�");
		} else {
			System.out.println("���а�������ڵ�");
		}
		bto.deleteNode(rootNode, 2);
	}
	
	// ɾ�����������
	public BTreeNode deleteNode(BTreeNode rootNode, int value) {
		rootNode = deleteProcess(rootNode,value);
		if(rootNode.keys.size() == 0 && rootNode.b_node.size() == 1) {
			rootNode = rootNode.b_node.get(0);
		}
		return rootNode;
	}

	// ɾ��,�����Ƚϸ��ӣ�һ����ʮ�������
	private BTreeNode deleteProcess(BTreeNode rootNode, int value) {
		// ����ȷ��ֵ�ǲ����ڵ�ǰ�ڵ���
		int index = rootNode.containKey(value);
		int min_num = (int)rootNode.m / 2 - 1;
		if(index != -1) {
			// ����ڵ�ǰ�ڵ㣬������Ҷ�ӽڵ㣬��ôֱ��ɾ��
			if(rootNode.isLeaf) {
				rootNode.keys.remove(index);
				return rootNode;
			}else {
				BTreeNode left_node = rootNode.b_node.get(index);
				BTreeNode right_node = rootNode.b_node.get(index + 1);
				// ����ڵ�ǰ�ڵ㣬���ǲ���Ҷ�ӽڵ㣬��ô�������ӽڵ�˭��Key����������Сֵ�����ҳ�һ����ֵ�����浱ǰ�ڵ��key
				// �鿴��ڵ��Ƿ������Сֵ
				if(left_node.keys.size() > min_num) {
					// �ҳ��ýڵ��ֱ��ǰ����������ڵ�
					int max_value = findMax(left_node);
					rootNode.keys.set(index, max_value);
					left_node = deleteNode(left_node,max_value);
					return rootNode;
				}else if(right_node.keys.size() > min_num) {
					// �鿴�ҽڵ��Ƿ������Сֵ
					// �ҳ��ýڵ��ֱ�Ӻ�̴�������ڵ�
					int min_value = findMin(right_node);
					rootNode.keys.set(index, min_value);
					right_node = deleteNode(right_node,min_value);
					return rootNode;
				}else {
					
					// ������Ҷ�����С�����Ľڵ������Ƚ�Ҫɾ����key����,Ϊ�˷�������������
					left_node.keys.add(value);
					
					// �ٽ������ڵ��key�ϲ�
					for(int one_key:right_node.keys) {
						left_node.keys.add(one_key);
					}
					if(!right_node.isLeaf) {
						for(BTreeNode one_node:right_node.b_node) {
							left_node.b_node.add(one_node);
						}
					}
					
					// ֱ��ɾ��key��ֵ
					rootNode.keys.remove(index);
					// ɾ���ҽڵ�
					rootNode.b_node.remove(index+1);
                    left_node = deleteNode(left_node, value);     
					return rootNode;
				}
			}
		}else {
			if(rootNode.isLeaf) {
				System.out.println("ɾ��ʧ�ܣ������ڸ�����");
				return rootNode;
			}
			
			// ������ڵ�ǰ�ڵ��У���ô��Ҫ����ӽڵ��ֵ�ĸ����Ƿ������С����
			int pos = rootNode.searchInsertPos(value);
			BTreeNode current_path = rootNode.b_node.get(pos);
			if(current_path.keys.size() == min_num) {
				// �ӽڵ�����С��������ô��Ҫ�Ƚ��д�����������
				// ���ֵ��Ƿ�ɽ裬���н���
				// ��������ֵܣ���ô�����ֵܵ�
				if(pos < rootNode.keys.size() && rootNode.b_node.get(pos + 1).keys.size() > min_num) {
					// �����ֵܵ�
					BTreeNode right_node = rootNode.b_node.get(pos + 1);
					current_path.keys.add(rootNode.keys.get(pos));
					if(!right_node.isLeaf) current_path.b_node.add(right_node.b_node.remove(0));
					rootNode.keys.set(pos, right_node.keys.remove(0));
					current_path = deleteNode(current_path,value);
					return rootNode;
				}else if(pos == rootNode.keys.size() && rootNode.b_node.get(pos - 1).keys.size() > min_num){
					// �����ֵܵ�
					BTreeNode left_node = rootNode.b_node.get(pos - 1);
					current_path.keys.add(0, rootNode.keys.get(pos - 1));
					if(!left_node.isLeaf) current_path.b_node.add(0, left_node.b_node.remove(left_node.keys.size()));
					rootNode.keys.set(pos - 1, left_node.keys.remove(left_node.keys.size()-1));
					current_path = deleteNode(current_path,value);
					return rootNode;
				}else {
					// �����ֵܶ����ɽ裬�ϲ�����
					// �����ֵ�
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
						// ֻ�����ֵ�
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

	// ����
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
			// �����ڵ��Ƿ���Ҫ����
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

	// ������� 
	private BTreeNode insertNode(BTreeNode rootNode, int value) {
		
		if(rootNode.isLeaf) {
			// �ҵ��Ļ���ֱ�Ӳ��뼴�ɣ���ΪҶ�ڵ�û���ӽڵ㣬���Բ��ÿ����ӽڵ��ȥ��		
			rootNode.insertkey(value);
		}else {
			// ������ߵ��ӽڵ����˾���Ҫ���з��Ѳ���
			Iterator it = rootNode.keys.iterator();
			boolean notFind = true;
			int insert_index = 0;
			while(it.hasNext() && notFind) {
				int current_key = (int) it.next();
				if(value < current_key) {
					// �ҵ�����λ��
					notFind = false;
					// ����ӽڵ��Ƿ�����
					if(rootNode.b_node.get(insert_index).keys.size() == rootNode.m - 1) {
						rootNode = splitNode(rootNode,rootNode.b_node.get(insert_index),insert_index);
					}
					
					// �����ж����ֵ�Ĵ�С
					if(value > rootNode.keys.get(insert_index)) {
						BTreeNode current_node = rootNode.b_node.get(insert_index + 1);
						current_node = insertNode(current_node,value);
					}else {
						BTreeNode current_node = rootNode.b_node.get(insert_index);
						current_node = insertNode(current_node,value);
					}
					
				}else if(value == current_key) {
					// ��������ͬ��ֵ
					System.out.println("��������ͬ��ֵ");
				}else {
					insert_index++;
				}
			}
			
			// ���뵽���ұ�
			if(notFind) {
				// ����ӽڵ��Ƿ�����
				if(rootNode.b_node.get(insert_index).keys.size() == rootNode.m - 1) {
					rootNode = splitNode(rootNode,rootNode.b_node.get(insert_index),insert_index);
				}
				
				// �����ж����ֵ�Ĵ�С
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

	// ���Ѳ���
	private BTreeNode splitNode(BTreeNode rootNode, BTreeNode childNode, int insert_index) {
		int middle = childNode.m / 2 - 1;
		// ֮ǰ�Ľڵ�����ڵ㣬�¶���Ľڵ����ҽڵ�
		BTreeNode right_leaf = new BTreeNode();
		right_leaf.isLeaf = childNode.isLeaf;
		rootNode.b_node.add(insert_index + 1, right_leaf);
		// ������ҽڵ�
		if(!childNode.isLeaf) right_leaf.b_node.add(0, childNode.b_node.remove(childNode.m-1));
		int key_index = childNode.m - 2;
		while(key_index > middle) {
			right_leaf.keys.add(0, childNode.keys.remove(key_index));
			if(!childNode.isLeaf) right_leaf.b_node.add(0, childNode.b_node.remove(key_index));
			key_index--;
		}
		// ���ڵ�ͷ��ѵĽڵ�ϲ�
		rootNode.keys.add(insert_index, childNode.keys.remove(middle));
		
		return rootNode;
	}
	
	// ��α���
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
