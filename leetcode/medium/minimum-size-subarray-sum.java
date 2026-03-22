class Solution {
	public int minSubArrayLen(int target, int[] nums) {
		int left = 0;
		int right = 1;
		int subArraySum = nums[0];
		int minSubArrayLen = Integer.MAX_VALUE;
		while (right <= nums.length) {
			if (subArraySum >= target) {
				minSubArrayLen = Math.min(minSubArrayLen, (right - left));
			}

			if (subArraySum > target) {
				subArraySum -= nums[left];
				left++;
			} else {
				if (right == nums.length) {
					break;
				}
				subArraySum += nums[right];
				right++;
			}
		}
		return minSubArrayLen == Integer.MAX_VALUE ? 0 : minSubArrayLen;
	}
}
