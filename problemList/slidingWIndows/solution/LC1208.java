package problemList.slidingWIndows.solution;

public class LC1208 {
    public int equalSubstring(String s, String t, int maxCost) {
        int n = s.length(), left = 0, ret = 0, curCost = 0;
        for(int i = 0;i < n;i++){
            curCost += (int)Math.abs(s.charAt(i) - t.charAt(i));
            while(curCost > maxCost){
                curCost -= (int)Math.abs(s.charAt(left) - t.charAt(left));
                left++;
            }
            ret = Math.max(ret, i - left + 1);
        }
        return ret;
    }
}
