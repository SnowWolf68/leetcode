package problemList.dp.solution;

import java.util.Arrays;

/**
使用相邻相关的状压dp来解决
dp[state][i] 表示当前选择的元素集合为state, 并且当前选择的元素的下标为i时, 此时的最小不兼容性
dp[state][i]: 
    1. 如果bitCount(state) % k == 1, 说明此时这个元素是某一组的第一个元素, 那么此时没有 不重复 的约束, 因此: 
        枚举上一个组的最后一个元素, 假设为pre, 那么有
        dp[state][i] = min(dp[state ^ (1 << i)][pre])
    2. 如果bitCount(state) % k != 1, 说明当前组中前面已经有元素, 因此此时的下标为i的元素要受到前面 组内不重复 的约束, 因此:
        依旧是枚举上一个元素的下标, 假设为pre: 
        由于对于每个子集来说, 我们都是从小到大枚举, 因此此时当前子集兼容性的增量为 nums[i] - nums[pre]
        dp[state][i] = min(dp[state ^ (1 << i)][pre] + nums[i] - nums[pre])
初始化: 初始化所有dp[0][i] = 0, 从state = 1开始枚举, 其余位置首先都初始化为INF
return min(dp[mask][i])
 */
public class LC1681_1 {
    // TODO: 还有问题
    public int minimumIncompatibility(int[] nums, int k) {
        int n = nums.length, mask = 1 << n, INF = 0x3f3f3f3f;
        Arrays.sort(nums);
        int[][] dp = new int[mask][n];
        for(int state = 1;state < mask;state++){
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                dp[state][i] = INF;
                if(Integer.bitCount(state) % k == 1){
                    int preState = state ^ (1 << i);
                    if(preState == 0){
                        dp[state][i] = 0;
                    }else{
                        for(int pre = 0;pre < n;pre++){
                            if(((state >> pre) & 1) == 0 || pre == i) continue;
                            dp[state][i] = Math.min(dp[state][i], dp[state ^ (1 << i)][pre]);
                        }
                    }
                }else{
                    for(int pre = 0;pre < i;pre++){
                        if(((state >> pre) & 1) == 0 || nums[pre] == nums[i] || pre == i) continue;
                        dp[state][i] = Math.min(dp[state][i], dp[state ^ (1 << i)][pre] + nums[i] - nums[pre]);
                    }
                }
            }
        }
        // System.out.println("------------------------------");
        // for(int state = 0;state < mask;state++){
        //     for(int i = 0;i < n;i++){
        //         System.out.print(dp[state][i] + " ");
        //     }
        //     System.out.println();
        // }
        int ret = INF;
        for(int i = 0;i < n;i++) ret = Math.min(ret, dp[mask - 1][i]);
        return ret;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{6,3,8,1,3,1,2,2};
        int k = 4;
        System.out.println(new LC1681_1().minimumIncompatibility(nums, k));
    }
}
