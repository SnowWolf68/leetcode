package problemList.stringHash.solution;

import java.util.HashSet;
import java.util.Set;

/**
二分长度 + 字符串哈希(双哈希)
 */
public class LC1044_3 {
    public String longestDupSubstring(String s) {
        int n = s.length(), p1 = 13, p2 = 17, MOD = (int)1e9 + 7;
        StringHash stringHash1 = new StringHash(s, p1, MOD);
        StringHash stringHash2 = new StringHash(s, p2, MOD);
        int l = 0, r = n;
        String ret = null;
        while(l < r){
            int mid = (l + r + 1) >> 1;
            String str = check(s, mid, stringHash1, stringHash2);
            if(str != null) {
                l = mid;
                ret = str;
            }
            else r = mid - 1;
        }
        return ret == null ? "" : ret;
    }

    /*
     * 检查是否有长为len的重复子串, 如果有, 返回其中某一个符合要求的子串
     */
    private String check(String s, int len, StringHash stringHash1, StringHash stringHash2){
        int n = s.length();
        Set<Long> set = new HashSet<>();
        for(int i = 0;i - 1 + len < n;i++){
            // [i, i - 1 + len]
            int hash1 = (int)stringHash1.getHashValue(i, i - 1 + len);
            int hash2 = (int)stringHash2.getHashValue(i, i - 1 + len);
            long hash = ((long)hash1 << 32) | (long)hash2;
            if(set.contains(hash)){
                return s.substring(i, i + len);
            }
            set.add(hash);
        }
        return null;
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
