package problemList.stringHash.solution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class LC3213_StringHash {
    public int minimumCost(String target, String[] words, int[] costs) {
        int p1 = 13, p2 = 17, n = target.length(), INF = 0x3f3f3f3f;
        long MOD = (long)1e9 + 7;
        // int MOD = (int)1e9 + 7;
        StringHash stringHash1 = new StringHash(target, p1, MOD);
        StringHash stringHash2 = new StringHash(target, p2, MOD);

        // 统计words中有多少种不同的长度, 使用TreeSet进行去重
        Set<Integer> lenSet = new TreeSet<>();
        for(String s : words){
            lenSet.add(s.length());
        }

        // words长度数组
        int[] lenArr = lenSet.stream().mapToInt(Integer::valueOf).toArray();

        // 建立双哈希值到costs的映射关系
        Map<Long, Integer> costMap = new HashMap<>();
        for(int i = 0;i < words.length;i++){
            long hash1 = StringHash.evaluate(words[i], p1, MOD);
            long hash2 = StringHash.evaluate(words[i], p2, MOD);
            long hash = (hash1 << 32) | hash2;
            /*
            int hash1 = (int)StringHash.evaluate(words[i], p1, MOD);
            int hash2 = (int)StringHash.evaluate(words[i], p2, MOD);
            long hash = ((long)hash1 << 32) | (long)hash2;
             */
            // words中可能有相同字符, 因此cost需要取最小的那个
            costMap.put(hash, Math.min(costMap.getOrDefault(hash, INF), costs[i]));
        }


        int[] dp = new int[n + 1];
        Arrays.fill(dp, INF);   dp[0] = 0;
        for(int i = 1;i <= n;i++){
            // i指的是下标 +1
            for(int len : lenArr){
                if(i - len >= 0){
                    // 最后一个字符串的下标范围是: [i - len, i - 1]
                    long hash1 = stringHash1.getHashValue(i - len, i - 1);
                    long hash2 = stringHash2.getHashValue(i - len, i - 1);
                    long hash = (hash1 << 32) | hash2;
                    /**
                    int hash1 = (int)stringHash1.getHashValue(i - len, i - 1);
                    int hash2 = (int)stringHash2.getHashValue(i - len, i - 1);
                    long hash = ((long)hash1 << 32) | (long)hash2;
                     */
                    if(costMap.get(hash) != null) dp[i] = Math.min(dp[i], dp[i - len] + costMap.get(hash));
                }
            }
        }
        return dp[n] == INF ? -1 : dp[n];
    }

    class StringHash {
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
