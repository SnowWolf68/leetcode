package problemList.stringHash.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 双哈希
 */
public class LC187_2 {
    public List<String> findRepeatedDnaSequences(String s) {
        int p1 = 13, p2 = 17, MOD = (int)1e9 + 7, n = s.length();
        StringHashBasic stringHash = new StringHashBasic(s, p1, MOD);
        StringHashBasic stringHash2 = new StringHashBasic(s, p2, MOD);
        Map<Long, String> hashToString = new HashMap<>();
        Map<Long, Integer> cnt = new HashMap<>();
        for(int i = 0;i + 9 < n;i++){
            String subString = s.substring(i, i + 10);
            int hash1 = (int)stringHash.getHashValue(i, i + 9);
            int hash2 = (int)stringHash2.getHashValue(i, i + 9);
            long hash = ((long)hash1 << 32) | (long)hash2;
            hashToString.put(hash, subString);
            cnt.put(hash, cnt.getOrDefault(hash, 0) + 1);
        }
        List<String> ret = new ArrayList<>();
        for(long key : cnt.keySet()){
            if(cnt.get(key) > 1){
                System.out.println(cnt.get(key));
                ret.add(hashToString.get(key));
            }
        }
        return ret;
    }

    class StringHashBasic {
        private long p;
        private long MOD;
    
        private int n;          // 字符串长度
        private long[] pre;      // 哈希值前缀
        private long[] pow;      // pow[i] = p^i
    
        StringHashBasic (String str, long p, long mod) {
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
    }
}
