package problemList.slidingWindow.solution;

/**
转化为 max(T中最多连续k个F的子数组长度, F中最多连续k个T的子数组长度)
 */
public class LC2024_1 {
    public int maxConsecutiveAnswers(String answerKey, int k) {
        return Math.max(sliding('T', answerKey, k), sliding('F', answerKey, k));
    }

    private int sliding(char ch, String answerKey, int k){
        int n = answerKey.length(), ret = 0, left = 0, cnt = 0;
        for(int i = 0;i < n;i++){
            if(answerKey.charAt(i) != ch) cnt++;
            while(cnt > k){
                if(answerKey.charAt(left) != ch) cnt--;
                left++;
            }
            ret = Math.max(ret, i - left + 1);
        }
        return ret;
    }
}
