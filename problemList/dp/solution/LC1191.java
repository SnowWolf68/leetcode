package problemList.dp.solution;

/**
串联后的最大子数组和有四种可能
    1. sum(arr) * k
    2. arr中的最大子数组和
    3. preffix(arr) + (k - 2) * sum(arr) + suffix(arr)
    4. preffix(arr) + suffix(arr)
    注: 第3/4种情况的前提是k >= 2
针对第3/4种情况, 应使得preffix(arr) + suffix(arr)最大
    求pre(arr) + suf(arr)最大:
        错误做法: 要求pre(arr) + suf(arr)最大, 那么就求一个子数组的最小值minSum, 然后2 * sum(arr) - minSum即可
        错误原因: 这里要求pre和suf都必须是一个arr中的, 而上面的方法求出来的pre和suf可能会跨两个arr, 这显然是不对的

        正确做法: 单独求pre和suf, 并且单独求pre和suf的最大值即可
 */
public class LC1191 {
    public int kConcatenationMaxSum(int[] arr, int k) {
        int n = arr.length, MOD = (int)1e9 + 7;
        long sum = 0;
        for(int x : arr) sum += x;
        long f = arr[0], ret = f;
        for(int i = 1;i < n;i++){
            f = Math.max(f + arr[i], arr[i]);
            ret = Math.max(ret, f);
        }
        ret = Math.max(ret, 0);
        long preSum = arr[0], preSumMax = arr[0];
        for(int i = 1;i < n;i++){
            preSum += arr[i];
            preSumMax = Math.max(preSumMax, preSum);
        }
        long sufSum = arr[n - 1], sufSumMax = arr[n - 1];
        for(int i = n - 2;i >= 0;i--){
            sufSum += arr[i];
            sufSumMax = Math.max(sufSumMax, sufSum);
        }
        return (int)(Math.max(sum * k, Math.max(ret, Math.max(k < 2 ? 0 : sufSumMax + preSumMax + (k - 2) * sum, k < 2 ? 0 : preSumMax + sufSumMax))) % MOD);
    }
}
