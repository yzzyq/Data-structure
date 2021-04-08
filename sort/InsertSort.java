package sort;

public class InsertSort {
	// 整体都是从小到大排序
	
	// 直接插入排序
	// 1. 找出要插入的位置
	// 2. 之后所有的元素都要后移
	// 3. 赋值
	public void DirectInsertSort(int[] nums) {
		for(int index = 1; index < nums.length; index++) {
			if(nums[index] < nums[index - 1]) {
				int temp = nums[index];
				int pos_index = index - 1;
				while(pos_index >= 0 && temp < nums[pos_index]) {
					nums[pos_index + 1] = nums[pos_index];
					pos_index--;
				}
				nums[pos_index + 1] = temp;
			}
		}
	}
	
	
	
	// 折半插入排序，主要差异就是查找插入位置使用的是二分查找
	public void SplitInsertSort(int[] nums) {
		for(int index = 1; index < nums.length; index++) {
			if(nums[index] < nums[index - 1]) {
				// 使用二分法查找插入位置
				int temp = nums[index];
				int left = 0;
				int right = index - 1;
				// 找出要插入的位置
				while(left <= right) {
					int middle = (left + right) / 2;
					if(temp > nums[middle]) {
						left = middle + 1;
					}else {
						right = middle - 1;
					}
				}
				// 数据进行后移
				for(int temp_index  = index - 1; temp_index >= right + 1; temp_index--) {
					nums[temp_index + 1] = nums[temp_index];
				}
				nums[right + 1] = temp;
			}
			
		}
	}
	
	// 希尔排序，主要差异就是缩小增量，适合比较大的数据集
	// 对于大的数据集，会先分成小的，比较这些小的，之后进行合并成大的。
	// 对于分成小的，我们并不会真的开辟内存给它，真正的实现是隔空对比即可,这样就能减少空间复杂度
	public void ShellSort(int[] nums) {
		int len = nums.length;
		for(int step = len / 2; step > 0; step /= 2) {
			for(int index = step; index < len; index++) {
				if(nums[index] < nums[index - step]) {
					// 每个分组都使用直接插入排序
					int temp = nums[index];
					int pos_index = index - step;
					while(pos_index >= 0 && temp < nums[pos_index]) {
						nums[pos_index + step] = nums[pos_index];
						pos_index -= step;
					}
					nums[pos_index + step]= temp;
				}
			}
			
		}
	}
	

	public static void main(String[] args) {
		int[] nums = {5,3,1,2,2,100,5};
		InsertSort is = new InsertSort();
//        is.DirectInsertSort(nums);
        is.SplitInsertSort(nums);
        for(int num:nums) {
        	System.out.print(num + ",");
        }
	}

}
