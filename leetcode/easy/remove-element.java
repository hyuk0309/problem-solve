class Solution {
    public int removeElement(int[] nums, int val) {
      int k = 0; // 유효한 값을 채워넣을 인덱스 (포인터)
		
		  for (int i = 0; i < nums.length; i++) {
			  // 현재 값이 삭제할 값(val)이 아니면
			  if (nums[i] != val) {
				  nums[k] = nums[i]; // k 위치에 현재 값을 덮어쓰기
				  k++; // 다음 위치로 이동
			  }
		  }
		
		  return k; // 유효한 요소의 개수 반환
    }
}
