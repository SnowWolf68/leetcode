package problemList.dp.solution;

/**
状态转移方程: 
    f[i] = g[i - 1];
    g[i] = f[i - 1] + g[i - 1];
注意: 如下的状态转移方程是错误的: f[i] = g[i - 1] + 1;
    因为 +1 意味着考虑只在下标为i的地块上放置房屋的情况, 然而, 这种情况已经在g[i - 1]中包括了, 因此这里不需要 +1
 */
public class LC2320 {
    public int countHousePlacements(int n) {
        int[] f = new int[n], g = new int[n];
        int MOD = (int)1e9 + 7;
        f[0] = g[0] = 1;
        for(int i = 1;i < n;i++){
            f[i] = g[i - 1];
            g[i] = (f[i - 1] + g[i - 1]) % MOD;
        }
        long res = ((long)f[n - 1] + g[n - 1]) % MOD;
        return (int)((res * res) % MOD);
    }
}
