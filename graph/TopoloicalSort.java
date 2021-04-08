package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TopoloicalSort {
	
	// ����������Ҫ������������ϵ�����񣬲���ͼ�����������޻�ͼ����������Ż�����
	public List<Integer> topoloSort(int[][] map){
		int len = map.length;
		List<Integer> sort_seq = new ArrayList<Integer>();
		Deque<Integer> queue_zero = new LinkedList<Integer>();
		boolean[] visited = new boolean[len];
		Arrays.fill(visited, false);
		// ͳ��������ȵ�
		int[] in = new int[len];
		for(int col = 0; col < len; col++) {
			for(int line = 0; line < len; line++) {
				in[col] += map[line][col];
			}
			// �����Ϊ0�Ľ������
			if(in[col] == 0) {
				queue_zero.offer(col);
				visited[col] = true;
			}
		}
		
		while(!queue_zero.isEmpty()) {
			int index = queue_zero.poll();
			for(int col = 0; col < len; col++) {
				in[col] -= map[index][col];
				if(in[col] == 0 && !visited[col]) {
					queue_zero.offer(col);
					visited[col] = true;
				}
			}
			sort_seq.add(index);
		}
		
		return sort_seq;
	}
	

	public static void main(String[] args) {
		int[][] map = {
				{0,1,1,1,0,0},
				{0,0,0,0,0,0},
				{0,1,0,0,1,0},
				{0,0,0,0,1,0},
				{0,0,0,0,0,0},
				{0,0,0,1,1,0}
		};

		TopoloicalSort ts = new TopoloicalSort();
		List<Integer> sort_seq = ts.topoloSort(map);
		Iterator<Integer> iter = sort_seq.iterator();
		while(iter.hasNext()) {
			System.out.print(iter.next() + " --> ");
		}
	}

}
