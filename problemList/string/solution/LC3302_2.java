package problemList.string.solution;

/**
大致思路和写法1还是一样的, 只是稍微改动了一下不相等时的if条件
这种写法更符合自然的思维方式
 */
public class LC3302_2 {
    public int[] validSequence(String s, String t) {
        int m = s.length(), n = t.length();
        int[] suf = new int[m];
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
                // 不相等
                // 或者也可以按下面这种方式来写, 感觉这样更清晰
                // 首先由于suf[i + 1]有可能越界, 因此这里需要对i特殊判断一下
                if(i == m - 1){
                    if(j == m - 1){
                        ans[j] = i;
                        return ans;
                    }else{
                        // 此时说明t中剩下不止一个字符没有匹配, 此时显然找不到任何一个符合要求的子序列, 返回[]
                        return new int[]{};
                    }
                }else{
                    // 此时可以用suf[i + 1]了
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
        }
        return new int[]{};
    }
}
