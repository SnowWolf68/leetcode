package problemList.stringHash.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
如果使用HashSet, 那么截取子串subString也需要花费O(n)的时间 (其中n为截取的子串长度)
如果想要做到真正的O(1), 那么可以使用字符串哈希
 */
public class LC472_2 {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Set<Integer> set = new HashSet<>();
        int p = 131313, MOD = Integer.MAX_VALUE;
        for(String s : words) set.add((int)StringHash.evaluate(s, p, MOD));
        List<String> ret = new ArrayList<>();
        for(String str : words){
            int n = str.length();
            StringHash stringHash = new StringHash(str, p, MOD);
            int[] dp = new int[n + 1];
            for(int i = 1;i <= n;i++){
                for(int j = 1;j <= i;j++){
                    int hash = (int)stringHash.getHashValue(j - 1, i - 1);
                    if(set.contains(hash) && (dp[j - 1] > 0 || j - 1 == 0)){
                        dp[i] = Math.max(dp[i], dp[j - 1] + 1);
                    }
                }
                if(dp[n] >= 2) ret.add(str);
            }
        }
        return ret;
    }

    public class StringHash {
        private long p;
        private long MOD;
    
        private int n;          // 字符串长度
        private long[] pre;      // 哈希值前缀
        private long[] pow;      // pow[i] = p^i
    
        StringHash (String str, long p, long mod) {
            this.p = p;
            this.MOD = mod;
            this.n = str.length();
    
            pre = new long[n + 1];
            pow = new long[n + 1];
    
            pre[0] = 0;
            pow[0] = 1;
    
            for(int i = 1;i <= n;i++){
                pow[i] = (pow[i - 1] * p) % MOD;
            }
    
            for(int i = 1;i <= n;i++){
                pre[i] = ((pre[i - 1] * p) % MOD + str.charAt(i - 1)) % MOD;
            }
        }
    
        long getHashValue(int l, int r){
            return ((pre[r + 1] - (pre[l] * pow[r - l + 1]) % MOD) + MOD) % MOD;
        }
    
        long rotate(int l) {
            if (l < 0 || l >= n - 1) {
                return getHashValue(0, n - 1);
            } else {
                long hash1 = getHashValue(0, l);
                long hash2 = getHashValue(l + 1, n - 1);
                return (hash2 * pow[l + 1] % MOD + hash1) % MOD;
            }
        }
    
        static long evaluate(String s, int p, long MOD){
            long hash = 0;
            for(char c : s.toCharArray()){
                hash = ((hash * p) % MOD + c) % MOD;
            }
            return hash;
        }
    }
}
