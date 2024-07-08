package problemList.stringHash.solution;

/**
这里的判断规则很巧妙

首先得到拼接字符串a的最多次数为 (b.length() / a.length())上取整 + 1, 即cnt = (int)Math.ceil((double)n / m) + 1;
因此先得到拼接后的字符串, 然后在拼接后的字符串中匹配b
如果某个匹配位置前后所剩下的字符数量超过a.length(), 说明只需要cnt - 1次拼接即可, 如果匹配位置前后剩下的字符数量不超过a.length(), 说明需要cnt次拼接
如果找不到匹配位置, 返回-1即可
 */
public class LC686 {
    public int repeatedStringMatch(String a, String b) {
        int m = a.length(), n = b.length();
        int cnt = (int)Math.ceil((double)n / m) + 1;
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < cnt;i++){
            sb.append(a);
        }
        String aa = sb.toString();
        int p = 13, MOD = (int)1e9 + 7;
        StringHash stringHash = new StringHash(aa, p, MOD);
        int hashB = (int)StringHash.evaluate(b, p, MOD);
        for(int i = 0;i < 2 * m && i - 1 + n < aa.length();i++){
            if((int)stringHash.getHashValue(i, i - 1 + n) == hashB){
                if(i >= m || i - 1 + n < aa.length() - m){
                    return cnt - 1;
                }else{
                    return cnt;
                }
            }
        }
        return -1;
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
