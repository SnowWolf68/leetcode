package solution;

/**
优化: 考虑能不能优化掉内层遍历k的这一层循环
    对于i这一列来说, 首先还是需要遍历这一列应该变成的所有可能的数, 假设为v
    此时再考虑i - 1这一列的情况
        1. 如果[0, i - 1]这些列保留的元素取最大值时, 第i - 1列修改成的元素和v不等, 那么显然可以直接让i列全都修改成v
        2. 如果[0, i - 1]这些列保留的元素取最大值时, 第i - 1列修改成的元素和v相等, 那么此时在i列取v的情况下, 
            [0, i - 1]此时就不能取 保留元素的最大值 了, 而是应该取 次大值
    总的来说就是: 
        假设 [0, i - 1]列保留的元素取 最大值 时, 保留的元素个数为preCnt1, 此时i - 1列所有元素为pre1
            [0, i - 1]列保留的元素取 次大值 时, 保留的元素个数为preCnt2, 此时i - 1列所有元素为pre2 (这里pre2实际上并不会用到)
            i列当前遍历到的元素为v
        1. 如果v != pre1, 当前[0, i]保留的元素最大值为 preCnt1 + cnt[i][v];
        2. 如果v = pre1, 当前[0, i]保留的元素最大值为 preCnt2 + cnt[i][v];
    这样我们就可以在O(1)的时间内, 计算得到dp[i][j]

    特别的: 这里由于我们只需要用到pre1, preCnt1, preCnt2, 因此我们可以将dp表简化为这三个变量
 */
public class LC3122_2 {
    public int minimumOperations(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] cnt = new int[n][10];
        for(int j = 0;j < n;j++){
            for(int i = 0;i < m;i++){
                int num = grid[i][j];
                cnt[j][num]++;
            }
        }
        int pre = -1, preCnt1 = 0, preCnt2 = 0;
        for(int j = 0;j < 10;j++){
            if(cnt[0][j] > preCnt1){
                preCnt1 = cnt[0][j];
                pre = j;
            }
        }
        for(int i = 1;i < n;i++){
            int curCnt1 = preCnt1, curCnt2 = preCnt2;
            for(int j = 0;j < 10;j++){
                if(j != pre){
                    if(preCnt1 + cnt[i][j] > curCnt1){
                        
                    }
                }else{
                    
                }
            }
        }
    }
}
