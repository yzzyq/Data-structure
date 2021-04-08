package tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//
public class BTreeNode {
	// ���Ľף�Ҳ�������м����ӽڵ�
	int m = 4;
	List<Integer> keys;
    List<BTreeNode> b_node;
    boolean isLeaf;
    
    public BTreeNode() {
    	keys = new ArrayList<Integer>(m-1);
    	b_node = new ArrayList<BTreeNode>(m);
    	isLeaf = true;
    }

	public void insertkey(int value) {
		int pos = searchInsertPos(value);
		keys.add(pos, value);
	}
	
	public int searchInsertPos(int value) {
		Iterator it = keys.iterator();
		int pos = 0;
		while(it.hasNext()) {
			if(value < (int)it.next()) {
				break;
			}
			pos++;
		}
		return pos;
	}

	public int containKey(int value) {
		Iterator it = keys.iterator();
		int pos = 0;
		while(it.hasNext()) {
			if(value == (int)it.next()) {
				return pos;
			}
			pos++;
		}
		return -1;
	}
}
