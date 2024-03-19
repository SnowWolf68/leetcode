package problems.sliding_window.solution;

public class LC567 {
    public boolean checkInclusion(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        if(m > n) return false;
        int[] cnt1 = new int[26];
        for(char c : s1.toCharArray()) cnt1[c - 'a']++;
        int[] cnt2 = new int[26];
        int left = 0, right = 0;
        while(right < m){
            cnt2[s2.charAt(right) - 'a']++;
            right++;
        }
        boolean flag = true;
        for(int i = 0;i < 26;i++){
            if(cnt1[i] != cnt2[i]){
                flag = false;
                break;
            }
        }
        if(flag) return true;
        while(right < n){
            cnt2[s2.charAt(left) - 'a']--;
            cnt2[s2.charAt(right) - 'a']++;
            left++;
            right++;
            flag = true;
            for(int i = 0;i < 26;i++){
                if(cnt1[i] != cnt2[i]){
                    flag = false;
                    break;
                }
            }
            if(flag) return true;
        }
        return false;
    }
}
