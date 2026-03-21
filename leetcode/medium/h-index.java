class Solution {
	public int hIndex(int[] citations) {
		int[] map = new int[citations.length + 1];
		Arrays.fill(map, 0);
		for (int i = 0; i < citations.length; i++) {
			if (citations[i] > map.length - 1) {
				map[map.length - 1]++;
			} else {
				map[citations[i]]++;
			}
		}

		int paperCount = 0;
		for (int i = map.length - 1; i >= 0; i--) {
			paperCount += map[i];
			if (paperCount >= i) {
				return i;
			}
		}
		return 0;
	}
}
