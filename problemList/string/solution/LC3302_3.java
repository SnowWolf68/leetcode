package problemList.string.solution;

/**
发现双指针部分由于suf[i + 1]可能越界, 因此有比较多的if判断部分, 因此考虑添加suf[m], 作为辅助节点
减少if判断的部分

由于suf[i] = s[i:]和t的后缀的最大匹配长度, 因此可以初始化suf[m] = 0
 */
public class LC3302_3 {
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
        for(int i = 0;i < m;i++){
            if(s.charAt(i) == t.charAt(j)){
                // 如果相等, 那么两个指针都向后移动
                ans[j++] = i;
                if(j == n) return ans;
            }else{
                if(suf[i + 1] >= n - 1 - j){
                    ans[j++] = i;
                    if(j < n){
                        for(int k = i + 1;k < m;k++){
                            if(s.charAt(k) == t.charAt(j)) ans[j++] = k;
                            if(j == n) break;
                        }
                    }
                    return ans;
                }
            }
        }
        return new int[]{};
    }
}
