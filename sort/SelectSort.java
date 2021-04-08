package sort;

public class SelectSort {
	
	// 简单选择排序
	// 它与插入排序的区别就是，插入排序操作的是前面的数，选择排序是操作的后面的数
	public void directSelect(int[] nums) {
		for(int index = 0; index < nums.length; index++) {
			// 每一轮都选择出最值元素
			int min_index = index;
			for(int com_index = index + 1; com_index < nums.length; com_index++) {
				if(nums[min_index] > nums[com_index]) {
					min_index = com_index;
				}
			}
			if(min_index != index) {
				int temp = nums[min_index];
				nums[min_index] = nums[index];
				nums[index] = temp;
			}
		}
	}
	
	// 堆排序
	public void heapSort(int[] nums) {
		int len = nums.length;
		// 构建初始堆
		for(int index = len/2 - 1; index >=0; index--) {
			adjustMaxHeap(nums, index, len);
		}
		
		// 有了初始堆之后，每次只要将堆头的元素调整到最后，之后在调整堆头就行，不用再依次循环调整
		for(int index = len - 1; index > 0; index--) {
			nums[0] = nums[0] + nums[index];
			nums[index] = nums[0] - nums[index];
			nums[0] = nums[0] - nums[index];
			adjustMaxHeap(nums, 0, index);
		}
		
	}
	
	public void adjustMaxHeap(int[] nums, int start, int len) {
		int temp = nums[start];
		for(int index = start*2 + 1; index < len; index = index*2 + 1) {
			if(index + 1 < len && nums[index] < nums[index + 1]) index++;
			
			if(nums[start] >= nums[index]) {
				break;
			}else {
				// 这边有的人认为是直接赋值，然后在for的外面再赋值开始的值
				// 我觉得那样不对，你不交换的话，值就没有变化
				// 没有变化，下面也不会调整
				nums[start] = nums[index];
				nums[index] = temp;
				start = index;
			}
			
		}
		
	}

	public static void main(String[] args) {
//		int[] nums = {5,3,1,2,2,3,10};
		int[] nums = {4,6,8,5,9};
		SelectSort cs = new SelectSort();
//        cs.directSelect(nums);
		cs.heapSort(nums);
        for(int num:nums) {
        	System.out.print(num + ",");
        }
	}

}
