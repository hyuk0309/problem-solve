class Solution {
	public boolean isValidSudoku(char[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != '.') {
					if (!isValid(board, i, j)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	boolean isValid(char[][] board, int curX, int curY) {
		for (int i = 0; i < board.length; i++) {
			if (!(i == curX)) {
				if (board[i][curY] == board[curX][curY]) {
					return false;
				}
			}
		}

		for (int i = 0; i < board[curX].length; i++) {
			if (!(i == curY)) {
				if (board[curX][i] == board[curX][curY]) {
					return false;
				}
			}
		}

		int startX = (curX / 3) * 3;
		int startY = (curY / 3) * 3;
		for (int i = startX; i < startX + 3; i++) {
			for (int j = startY; j < startY + 3; j++) {
				if (!(i == curX && j == curY)) {
					if (board[i][j] == board[curX][curY]) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
