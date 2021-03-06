// 두 포인터 이용한 풀이.
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

void getRangeSum(vector<int>& arr, vector<int>& rangeSum) {
	for (int i = 0; i < arr.size(); ++i) {
		int sum = 0;
		for (int j = i; j < arr.size(); ++j) {
			sum += arr[j];
			rangeSum.push_back(sum);
		}
	}
}
long long solve(int& T, int& n, int& m, vector<int>& a, vector<int>& b) {
	vector<int> rangeSumA;
	getRangeSum(a, rangeSumA);

	vector<int> rangeSumB;
	getRangeSum(b, rangeSumB);

	sort(rangeSumA.begin(), rangeSumA.end());
	sort(rangeSumB.begin(), rangeSumB.end(), greater<int>());

	long long answer = 0;
	int aIdx = 0;
	int bIdx = 0;
	int sum = rangeSumA[aIdx] + rangeSumB[bIdx];
	while (true) {
		if (sum > T) {
			if (bIdx == rangeSumB.size() - 1) {
				break;
			}
			sum -= rangeSumB[bIdx];
			bIdx++;
			sum += rangeSumB[bIdx];
		}
		else if (sum == T) {
			long long a = 1;
			long long b = 1;

			while (true) {
				if (aIdx == rangeSumA.size() - 1) {
					break;
				}
				if (rangeSumA[aIdx] == rangeSumA[aIdx + 1]) {
					aIdx++;
					a++;
				}
				else {
					break;
				}
			}
			while (true) {
				if (bIdx == rangeSumB.size() - 1) {
					break;
				}
				if (rangeSumB[bIdx] == rangeSumB[bIdx + 1]) {
					bIdx++;
					b++;
				}
				else {
					break;
				}
			}

			answer += a * b;
			if (bIdx == rangeSumB.size() - 1) {
				break;
			}
			sum -= rangeSumB[bIdx];
			bIdx++;
			sum += rangeSumB[bIdx];
		}
		else {
			if (aIdx == rangeSumA.size() - 1) {
				break;
			}
			sum -= rangeSumA[aIdx];
			aIdx++;
			sum += rangeSumA[aIdx];
		}
	}
	return answer;
}
void input(int& T, int& n, int& m, vector<int>& a, vector<int>& b) {
	cin >> T >> n;
	a.assign(n, 0);
	for (int i = 0; i < n; ++i) {
		cin >> a[i];
	}
	cin >> m;
	b.assign(m, 0);
	for (int i = 0; i < m; ++i) {
		cin >> b[i];
	}
}
int main() {
	int T, n, m;
	vector<int> a;
	vector<int> b;
	input(T, n, m, a, b);

	long long answer = solve(T, n, m, a, b);
	cout << answer;
	return 0;
}

//이분탐색을 이용한 풀이
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int upperBound(vector<int>& arr, int key) {
	int lo = 0;
	int hi = arr.size();
	while (lo + 1 < hi) {
		int mid = lo + (hi - lo) / 2;
		if (arr[mid] <= key) {
			lo = mid;
		}
		else {
			hi = mid;
		}
	}

	if (arr[lo] != key) {
		return -1;
	}
	return lo;
}
int lowerBound(vector<int>& arr, int key) {
	int lo = -1;
	int hi = arr.size() - 1;
	while (lo + 1 < hi) {
		int mid = lo + (hi - lo) / 2;
		if (arr[mid] >= key) {
			hi = mid;
		}
		else {
			lo = mid;
		}
	}

	if (arr[hi] != key) {
		return -1;
	}
	return hi;
}
void getRangeSum(vector<int>& arr, vector<int>& rangeSum) {
	for (int i = 0; i < arr.size(); ++i) {
		int sum = 0;
		for (int j = i; j < arr.size(); ++j) {
			sum += arr[j];
			rangeSum.push_back(sum);
		}
	}
}
long long solve(int& T, int& n, int& m, vector<int>& a, vector<int>& b) {
	vector<int> rangeSumA;
	getRangeSum(a, rangeSumA);

	vector<int> rangeSumB;
	getRangeSum(b, rangeSumB);

	sort(rangeSumA.begin(), rangeSumA.end());

	long long answer = 0;
	for (int i = 0; i < rangeSumB.size(); ++i) {
		long long lo = lowerBound(rangeSumA, T - rangeSumB[i]);
		long long hi = upperBound(rangeSumA, T - rangeSumB[i]);

		if (hi != -1 && lo != -1) {
			answer += (hi - lo + 1);
		}
	}
	return answer;
}
void input(int& T, int& n, int& m, vector<int>& a, vector<int>& b) {
	cin >> T >> n;
	a.assign(n, 0);
	for (int i = 0; i < n; ++i) {
		cin >> a[i];
	}
	cin >> m;
	b.assign(m, 0);
	for (int i = 0; i < m; ++i) {
		cin >> b[i];
	}
}
int main() {
	int T, n, m;
	vector<int> a;
	vector<int> b;
	input(T, n, m, a, b);

	long long answer = solve(T, n, m, a, b);
	cout << answer;
	return 0;
}
