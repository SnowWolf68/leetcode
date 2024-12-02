package problemList.dp.solution;

/**
s是平衡的 --> s中不存在 (b, a) 的逆序对
换句话说, s中的字母只能是 一串a(或者没有a) + 一串b(或者没有b)
因此我们可以枚举分割点, 计算出前缀[0, i]中有多少个b 以及 后缀[i, n - 1]中有多少个a
利用前后缀分解的思想可以解决
 */
public class LC1653 {
    public int minimumDeletions(String s) {
        int n = s.length();
        int[] prefix = new int[n + 1], suffix = new int[n + 1];
        for(int i = 0;i < n;i++){
            if(s.charAt(i) == 'b') prefix[i + 1] = prefix[i] + 1;
            else prefix[i + 1] = prefix[i];
        }
        for(int i = n - 1;i >= 0;i--){
            if(s.charAt(i) == 'a') suffix[i] = suffix[i + 1] + 1;
            else suffix[i] = suffix[i + 1];
        }
        int ret = suffix[0];
        for(int i = 0;i < n;i++){
            ret = Math.min(ret, prefix[i] + suffix[i + 1]);
        }
        return ret;
    }
}
