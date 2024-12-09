package problemList.dp.solution;

import java.util.ArrayList;
import java.util.List;

/**
二刷

递推顺序
    由于本题要求我们输出 "字典序最小的排列" , 而不是 "任何一种可能的排列", 因此我们需要考虑怎么样能够保证字典序最小
    对于dp[state][i]这种状态表示来说, 所谓的递推顺序, 就是state的枚举顺序是 从小到大 还是 从大到小 
    按照 相邻相关型状压DP 的一般套路来说, 我们都是: 定义dp[state][i]为 在当前已经选的元素中考虑, 此时分数最低的排列, 那么这样的状态表示就意味着我们必须要从小到大枚举 state
    那么在dp完构造具体方案的时候, 显然我们需要 按照dp序的逆序 来构造具体方案, 那么此时由于dp序我们是按照 选了k个元素 -> 选了k + 1个元素 这样的顺序计算的, 
    那么最后构造答案的时候, 我们就只能让state从大到小枚举, 即只能知道 选了k + 1个元素, 并且分数最低时, 第k个元素选的是什么, 那么这样的构造顺序显然不能保证最终构造出来的方案的字典序是最小的

    换句话说, 如果dp时, 我们的state的枚举顺序是 从小到大, 那么在dp完之后构造具体方案时, state的枚举顺序就是从大到小, 显然state从大到小的枚举顺序显然不能保证最终构造出来的排列是字典序最小的
    如果我们从最后构造字典序最小的排列的角度分析, 也就是意味着最终构造时, state应该是 从小到大 进行枚举的, 那么dp时, state的枚举顺序就应该是 从大到小

    那么此时状态应该如何定义呢?
    要想让计算dp时的state从大到小枚举, 我们可以定义 dp[state][i] 表示 当前已经选的元素(state对应位是1)是state, 并且 从此时 剩下 的元素(state对应位是0)中选 , 此时最低的分数
    在这种状态定义下, 最后构造字典序最小的排列时, 就可以让state从0开始构造, 此时意味着 "剩下n个元素", 那么通过dp表, 我们就可以知道 "剩下n - 1" 个元素时, 此时所有可能的排列的分数是多少, 
    那么此时构造的顺序就是 "从前往后" , 这样显然就可以保证 构造出来的字典序是最小的

这里还有另一种角度来理解这里的两种递推顺序: 
    对于state从小到大的递推顺序来说, 其实我们是按照 perm[0] 到 perm[n - 1] 这样的顺序考虑的
    那么对于state从大到小的递推顺序来说, 这就是按照 perm[n - 1] 到 perm[0] 这样的顺序考虑的
也可以从这个角度来更好的理解一下上面说的 dp顺序 与 构造方案顺序 之间的关系

dp[state][i] 表示当前选择的元素集合为state, 并且最后选择的元素的下标是i, 此时从剩下的元素(state中对应位是0)中选, 此时的最低分数
dp[state][i]: 枚举下一个要选的元素, 假设为nxt, 那么有: 
    dp[state][i] = min(dp[state | (1 << nxt)][nxt] + abs(i - nums[nxt]));
初始化: 这里由于递推方向是 state 从大到小递推, 因此我们初始化的位置应该是 剩余0个元素 的情况
    因此我们可以初始化 dp[mask - 1][i] = Math.abs(i - nums[0]);
        -- 实际上这里我们就是要初始化选择perm[n - 1]为元素i时的情况
            相比之下, 如果按照state从小到大的顺序递推的话, 这里的初始化就是要初始化 选择perm[0]为元素i时的情况
return dp[1][0];    // 因为在字典序最小的情况下, 一定有: perm[0] = 0
 */
public class LC3149_3 {
    public int[] findPermutation(int[] nums) {
        int n = nums.length, mask = 1 << n, INF = 0x3f3f3f3f;
        int[][] dp = new int[mask][n];
        for(int i = 1;i < n;i++) dp[mask - 1][i] = Math.abs(i - nums[0]);
        dp[mask - 1][0] = INF;
        for(int state = mask - 2;state >= 0;state--){
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                dp[state][i] = INF;
                for(int nxt = 0;nxt < n;nxt++){
                    if(((state >> nxt) & 1) == 1) continue;
                    dp[state][i] = Math.min(dp[state][i], dp[state | (1 << nxt)][nxt] + Math.abs(i - nums[nxt]));
                }
            }
        }
        int state = 1, idx = 0;
        List<Integer> list = new ArrayList<>();
        list.add(idx);
        while(state != mask - 1){
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 1) continue;
                if(dp[state][idx] == dp[state | (1 << i)][i] + Math.abs(idx - nums[i])){
                    list.add(i);
                    state = state | (1 << i);
                    idx = i;
                    break;  // 找到从左到右第一个nxt就break, 保证字典序最小
                }
            }
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
