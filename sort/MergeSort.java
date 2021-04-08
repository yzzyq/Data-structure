package sort;

public class MergeSort {
	// 归并排序
	// 就是不断地合并数组
	public void mergeSortNum(int[] nums) {
		
		mergeSortRes(nums, 0, nums.length-1);
	}
 
	public void mergeSortRes(int[] nums, int start, int end) {
		if(start < end) {
			int middle = (start + end) / 2;
			// 进行递归合并
			mergeSortRes(nums, start, middle);
			mergeSortRes(nums, middle + 1, end);
			mergeNum(nums, start, middle, end);
		}
	}

	// 合并俩个数组
	public void mergeNum(int[] nums, int start, int middle, int end) {
		int[] temp = new int[end - start + 1];
		int first_index = start, second_index = middle + 1, index= 0;
		for(; first_index <= middle && second_index <= end; index++) {
			if(nums[first_index] >= nums[second_index]) {
				temp[index] = nums[second_index++];
			}else {
				temp[index] = nums[first_index++];
			}
		}
		while(first_index <= middle) temp[index++] = nums[first_index++];
		while(second_index <= end) temp[index++] = nums[second_index++];
		
		for(int one:temp) {
			nums[start++] = one;
		}
		
	}


	public static void main(String[] args) {
		int[] nums = {5,3,1,2,2,3,10};
		MergeSort cs = new MergeSort();
		cs.mergeSortNum(nums);
        for(int num:nums) {
        	System.out.print(num + ",");
        } 
	}

}
