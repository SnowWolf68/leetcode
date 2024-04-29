package problemList.dp.solution;

import java.util.Arrays;

/**
这题虽然分类放在线性dp中, 但是其实使用划分型dp来理解更自然
    dp[i] 表示跑完前i圈, 所需要的最少时间
        dp[i]: 由于轮胎有n种选择, 因此我们首先需要遍历当前使用哪一条轮胎, 然后
            枚举当前这条轮胎从哪一圈开始跑, 假设从j这一圈开始跑, 其中j的范围[0, i]
                dp[i] = dp[j - 1] + changeTime + cost[i - j + 1];
                其中, cost[i - j + 1]表示使用当前轮胎跑i - j + 1圈所需要的耗时
        实际上: 由于cost[i - j + 1]只和轮胎使用的圈数有关, 而和具体使用哪一条轮胎无关, 因此我们可以首先预处理出cost[]数组
        其中cost[i]表示使用任意一条轮胎, 跑i圈, 所需要的最小耗时, 那么这里在计算dp[i]的时候, 就不需要遍历所有轮胎了

        这里j的范围其实还可以继续缩小
        由于轮胎的圈速是随着圈数的增加而不断增大的, 而每次换胎的时间changeTime又是不变的, 那么对于某一种轮胎来说, 其连续使用的圈数一定会有一个上界maxLaps
        如果当前轮胎连续使用的圈数超过了maxLaps, 那么此时继续跑下去的圈速一定大于换新胎的圈速 + 换胎时间, 那么显然超过了maxLaps的这些圈我们就不需要考虑
        下面来计算一下maxLaps:
        1. 第一种计算方式
            通过maxLaps的定义我们可以知道, 当连续使用的圈数超过了maxLaps之后, 每一圈的圈速 大于 换新胎的第一圈的圈速 + 换胎时间, 即
            fi * ri ^ (maxLaps + 1 - 1) > changeTime + fi
            那么解得maxLaps > log(changeTime / fi + 1) / log(ri), 那么maxLaps应该取 > log(changeTime / fi + 1) / log(ri)的最小值
            由于这里fi, ri是对于任意一条轮胎来说的, 那么我们应该找到合适的fi, ri, 使得log(changeTime / fi + 1) / log(ri)最大
            那么显然让fi, ri都取最小值, 此时log(changeTime / fi + 1) / log(ri)最大, 通过题目中给的数据范围, fi最小值为1, ri最小值为2
            因此log(changeTime / fi + 1) / log(ri) <= log(changeTime + 1) / log2
            因此maxLaps > log(changeTime + 1) / log2
            那么maxLaps取大于log(changeTime + 1) / log2的最小值, 即maxLaps = Math.ceil(Math.log(changeTime + 1) / Math.log(2));
        2. 第二种计算方式
            还是根据maxLaps的定义, 换一个角度来分析, 我们还可以这样:
            maxLaps意味着所有小于maxLaps的圈中, 连续使用这一条轮胎的每一圈的圈速, 一定小于换新胎第一圈的圈速 + 换胎时间, 即
            fi * ri ^ (maxLaps - 1) <= changeTime + fi
            解得maxLaps <= (log(changeTime / fi + 1) / log(ri)) + 1
            那么maxLaps应该取小于等于(log(changeTime / fi + 1) / log(ri)) + 1的最大值, 同理我们应该取fi, ri, 让log(changeTime / fi + 1) / log(ri)最大
            和上面类似, 我们还是应该取fi = 1, ri = 2, 那么log(changeTime / fi + 1) / log(ri) <= log(changeTime + 1) / log2
            因此maxLaps = Math.floor(Math.log(changeTime + 1) / Math.log(2)) + 1;
        注: 以上两种方式分别求出来的maxLaps可能会不相等, 但是一定都符合要求, 即都能包括所有可能用到的laps
    初始化: i == 0本身就是一个辅助节点, dp[0]意味着此时不需要跑任何圈, 那么此时时间肯定为0
        但是: 这里由于状态转移方程为dp[i] = dp[j - 1] + changeTime + cost[i - j + 1], 也就是每次都会首先加上changeTime
        那么这里如果初始化dp[0] = 0, 那么对于第一条轮胎来说, 此时就相当于多加了一个changeTime, 这显然不行, 因此为了修正第一条轮胎多加上的那个changeTime, 这里我们初始化dp[0] = -changeTime
    return dp[numLaps - 1];

    细节: 这里在计算cost[j]的时候, 会出现sum溢出的情况, 并且这个溢出使用long也无法避免, 这个溢出的根本原因在于, 对于当前这条轮胎来说, 其连续跑的圈数太多, 从而导致耗时过长
        因此相应的就有两种解决方案:
            1. 如果有溢出, 那么意味着当前连续跑j圈的情况下, 肯定不能用当前的这个轮胎来跑, 那么我们可以直接判断溢出, 即如果sum < 0或cur < 0, 那么意味着此时肯定溢出了
                那么此时肯定无需继续计算, 直接break即可
            2. 溢出的原因在于当前这个轮胎不能连续跑j圈, 也就是说, 这个轮胎继续跑第j圈的耗时, 要大于 换一条新的当前轮胎的第一圈的圈速 + 换胎时间, 即
                cur > changeTime + tire[i][0], 那么意味着此时当前轮胎不能再继续跑下去了, 那么此时直接break即可
                注意这里比较是需要用cur, 即当前这个轮胎跑第j圈的圈速, 而不是跑j圈的总时间sum
    
 */
public class LC2188 {
    public int minimumFinishTime(int[][] tires, int changeTime, int numLaps) {
        int n = tires.length, INF = 0x3f3f3f3f;
        int maxLaps = (int)Math.ceil(Math.log(changeTime + 1) / Math.log(2));
        // 使用下面这种方式计算maxLaps也可
        // int maxLaps2 = (int)Math.floor(Math.log(changeTime + 1) / Math.log(2)) + 1;
        int[] cost = new int[maxLaps + 1];
        Arrays.fill(cost, INF);
        for(int i = 0;i < n;i++){
            cost[1] = Math.min(cost[1], tires[i][0]);
            int cur = tires[i][0], sum = tires[i][0];
            for(int j = 2;j <= maxLaps;j++){
                cur *= tires[i][1];
                sum += cur;
                if(sum < 0 || cur < 0) break;
                // 使用下面这种方式解决溢出也可以
                // if(cur > changeTime + tires[i][0]) break;
                cost[j] = Math.min(cost[j], sum);
            }
        }
        int[] dp = new int[numLaps + 1];
        dp[0] = -changeTime;
        for(int i = 1;i <= numLaps;i++){
            dp[i] = INF;
            for(int j = Math.max(1, i - maxLaps + 1);j <= i;j++){
                dp[i] = Math.min(dp[i], dp[j - 1] + changeTime + cost[i - j + 1]);
            }
        }
        return dp[numLaps];
    }
}
