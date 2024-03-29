/* dfs 이용한 완탐 */
class Solution {
    
    int[] apeach;
    int[] answer = new int[11];
    int maxScoreDiff = 0;
    
    public int[] solution(int n, int[] info) {
        
        apeach = info;
        game(n, 0, new int[11]);
        
        if (maxScoreDiff == 0) {
            return new int[]{-1};
        }
        return answer;
    }
    
    private void game(int arrowCnt, int depth, int[] lion) {
        if (depth == 11) {
            lion[depth - 1] += arrowCnt;
            
            int scoreDiff = getScoreDiff(lion);
            
            if (isNewAnswer(lion, scoreDiff)) {
                updateAnswer(lion, scoreDiff);
            }
            lion[depth - 1] -= arrowCnt;
            return;
        }
        
        /* 라이언은 두 가지 경우를 선택할 수 있다. 1. 점수 획득 o 2. 점수 획득 x */
        
        if (apeach[depth] < arrowCnt) {
            lion[depth] = apeach[depth] + 1;
            game(arrowCnt - lion[depth], depth + 1, lion);
            lion[depth] = 0;
        }
        
        game(arrowCnt, depth + 1, lion);
    }
    
    private int getScoreDiff(int[] lion) {
        int scoreDiff = 0;
        for (int i = 0; i < lion.length; ++i) {
            if (apeach[i] < lion[i]) {
                scoreDiff += 10 - i;
            } else {
                if (apeach[i] > 0) {
                    scoreDiff -= 10 - i;
                }
            }
        }
        return scoreDiff;
    }
    
    private boolean isNewAnswer(int[] lion, int scoreDiff) {
        if (scoreDiff > 0) {
            if (scoreDiff > maxScoreDiff) {
                return true;
            } else if (scoreDiff == maxScoreDiff) {
                for (int i = 10; i >= 0; --i) {
                    if (lion[i] > answer[i]) {
                        return true;
                    }
                    if (lion[i] < answer[i]) {
                        return false;
                    }
                }
            }
        }
        return false;
    }
    
    private void updateAnswer(int[] lion, int scoreDiff) {
        maxScoreDiff = scoreDiff;
        for (int i = 0; i < lion.length; ++i) {
            answer[i] = lion[i];
        }
    }
}

/* 중복 조합 풀이 */
class Solution {
    
    private int[] apeach;
    private int[] lion;
    private int[] answer;
    private int maxScoreDiff = 0;
    
    public int[] solution(int n, int[] info) {
        
        apeach = info.clone();
        lion = new int[11];
        answer = new int[11];
        
        duplicateCombi(n, 0, 0);
        
        if (maxScoreDiff == 0) {
            return new int[]{-1};
        }
        return answer;
    }
    
    private void duplicateCombi(int n, int start, int depth) {
        if (depth == n) {
            updateAnswer();
            return;
        }
        
        for (int i = start; i < 11; ++i) {
            lion[i]++;
            duplicateCombi(n, i, depth + 1);
            lion[i]--;
        }
    }
    
    private void updateAnswer() {
        int score = getScore();
        
        boolean isNewAnswer = false;
        if (score > maxScoreDiff) {
            isNewAnswer = true;
        } else if (score == maxScoreDiff) {
            for (int i = 10; i >= 0; --i) {
                if (lion[i] > answer[i]) {
                    isNewAnswer = true;
                    break;
                } else if (lion[i] < answer[i]) {
                    isNewAnswer = false;
                    break;
                }
            }
        }
        
        if (isNewAnswer) {
            maxScoreDiff = score;
            answer = lion.clone();
        }
    }
    
    private int getScore() {
        int score = 0;
        for (int i = 0; i < 11; ++i) {
            if (lion[i] > apeach[i]) {
                score += 10 - i;
            } else {
                if (apeach[i] == 0) {
                    continue;
                }
                score -= 10 - i;
            }
        }
        return score;
    }
}

/* 직관적인 풀이 */
import java.util.*;

class Solution {
    
    private List<List<Integer>> scoreCombies = new ArrayList<>();
    
    public int[] solution(int n, int[] info) {
        
        //라이언이 얻을 수 있는 모든 점수 조합 구하기
        for (int i = 1; i <= n; ++i) {
            List<Integer> combie = new ArrayList<>();
            getCombi(combie, 0, i);
        }
        
        //정답 구하기
        int maxDiff = 0;
        boolean isAnswer = false;
        int[] answer = new int[11];
        for (List<Integer> combi : scoreCombies) {
                
            //만들 수 있는 점수 조합인지 확인하기
            if (!isPossibleCombie(combi, info, n)) {
                continue;
            }
            
            int[] lionInfo = throwArrow(combi, info, n);
            
            //점수 차 구하기
            int scoreDiff = getScoreDiff(info, lionInfo);
            
            if (maxDiff < scoreDiff) {
                maxDiff = scoreDiff;
                
                isAnswer = true;
                for (int i = 0; i < 11; ++i) {
                    answer[i] = lionInfo[i];
                }
            } else if (maxDiff == scoreDiff) {
                if (isNewAnswer(answer, lionInfo)) {
                    for (int i = 0; i < 11; ++i) {
                        answer[i] = lionInfo[i];
                    }
                }
            }
        }
        
        if (!isAnswer) {
            return new int[]{-1};
        }
        return answer;
    }
    
    private boolean isPossibleCombie(List<Integer> combie, int[] info, int n) {
        int arrowCnt = 0;
        
        for (Integer score : combie) {
            arrowCnt += info[10 - score] + 1;
        }
        
        return arrowCnt <= n;
    }
    
    private int[] throwArrow(List<Integer> combie, int[] info, int n) {
        
        int[] lionInfo = new int[info.length];
        for (Integer score : combie) {
            int apeachArrowCnt = info[10 - score];
            lionInfo[10 - score] = apeachArrowCnt + 1;
            n -= apeachArrowCnt + 1;
        }
        
        //남은 화살은 가장 낮은 점수 쏘기
        lionInfo[10] += n;
        
        return lionInfo;
    }
    
    private int getScoreDiff(int[] apeach, int[] lion) {
        int lionScore = 0;
        int apeachScore = 0;
        for (int i = 0; i < 11; ++i) {
            if (apeach[i] == 0 && lion[i] == 0) {
                continue;
            }
                
            if (apeach[i] >= lion[i]) {
                apeachScore += 10 - i;
            } else {
                lionScore += 10 - i;
            }
        }
        return lionScore - apeachScore;
    }
    
    private boolean isNewAnswer(int[] answer, int[] lionInfo) {
        for (int i = 10; i >= 0; --i) {
            if (answer[i] < lionInfo[i]) {
                return true;
            }
        }
        return false;
    }
    
    private void getCombi(List<Integer> combie, int idx, int n) {
        if (combie.size() == n) {
            scoreCombies.add(new ArrayList<>(combie));
            return;
        }
        
        for (int i = idx; i <= 10; ++i) {
            combie.add(10 - i);
            getCombi(combie, i + 1, n);
            combie.remove(combie.size() - 1);
        }
    }
}
