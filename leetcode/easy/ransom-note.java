class Solution {
	public boolean canConstruct(String ransomNote, String magazine) {
		int[] charMap = new int[26];
		for (int i = 0; i < magazine.length(); i++) {
			charMap[magazine.charAt(i) - 'a']++;
		}

		for (int i = 0; i < ransomNote.length(); i++) {
			charMap[ransomNote.charAt(i) - 'a']--;
			if (charMap[ransomNote.charAt(i) - 'a'] < 0) {
				return false;
			}
		}
		return true;
	}
}
