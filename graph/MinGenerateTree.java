package graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class MinGenerateTree {
	
	// ��
	class edges{
		int weight;
		int point1;
		int point2;
		
		public edges(int weight, int point1, int point2) {
			this.weight = weight;
			this.point1 = point1;
			this.point2 = point2;
		}
	}
	
	// ��С������һ���������㷨����һ�־���prim�㷨���ڶ��־���kruskal��³˹�����㷨
	// ���ֶ���̰���㷨������һ��ȫ��̰��������һ���ֲ�����̰��
	
	// prim�㷨�����������ȶ������洢����Ҫ�ı߹�ϵ
	public List<edges> primAlgorithm(int[][] map) {
		int map_size = map.length;
		// ��Ҫһ�������ʾ�Ƿ��Ѿ����뵽�ѷ��ʼ�����
		boolean[] visited_vector = new boolean[map_size];
		Arrays.fill(visited_vector, false);
		
		// ��ʾ�ѷ��ʼ��ϵ�δ���ʼ����е�·��
		// ���������ȶ��У�ҲҪ���ñȽϷ���
		PriorityQueue<edges> edge_queue = new PriorityQueue<edges>(new Comparator<edges>() {
			public int compare(edges e1, edges e2) {
				return e1.weight - e2.weight;
			}
		});
		
		// �����·��˳��
		List<edges> edge_list = new LinkedList<edges>();
		
		int start = 0;
		
		// ���ʽڵ㣬���ҽ�֮ȫ���ı߶����뵽���ȶ�����
		for(int index = 0; index < map_size - 1; index++) {
			visited_vector[start] = true;
			for(int col = 0; col < map_size; col++) {
				// ��·�����뵽���ȶ���
				if(map[start][col] != -1 && map[start][col] != 0) {
					edges e = new edges(map[start][col], start, col);
					edge_queue.offer(e);
				}
			}
			// �ó���С��·�������һ���δ���ʹ��ĵ�
			edges min_weight = edge_queue.poll();
			while(visited_vector[min_weight.point2]) min_weight = edge_queue.poll();
			
			edge_list.add(min_weight);
			start = min_weight.point2;
		}
		
		return edge_list;
	}
	
	
	// ��³˹�����㷨����������ķ�㷨���Ĳ�ͬ��������ȫ����������
	// ����Ҫ���鼯�����Ƿ��γ��˻�
	public List<edges> kruskalAlgorithm(int[][] map){
		int map_len = map.length;
		// �����·��˳��
	    List<edges> edge_list = new LinkedList<edges>();
	    
	    PriorityQueue<edges> edge_queue = new PriorityQueue<edges>(new Comparator<edges>(){
	    	public int compare(edges e1, edges e2) {
	    		return e1.weight - e2.weight;
	    	}
	    });
	    
	    // ���鼯����ʾ�����ڼ����У�����ڵ����ĸ��ڵ�,��ʼĬ�ϵĴ���������
	    Map<Integer, Integer> node_map = new HashMap<Integer, Integer>();
	    
	    // ��ͼ�����еı߶���ȡ���������ҳ�ʼ�����鼯
	    for(int first_index = 0; first_index < map_len - 1; first_index++) {
	    	for(int second_index = first_index + 1; second_index < map_len; second_index++) {
	    		if(map[first_index][second_index] != -1) {
	    			edges e = new edges(map[first_index][second_index], first_index, second_index);
	    			edge_queue.offer(e);
	    		}
	    	}
	    	node_map.put(first_index, -1);
	    }
	    node_map.put(map_len - 1, -1);
	    
	    while(edge_list.size() < map_len - 1) {
	    	edges e = edge_queue.poll();
	    	// ��������߼����Ƿ���γɻ�·
	    	while(getPoint(node_map,e.point1) == getPoint(node_map,e.point2)) {
	    		e = edge_queue.poll();
	    	}
	    	// ������γɻ�·����ô�ͺϲ�����,��ʵ���Ǹı䲢�鼯
	    	edge_list.add(e);
	    	// ����ĸ���Ҫע���Ǹ��´���ڵ�
	    	node_map.put(getPoint(node_map,e.point2), getPoint(node_map, e.point1));
	    }
	    return edge_list;
	}
	
	// �õ��ڵ����ڼ��ϵĴ���ڵ�
	public int getPoint(Map<Integer, Integer> node_map, int point) {
		int key = point;
		while(node_map.get(key) != -1) {
			key = node_map.get(key);
		}
		return key;
	}
	

	public static void main(String[] args) {
		// ͼ���ڽӾ���-1��ʾ���ɴ�
//		int[][] map = {
//				{0,5,1,-1,-1,-1},
//				{5,0,2, 1,-1,-1},
//				{1,2,0, 4, 8,-1},
//				{-1,1,4,0,3,6},
//				{-1,-1,8,3,0,-1},
//				{-1,-1,-1,6,-1,0}
//		};
		int[][] map = {
				{0,2,2,3},
				{2,0,-1,2},
				{2,-1,0,2},
				{3,2,2,0},
		};
		
		MinGenerateTree mgt = new MinGenerateTree();
		List<edges> edge_list = mgt.primAlgorithm(map);
//		List<edges> edge_list = mgt.kruskalAlgorithm(map);
		for(edges e:edge_list) {
			System.out.println(e.point1 + "-->" + e.point2 + ":" + e.weight);
		}

	}

}
