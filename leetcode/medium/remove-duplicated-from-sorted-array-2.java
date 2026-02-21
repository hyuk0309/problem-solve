class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) return nums.length;

		int replaceIdx = 2;
		for (int i = 2; i < nums.length; i++) {
			if (nums[i] != nums[replaceIdx - 2]) {
				nums[replaceIdx] = nums[i];
				replaceIdx++;
			}
		}
		return replaceIdx;
    }
}
