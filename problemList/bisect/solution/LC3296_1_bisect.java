package problemList.bisect.solution;

/**
二分答案: 
这题我一开始在想能不能贪心地分配每人降低多少高度, 但是WA了几次之后发现没法贪心
正解应该是二分答案

二分啥东西?
    -- 二分最终的答案, 即二分总共所需要的时间
        找出一个最小的时间, 在这个时间内, 能够保证所有的工人一起工作, 能够将山的高度减为0
为什么能二分? 
    -- 因为对于每个工人来说, 花的时间越长, 能够降低的高度也就越高, 存在单调性, 可以二分

如何二分?
    -- 二分的一个关键点在于, 当二分到一个值之后, 怎么判断这个值是否符合要求, 从而折半区间
    因此我们就需要推导计算, 在给定的时间time内, 所有工人一起工作, 能够降低的最大高度是多少?

    一个工人降低x高度, 所需要的时间为: t + 2t + 3t + ... + xt = t * (x * (x + 1)) / 2
    令t * (x * (x + 1)) / 2 = m, 即在m时间内, 一个工人最多减少x高度
    从上式中解得x = (sqrt(1 + 4 * k) - 1) / 2, 其中k = 2 * floor(m / t)
    即得到一个工人在时间m内, 最多减少x = (sqrt(1 + 4 * k) - 1) / 2这么多高度
    累加此时所有工人降低的高度和, 即得到在m时间内所有人一起工作, 可以减少的山的高度

    如果这个高度 >= mountainHeight, 说明此时可以将山全部移走, 反之不能
二分的上下界?
    -- 下界: 0
    -- 上界: 设maxT = max(workerTimes), n = workerTimes.length
        假设所有工人都是maxT的最坏情况, 每个工人需要降低的高度为ceil(mountainHeight / n) = h
        那么所需要的时间为: maxT + 2 * maxT + ... + h * maxT = maxT * (h * (h + 1)) / 2
        因此maxT * (h * (h + 1)) / 2即为上界
何种类型的二分?
    -- 要找满足要求的最小值, 因此是一个查找右区间左端点的二分
 */
public class LC3296_1_bisect {
    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {
        int n = workerTimes.length;
        int maxT = 0;
        for(int t : workerTimes) maxT = Math.max(maxT, t);
        long lower = 0, h = (long)Math.ceil((double)mountainHeight / n), upper = maxT * (h * (h + 1)) / 2;
        long l = lower, r = upper;
        while(l < r){
            long mid = (l + r) >> 1;
            if(check(mountainHeight, workerTimes, mid)) r = mid;
            else l = mid + 1;
        }
        return l;
    }

    // check在time时间内workerTimes这些工人能否将height减为0
    private boolean check(int height, int[] workerTimes, long time){
        int sumHeight = 0;
        for(int t : workerTimes){
            int k = 2 * (int)Math.floor(time / t), x = ((int)Math.sqrt(1 + 4 * k) - 1) / 2;
            sumHeight += x;
        }
        if(sumHeight >= height) return true;
        else return false;
    }
}
