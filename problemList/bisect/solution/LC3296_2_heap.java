package problemList.bisect.solution;

import java.util.PriorityQueue;

/**
这题也可以使用最小堆模拟, 其实本质还是贪心

贪心策略: 其实这个贪心策略也比较直观
        首先题目要求所有工人可以同时工作, 因此最终整体所花费的时间就是所有工人各自所花费的时间的最大值
        而我们又可以把降低山的mountainHeight高度看成 每次都选一个工人降低1的高度
        因此问题转化成了: 如何选出下一个要工作的工人, 使得max(每个工人的工作时间)最短

        因此很自然的想到可以维护nxt数组, 其中nxt[i]表示下标为i的工人, 接下来再降低1的高度, 其总共所需要的工作时间
        需要注意的是, 这里nxt[i]是总共的工作时间, 而不是再降低1的高度所需要的额外的工作时间, 因为最终的工作时间, 取决于每个工人总共的工作时间的最大值
        因此贪心策略为: 每次选择nxt[i]最小的工人, 作为接下来再降低1高度的工人

接下来我用示例1来描述一下贪心的实现过程

对于示例:    mountainHeight = 4, workerTimes = [2,1,1]
            输出： 3
                A  B  C
        idx     0  1  2      idx: 下标
        BASE:   2  1  1      BASE[i]指的是workerTimes[i]
        cur:    0  0  0      cur: 当前每个工人的工作时间
        cnt:    0  0  0      cnt: 每个工人降低的山的高度
        nxt:    2  1  1      nxt: 每个工人接下来要想将高度降低1, 总共需要的工作时间

        第一次选择: 选择nxt[i]最小的, 这里B和C均可, 不妨选择B
        更新: 
            cnt[1]++;                           // cnt[1] = 1
            cur[1] = nxt[1];                    // cur[1] = 1
            nxt[1] += BASE[1] * (cnt[1] + 1);   // nxt[1] = 3
            mountainHeight--;                   // mountainHeight = 3;
                A  B  C
        idx     0  1  2      idx: 下标
        BASE:   2  1  1      BASE[i]指的是workerTimes[i]
        cur:    0  1  0      cur: 当前每个工人的工作时间
        cnt:    0  1  0      cnt: 每个工人降低的山的高度
        nxt:    2  3  1      nxt: 每个工人接下来要想将高度降低1, 总共需要的工作时间

        第二次选择: 选择nxt[i]最小的, 即C
        更新: 
            cnt[2]++;                           // cnt[2] = 1
            cur[2] = nxt[2];                    // cur[2] = 1
            nxt[2] += BASE[2] * (cnt[2] + 1);   // nxt[2] = 3
            mountainHeight--;                   // mountainHeight = 2;
                A  B  C
        idx     0  1  2      idx: 下标
        BASE:   2  1  1      BASE[i]指的是workerTimes[i]
        cur:    0  1  1      cur: 当前每个工人的工作时间
        cnt:    0  1  1      cnt: 每个工人降低的山的高度
        nxt:    2  3  3      nxt: 每个工人接下来要想将高度降低1, 总共需要的工作时间
        
        第三次选择: 选择nxt[i]最小的, 即A
        更新: 
            cnt[0]++;                           // cnt[0] = 1
            cur[0] = nxt[0];                    // cur[0] = 2
            nxt[0] += BASE[0] * (cnt[0] + 1);   // nxt[0] = 6
            mountainHeight--;                   // mountainHeight = 1;
                A  B  C
        idx     0  1  2      idx: 下标
        BASE:   2  1  1      BASE[i]指的是workerTimes[i]
        cur:    2  1  1      cur: 当前每个工人的工作时间
        cnt:    1  1  1      cnt: 每个工人降低的山的高度
        nxt:    6  3  3      nxt: 每个工人接下来要想将高度降低1, 总共需要的工作时间

        第四次选择: 选择nxt[i]最小的, 即B或C, 这里不妨选B
        更新: 
            cnt[1]++;                           // cnt[1] = 2
            cur[1] = nxt[1];                    // cur[1] = 3
            nxt[1] += BASE[1] * (cnt[1] + 1);   // nxt[1] = 9
            mountainHeight--;                   // mountainHeight = 0;
                A  B  C
        idx     0  1  2      idx: 下标
        BASE:   2  1  1      BASE[i]指的是workerTimes[i]
        cur:    2  3  1      cur: 当前每个工人的工作时间
        cnt:    1  2  1      cnt: 每个工人降低的山的高度
        nxt:    6  9  3      nxt: 每个工人接下来要想将高度降低1, 总共需要的工作时间
        
        mountainHeight = 0, 贪心结束, 最终花费的总时间为 max(cur[i]) = 3
 */
public class LC3296_2_heap {
    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {
        int n = workerTimes.length;
        // <idx, nxt> 按照nxt的小根堆
        PriorityQueue<long[]> minHeap = new PriorityQueue<>((o1, o2) -> (int)(o1[1] - o2[1]));
        for(int i = 0;i < n;i++) minHeap.add(new long[]{i, workerTimes[i]});
        int[] cnt = new int[n];
        long[] cur = new long[n];
        while(mountainHeight > 0){
            long[] poll = minHeap.poll();
            int idx = (int)poll[0];
            long nxt = poll[1];
            cnt[idx]++;
            cur[idx] = nxt;
            nxt += (long)workerTimes[idx] * (cnt[idx] + 1);
            minHeap.add(new long[]{idx, nxt});
            mountainHeight--;
        }
        long ret = 0;
        for(long x : cur) ret = Math.max(ret, x);
        return ret;
    }
}
