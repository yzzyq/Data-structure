package graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class MinGenerateTree {
	
	// 边
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
	
	// 最小数生成一共有俩种算法，第一种就是prim算法，第二种就是kruskal克鲁斯卡尔算法
	// 俩种都是贪心算法，不过一个全部贪心来看，一个局部集合贪心
	
	// prim算法，其中用优先队列来存储最重要的边关系
	public List<edges> primAlgorithm(int[][] map) {
		int map_size = map.length;
		// 需要一个数组表示是否已经加入到已访问集合中
		boolean[] visited_vector = new boolean[map_size];
		Arrays.fill(visited_vector, false);
		
		// 表示已访问集合到未访问集合中的路径
		// 这里是优先队列，也要设置比较方法
		PriorityQueue<edges> edge_queue = new PriorityQueue<edges>(new Comparator<edges>() {
			public int compare(edges e1, edges e2) {
				return e1.weight - e2.weight;
			}
		});
		
		// 输出的路径顺序
		List<edges> edge_list = new LinkedList<edges>();
		
		int start = 0;
		
		// 访问节点，并且将之全部的边都加入到优先队列中
		for(int index = 0; index < map_size - 1; index++) {
			visited_vector[start] = true;
			for(int col = 0; col < map_size; col++) {
				// 将路径加入到优先队列
				if(map[start][col] != -1 && map[start][col] != 0) {
					edges e = new edges(map[start][col], start, col);
					edge_queue.offer(e);
				}
			}
			// 拿出最小的路径，并且还是未访问过的点
			edges min_weight = edge_queue.poll();
			while(visited_vector[min_weight.point2]) min_weight = edge_queue.poll();
			
			edge_list.add(min_weight);
			start = min_weight.point2;
		}
		
		return edge_list;
	}
	
	
	// 克鲁斯卡尔算法，它与普利姆算法最大的不同就是它从全部边来看的
	// 它需要并查集来看是否形成了环
	public List<edges> kruskalAlgorithm(int[][] map){
		int map_len = map.length;
		// 输出的路径顺序
	    List<edges> edge_list = new LinkedList<edges>();
	    
	    PriorityQueue<edges> edge_queue = new PriorityQueue<edges>(new Comparator<edges>(){
	    	public int compare(edges e1, edges e2) {
	    		return e1.weight - e2.weight;
	    	}
	    });
	    
	    // 并查集，表示它所在集合中，代表节点是哪个节点,开始默认的代表都是自身
	    Map<Integer, Integer> node_map = new HashMap<Integer, Integer>();
	    
	    // 将图中所有的边都提取出来，并且初始化并查集
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
	    	// 检查这条边加入是否会形成环路
	    	while(getPoint(node_map,e.point1) == getPoint(node_map,e.point2)) {
	    		e = edge_queue.poll();
	    	}
	    	// 如果不形成环路，那么就合并它们,其实就是改变并查集
	    	edge_list.add(e);
	    	// 这里的更新要注意是更新代表节点
	    	node_map.put(getPoint(node_map,e.point2), getPoint(node_map, e.point1));
	    }
	    return edge_list;
	}
	
	// 得到节点所在集合的代表节点
	public int getPoint(Map<Integer, Integer> node_map, int point) {
		int key = point;
		while(node_map.get(key) != -1) {
			key = node_map.get(key);
		}
		return key;
	}
	

	public static void main(String[] args) {
		// 图的邻接矩阵，-1表示不可达
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
