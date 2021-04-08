package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class ShortPath {
	
	class node{
		int distance;
		int path;
		boolean visited;
		int name;
		
		public node(int distance, int path, boolean visited, int name) {
			this.distance = distance;
			this.path = path;
			this.visited = visited;
			this.name = name;
		}
	}

	// ���·���㷨��һ���������㷨��һ����dijkstra������һ����floyd�㷨
	
	// dijkstra�㷨��prim�㷨���ƣ�����Ҳ�в�֮ͬ����
	// ���ͬ����prim�Ǵ��ѷ��ʽڵ㼯�ϳ���������dijkstra�Ǵӵ�ǰ�ڵ����
	// prim����ע�رߣ�dijkstra����ע�ص�
	// ��͵�����prim�����ظ���·��������dijkstra���������ظ��ĳ���
	public List<node> dijkstraPath(int[][] map, int start, int end){
		int len = map.length;
		List<node> node_path = new ArrayList<node>();
		
		// ��ʼ��
		for(int index = 0; index < len; index++) {
			node n = new node(Integer.MAX_VALUE, -1, false, index);
			node_path.add(n);
		}
		
		// ���ȶ���
		PriorityQueue<node> queue_node = new PriorityQueue<node>(new Comparator<node>() {
			public int compare(node n1, node n2) {
				return n1.distance - n2.distance;
			}
		});
		
		node_path.get(start).distance = 0;
		
		queue_node.add(node_path.get(start));
		
		// ֹͣ�����������
		// �������Ľڵ㡣�޷������κ������ڵ��ʱ�򣬸���ô��
		
		// �鿴Ŀ��ڵ��Ƿ񱻷���
		while(!node_path.get(end).visited) {
            node s_node = queue_node.poll();
            while(s_node.visited) {
            	s_node = queue_node.poll();
            }
            start = s_node.name;
            System.out.println(start);
			for(int index = 0; index < len; index++) {
				if(map[start][index] != -1 && start != index && 
						!node_path.get(index).visited) {
					// ���С�����ڵľ��룬��ô����
					// ѡ����С�ľ�����Ϊ��ǰ�ڵ�
					// ���滹��Ҫ�����޸�
					if(map[start][index] + s_node.distance < node_path.get(index).distance) {
						node_path.get(index).distance = map[start][index] + s_node.distance;
						node_path.get(index).path = start;
						queue_node.offer(node_path.get(index));
					}
				}
			}
			s_node.visited = true;
		}
		System.out.println("���·����ȫ��Ȩ�أ�" + node_path.get(end).distance);
		
		return node_path;
	}
	
	// ˼����ǣ����Ǵ�һ���㵽��һ���㣬�޷Ǿ��Ǿ�����������߲��������е㶯̬�滮����˼
	public List<Integer> floydPath(int[][] map, int start, int end){
		List<Integer> path = new ArrayList<Integer>();
		int len = map.length;
		
		// �������������������ݣ�֮�����
		// ����·���м侭���ĵ�
		int[][] middle_point = new int[len][len];
		// ����·��֮��ľ���
		int[][] distance = new int[len][len];
		
		// ���г�ʼ������
		for(int index = 0; index < len; index++) {
			for(int com_index = 0; com_index < len; com_index++) {
				distance[index][com_index] = map[index][com_index] ;
				if(distance[index][com_index] == -1) distance[index][com_index] = Integer.MAX_VALUE;
				middle_point[index][com_index] = -1;
			}
		}
		
		// ����Ҫ�ľ�������ѭ������
		for(int middle = 0; middle < len; middle++) {
			for(int first = 0; first < len; first++) {
				for(int second = 0; second < len; second++) {
					if(distance[first][middle] != Integer.MAX_VALUE && distance[middle][second] != Integer.MAX_VALUE 
							&& distance[first][middle] + distance[middle][second] < distance[first][second]) {
						distance[first][second] = distance[first][middle] + distance[middle][second];
						middle_point[first][second] = middle; 
					}
				}
			}
		}
		System.out.println(distance[start][end]);
		// �õ�·�������ұ�������
		path.add(start);
		getPath(middle_point, start, end, path);
		path.add(end);
		return path;
	}
	
	
	
	public void getPath(int[][] middle_point, int start, int end, List<Integer> path) {
		if(middle_point[start][end] == -1) {
			return ;
		}
		
		int middle = middle_point[start][end];
		getPath(middle_point, start, middle, path);
		path.add(middle);
		getPath(middle_point, middle, end, path);
		
	}


	public static void main(String[] args) {
		int[][] map = {
				{0,5,1,-1,-1,-1},
				{5,0,2, 1,-1,-1},
				{1,2,0, 4, 8,-1},
				{-1,1,4,0,3,6},
				{-1,-1,8,3,0,-1},
				{-1,-1,-1,6,-1,0}
		};
//		int[][] map = {
//				{0,2,2,3},
//				{2,0,-1,2},
//				{2,-1,0,2},
//				{3,2,2,0},
//		};
		
		ShortPath sp = new ShortPath();
		int start = 0;
		int end = 5;
//		List<node> node_path = sp.dijkstraPath(map,start,end);
//		while(node_path.get(end).path != -1) {
//			System.out.print(node_path.get(end).name + " <-- ");
//			end = node_path.get(end).path;
//		}
//		System.out.println(node_path.get(end).name);
		
		List<Integer> node_path = sp.floydPath(map,start,end);
		Iterator<Integer> iter = node_path.iterator();
		while(iter.hasNext()) {
			System.out.print(iter.next() + " --> ");
		}
		
	}

}
