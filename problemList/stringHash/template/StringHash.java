package problemList.stringHash.template;

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