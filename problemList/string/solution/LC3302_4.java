package problemList.string.solution;

/**
逐渐往灵神的写法上靠

其实可以添加一个boolean changed变量, 从而让最外层的if判断也可以省掉, 即让i和k共用一层for循环
 */
public class LC3302_4 {
    public int[] validSequence(String s, String t) {
        int m = s.length(), n = t.length();
        int[] suf = new int[m + 1];
        int j = n - 1;
        for(int i = m - 1;i >= 0;i--){
            if(j >= 0 && s.charAt(i) == t.charAt(j)){
                suf[i] = n - j;
                j--;
            }else{
                suf[i] = n - j - 1;
            }
        }
        j = 0;
        int[] ans = new int[n];
        boolean changed = false;
        for(int i = 0;i < m;i++){
            if(s.charAt(i) != t.charAt(j) && !changed){
                if(suf[i + 1] >= n - 1 - j){
                    changed = true;
                    ans[j++] = i;
                }
            }else if(s.charAt(i) == t.charAt(j)){
                ans[j++] = i;
            }
            if(j == n) return ans;
        }
        return new int[]{};
    }
}
