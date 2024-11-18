package problemList.dp.solution;

/**
直接跑最基本的前后缀分解 + 0-1背包, 时间复杂度可以过, 但是跑的比较慢 658ms, 这里继续学习灵神的题解, 做一些优化

这才只是一部分优化, 其他的优化今天不想再搞了, 之后再说 TODO: 继续优化
 */
public class LC3287_2 {
    public int maxValue(int[] nums, int k) {
        int n = nums.length, maxOr = ((1 << 7) - 1);
        boolean[][][] suf = new boolean[n + 1][k + 1][maxOr + 1], pre = new boolean[n + 1][k + 1][maxOr + 1];
        suf[n][0][0] = true; pre[0][0][0] = true;
        for(int i = n - 1;i >= k;i--){
            for(int j = 0;j <= k;j++){      // i再小没意义, 因为前缀序列不够k长度
                for(int x = 0;x <= maxOr;x++){
                    suf[i][j][x] |= suf[i + 1][j][x];
                    // if(j + 1 <= k) suf[i][j + 1][x | nums[i]] |= suf[i + 1][j][x];
                    if(j - 1 >= 0) suf[i][j][x | nums[i]] |= suf[i + 1][j - 1][x];
                }
            }
        }
        int ret = 0;
        for(int i = 1;i <= n - k;i++){      // i再大没意义, 因为后缀序列不够k长度
            for(int j = 0;j <= k;j++){
                for(int x = 0;x <= maxOr;x++){
                    pre[i][j][x] |= pre[i - 1][j][x];
                    // if(j + 1 <= k) pre[i][j + 1][x | nums[i - 1]] |= pre[i - 1][j][x];
                    if(j - 1 >= 0) pre[i][j][x | nums[i - 1]] |= pre[i - 1][j - 1][x];
                }
            }
            // 为了优化常数, 这里把前后缀分解的部分拿到计算pre的循环中
            // 需要注意的是, 这里只是计算出了 pre[0 : i] 区间的dp值, pre[i + 1] 的dp值此时还没有计算出来
            // 因此这里的分割区间需要变成 [0 : i - 1], [i : n - 1], 相应的i的范围也需要改成i - 1的范围
            if(i - 1 >= k - 1 && i - 1 <= n - 1 - k){
                // 分割区间: [0 : i - 1], [i : n - 1]
                for(int j = 0;j <= maxOr;j++){
                    if(suf[i][k][j]){
                        for(int x = 0;x <= maxOr;x++){
                            if(pre[i][k][x]){
                                ret = Math.max(ret, j ^ x);
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }
}
