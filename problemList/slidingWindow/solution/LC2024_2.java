package problemList.slidingWindow.solution;

/*
 * 无需转化, 直接做
 */
public class LC2024_2 {
    public int maxConsecutiveAnswers(String answerKey, int k) {
        int n = answerKey.length();
        int t = 0, f = 0;
        int left = 0, maxLen = 0;
        for(int i = 0;i < n;i++){
            if(answerKey.charAt(i) == 'T') t++;
            else f++;
            while(t > k && f > k) {
                if(answerKey.charAt(left) == 'T') t--;
                else f--;
                left++;
            }
            maxLen = Math.max(maxLen, i - left + 1);
        }
        return maxLen;
    }
}
