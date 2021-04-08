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

	// 最短路径算法，一共有俩个算法，一个是dijkstra，还有一个是floyd算法
	
	// dijkstra算法与prim算法类似，但是也有不同之处，
	// 最大不同就是prim是从已访问节点集合出发，但是dijkstra是从当前节点出发
	// prim更加注重边，dijkstra更加注重点
	// 这就导致了prim可以重复点路径，但是dijkstra不可能有重复的出现
	public List<node> dijkstraPath(int[][] map, int start, int end){
		int len = map.length;
		List<node> node_path = new ArrayList<node>();
		
		// 初始化
		for(int index = 0; index < len; index++) {
			node n = new node(Integer.MAX_VALUE, -1, false, index);
			node_path.add(n);
		}
		
		// 优先队列
		PriorityQueue<node> queue_node = new PriorityQueue<node>(new Comparator<node>() {
			public int compare(node n1, node n2) {
				return n1.distance - n2.distance;
			}
		});
		
		node_path.get(start).distance = 0;
		
		queue_node.add(node_path.get(start));
		
		// 停止条件如何设置
		// 如果到达的节点。无法到达任何其他节点的时候，该怎么办
		
		// 查看目标节点是否被访问
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
					// 如果小于现在的距离，那么更新
					// 选出最小的距离作为当前节点
					// 后面还需要更新修改
					if(map[start][index] + s_node.distance < node_path.get(index).distance) {
						node_path.get(index).distance = map[start][index] + s_node.distance;
						node_path.get(index).path = start;
						queue_node.offer(node_path.get(index));
					}
				}
			}
			s_node.visited = true;
		}
		System.out.println("最短路径的全部权重：" + node_path.get(end).distance);
		
		return node_path;
	}
	
	// 思想就是，我们从一个点到另一个点，无非就是经过其他点或者不经过，有点动态规划的意思
	public List<Integer> floydPath(int[][] map, int start, int end){
		List<Integer> path = new ArrayList<Integer>();
		int len = map.length;
		
		// 这俩个是用来保存数据，之后输出
		// 所有路径中间经过的点
		int[][] middle_point = new int[len][len];
		// 所有路径之间的距离
		int[][] distance = new int[len][len];
		
		// 进行初始化处理
		for(int index = 0; index < len; index++) {
			for(int com_index = 0; com_index < len; com_index++) {
				distance[index][com_index] = map[index][com_index] ;
				if(distance[index][com_index] == -1) distance[index][com_index] = Integer.MAX_VALUE;
				middle_point[index][com_index] = -1;
			}
		}
		
		// 最主要的就是三个循环机制
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
		// 得到路径，并且保存下来
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
