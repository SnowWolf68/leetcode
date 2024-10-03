package problemList.misc.solution;

/**
优化计算nums的过程到O(n)

这个优化过程非常巧妙
考虑之前计算nums的过程, 首先外层循环需要遍历ranges, 这是必不可少的
但是由于对于每个下标i来说, 其有可能被多个ranges[i]更新到, 因此需要在内层循环中继续遍历ranges[i]所有可能更新到的下标i
并且更新对应位置的nums[i]

实际上, 我们可以更改nums[]的含义, 从而在O(n)的时间内计算出nums[]

在之前那种nums[]数组的定义中, nums[i] = 下标i向右跳, 最多能够跳多远距离
然而, 这种定义意味着在遍历到每一个ranges[i]时, 都需要再遍历一次[Math.max(0, i - ranges[i]), Math.min(n, i + ranges[i])]中的所有下标
来更新对应的nums[j]

我们注意到, 对于ranges[i]来说, 其所要做的更新为: 
    更新nums[]在区间[Math.max(0, i - ranges[i]), Math.min(n, i + ranges[i])]的nums[j] = i + ranges[i] - j
其实实际上, 上述更新等价于: 更新给定的区间内的 所有下标能够跳到的最远下标为 i + ranges[i]
    即对于nums[]来说, 我们不使用 "增量" 的方式来进行定义, 而是使用 "最远能够跳到的下标" 来进行定义
而对于更新给定区间中 所有元素向右最远能够跳到的位置为i + ranges[i], 实际上可以只更新区间左端点对应的nums[]数组的值
因为在后续的 "造桥" 过程中, 我们是从左往右遍历, 并且在遍历的过程中维护nxtR = Math.max(nxtR, nums[i])
因此对于给定区间[Math.max(0, i - ranges[i]), Math.min(n, i + ranges[i])]来说, 其实我们只需要更新下标为Math.max(0, i - ranges[i])位置的nums[]即可

其次, 还注意到, 在计算nums[]的时候, 要更新的下标为Math.max(0, i - ranges[i]), 其中i是递增的, 如果两次更新的i - ranges[i]相同的话
那么意味着后面那次更新对应的ranges[i]要更大, 因此根据单调性, 要更新的值i + ranges[i]也会更大, 因此只需要保留最后那次更新的值即可
于是又少了一层取max的过程, 这样会再快一点

    注意: 上面的分析只是针对 i - ranges[i] > 0 的情况
        对于i - ranges[i] <= 0的情况, 上面的优化就不生效了, 还是要再取一个max

        还需要注意的是, 对于 i - ranges[i] == 0 的情况 上述优化也是不生效的
        因为对于mostRight[0]这个位置, 除了i - ranges[i] == 0时会被更新到, 当i - ranges[i] < 0时, 更新的也是mostRight[0]这个位置
        因此mostRight[0]这个位置的值, 不一定是最后一次更新的就是最大的, 因此需要每次更新都取一个max

为了和第一种做法中的nums数组的含义区分, 这里我们将nums数组改名为mostRight, 即mostRight[i] = 下标i的位置能够跳到的最远下标为mostRight[i]
 */
public class LC1326_2 {
    public int minTaps(int n, int[] ranges) {
        int[] mostRight = new int[n + 1];
        for(int i = 0;i <= n;i++){
            if(i - ranges[i] > 0) mostRight[i - ranges[i]] = i + ranges[i];
            else mostRight[0] = Math.max(mostRight[0], i + ranges[i]);
        }
        int curR = 0, nxtR = 0, cnt = 0;
        for(int i = 0;i <= n;i++){
            nxtR = Math.max(nxtR, mostRight[i]);
            if(i == curR && i != n){
                if(curR == nxtR) return -1;
                curR = nxtR;
                cnt++;
            }
        }
        return cnt;
    }
}
