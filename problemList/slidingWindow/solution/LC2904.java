package problemList.slidingWindow.solution;

public class LC2904 {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length(), left = 0, ret = Integer.MAX_VALUE, cnt = 0;
        String ans = "";
        for(int i = 0;i < n;i++){
            if(s.charAt(i) == '1') cnt++;
            while(cnt == k){
                if(i - left + 1 < ret){
                    ret = i - left + 1;
                    ans = s.substring(left, i + 1);
                }else if(i - left + 1 == ret){
                    if(ans.compareTo(s.substring(left, i + 1)) > 0) ans = s.substring(left, i + 1);
                }
                if(s.charAt(left) == '1') cnt--;
                left++;
            }
        }
        return ans;
    }
}
