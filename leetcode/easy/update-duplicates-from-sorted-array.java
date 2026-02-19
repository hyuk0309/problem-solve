class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

		int replaceIdx = 1;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] != nums[replaceIdx - 1]) {
				nums[replaceIdx] = nums[i];
				replaceIdx++;
			}
		}
		return replaceIdx;
    }
}
