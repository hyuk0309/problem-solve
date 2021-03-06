#include <iostream>
#include <vector>
#include <queue>
using namespace std;
struct cmp {
	bool operator () (const pair<int, int>& a, const pair<int, int>& b) {
		return a.second > b.second;
	}
};

void updateIndexTree(vector<int>& indexTree, int p) {
	p += (indexTree.size() / 2);
	indexTree[p]++;
	while (p > 1) {
		p = p / 2;
		indexTree[p] = indexTree[p * 2] + indexTree[p * 2 + 1];
	}
}
int rangeSum(vector<int>& indexTree, int right) {
	int left = indexTree.size() / 2;
	right += (indexTree.size() / 2);
	int sum = 0;
	while (left <= right) {
		if (left % 2 == 1) {
			sum += indexTree[left];
		}
		if (right % 2 == 0) {
			sum += indexTree[right];
		}
		left = (left + 1) / 2;
		right = (right - 1) / 2;
	}
	return sum;
}
void makeIndexTree(vector<int>& indexTree, int N) {
	int startIdx = 1;
	while (startIdx < N) {
		startIdx *= 2;
	}
	indexTree.assign(startIdx * 2, 0);
}
long long solve(int N, vector<int>& A, vector<int>& B) {
	//mapping 관계 구하기.
	priority_queue<pair<int, int>, vector<pair<int, int>>, cmp> pqA;
	priority_queue<pair<int, int>, vector<pair<int, int>>, cmp> pqB;

	for (int i = 0; i < N; ++i) {
		pqA.push(make_pair(i, A[i]));
		pqB.push(make_pair(i, B[i]));
	}

	vector<int> mappingInfo(N, 0); // mappingInfo[i] = j는 B의 i번째 기계가 A의 j와 mapping
	int Aidx = 0;
	int Bidx = 0;
	while (!pqA.empty() && !pqB.empty()) {
		Aidx = pqA.top().first;
		Bidx = pqB.top().first;
		pqA.pop();
		pqB.pop();

		mappingInfo[Bidx] = Aidx;
	}

	//indexTree 이용해 구간 합 구하기
	vector<int> indexTree;
	makeIndexTree(indexTree, N);

	long long totalCnt = 0;
	for (int i = N - 1; i >= 0; --i) {
		Aidx = mappingInfo[i];
		totalCnt += rangeSum(indexTree, Aidx);
		updateIndexTree(indexTree, Aidx);
	}
	return totalCnt;
}
void input(int& N, vector<int>& A, vector<int>& B) {
	cin >> N;
	A.assign(N, 0);
	B.assign(N, 0);
	for (int i = 0; i < N; ++i) {
		cin >> A[i];
	}
	for (int i = 0; i < N; ++i) {
		cin >> B[i];
	}
}
int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);

	int N;
	vector<int> A;
	vector<int> B;
	input(N, A, B);

	long long answer = solve(N, A, B);

	cout << answer;
	return 0;
}

// version 2
#include <iostream>
#include <vector>
#include <unordered_map>
using namespace std;

void updateIDT(vector<int>& IDT, int idx) {
    idx += (IDT.size() / 2);
    IDT[idx]++;
    while(idx > 1) {
        idx /= 2;
        IDT[idx] = IDT[idx * 2] + IDT[idx * 2 + 1];
    }
}
int getRangeSum(vector<int>& IDT, int right) {
    int left = IDT.size() / 2;
    right += (IDT.size() / 2);
    int sum = 0;
    while(left <= right) {
        if(left % 2 == 1) {
            sum += IDT[left];
        }
        if(right % 2 == 0) {
            sum += IDT[right];
        }

        left = (left + 1) / 2;
        right = (right - 1) / 2;
    }
    return sum;
}
void makeIDT(vector<int>& IDT, int N) {
    int leafSize = 1;
    while(leafSize < N) {
        leafSize *= 2;
    }

    IDT.assign(leafSize * 2, 0);
}
void getMappingInfo(int N, vector<int>& A, vector<int>& B, vector<int>& map) {
    unordered_map<int, int> hashMap;
    for(int i = 0; i < N; ++i) {
        hashMap[B[i]] = i;
    }

    for(int i = 0; i < N; ++i) {
        map[i] = hashMap[A[i]];
    }
}
long long solve(int N, vector<int>& A, vector<int>& B) {
    //mapping정보 구하기
    vector<int> map(N, 0);
    getMappingInfo(N, A, B, map);

    vector<int> IDT;
    makeIDT(IDT, N);

    long long ans = 0;
    for(int i = N - 1; i >= 0; --i) {
        int idx = map[i];
        ans += getRangeSum(IDT, idx);
        updateIDT(IDT, idx);
    }
    return ans;
}
void input(int& N, vector<int>& A, vector<int>& B) {
    cin >> N;
    A.assign(N, 0);
    B.assign(N, 0);
    for(int i = 0; i < N; ++i) {
        cin >> A[i];
    }
    for(int i = 0; i < N; ++i) {
        cin >> B[i];
    }
}
int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int N;
    vector<int> A, B;
    input(N, A, B);

    cout << solve(N, A, B);
    return 0;
}
