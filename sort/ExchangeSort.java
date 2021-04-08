package sort;

import java.util.Arrays;

public class ExchangeSort {

	// 冒泡排序
	// 比较相邻元素，若为逆序，俩俩交换
	public void BubbleSort(int[] nums) {
		for(int index = nums.length - 1; index > 0; index--) {
			// 查看是否发生了交换
			boolean flag = false;
			for(int com_index = 0; com_index < index; com_index++) {
				if(nums[com_index] > nums[com_index + 1]) {
					int temp = nums[com_index];
					nums[com_index] = nums[com_index + 1];
					nums[com_index + 1] = temp;
					flag = true;
				}
			}
			if(!flag) return;
		}
	}
	
	
	// 快速排序
	// 需要考虑的，第一就是基准如何选择，第二就是基准值如何排除在外
	public void QuickSort(int[] nums) {
		int low = 0;
		int high = nums.length - 1;
		QuickRes(nums, low, high);
//		QuickResComm(nums, low, high);
	}
	
	// 一般版本的快速排序
	public void QuickResComm(int[] nums, int low, int high) {
		// 基准放在最左边，那么遍历就从右边开始
		int left = low, right = high, pri = nums[left];
		while(left < right) {
			while(left < right && nums[right] > pri) right--;
			nums[left] = nums[right];
			while(left < right && nums[left] < pri) left++;
			nums[right] = nums[left];
		}
		nums[left] = pri;
		QuickRes(nums, low, left - 1);
		QuickRes(nums, left + 1, high);
	}
	
	// 三值选择的快速排序
	public void QuickRes(int[] nums, int low, int high) {
		if(low < high) {
			// 基准是三值选择中间的
			int middle = (low + high) / 2;
			if(nums[low] > nums[middle]) swap(nums, low, middle);
			if(nums[low] > nums[high]) swap(nums, low, high);
			if(nums[middle] > nums[high]) swap(nums, middle, high);
			
			int partion = nums[middle];
			// 基准放在最右边，那么遍历的时候，就需要先从左边开始，
			swap(nums, middle, high - 1);
			int left = low, right = high - 1;
			while(left < right) {
				while(nums[++left] < partion) ;
				while(nums[--right] > partion) ;
				if(left < right) {
					swap(nums,left,right);
				}else {
					break;
				}
			}
			swap(nums, left, high - 1);
			QuickRes(nums, low, left - 1);
			QuickRes(nums, left + 1, high);
		}
	}

    // 交换位置
	private void swap(int[] nums, int first, int second) {
		int temp = nums[first];
		nums[first] = nums[second];
		nums[second] = temp;		
	}


	// 交换排序
	public static void main(String[] args) {
		int[] nums = {5,3,1,2,2,3,10};
		ExchangeSort is = new ExchangeSort();
//        is.BubbleSort(nums);
        is.QuickSort(nums);
        for(int num:nums) {
        	System.out.print(num + ",");
        }
	}
}
