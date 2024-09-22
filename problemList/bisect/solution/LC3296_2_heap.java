package problemList.bisect.solution;

import java.util.PriorityQueue;

/**
这题也可以使用最小堆模拟

贪心决策规则: 根据nxt, 即下一次减少所需要额外花费的时间 来决策
 */
public class LC3296_2_heap {
    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {
        int n = workerTimes.length;
        // <idx, nxt> 按照nxt的小根堆
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((o1, o2) -> o1[1] - o1[1]);
        // nxt初始化为workerTimes[i]
        for(int i = 0;i < n;i++) minHeap.add(new int[]{i, workerTimes[i]});
        // cnt[i] 下标为i的工人降低了多少高度, 用于计算继续降低高度所增加的时间 = workerTimes[i] * (cnt[i] + 1)
        int[] cnt = new int[n];
        // 记录当前每个工人各自花费的时间
        int[] cur = new int[n];
        while(mountainHeight > 0){
            int[] poll = minHeap.poll();
            int idx = poll[0], nxt = poll[1];
            cnt[idx]++;
            nxt += workerTimes[idx] * (cnt[idx] + 1);
            minHeap.add(new int[]{idx, nxt});
            cur[idx] += workerTimes[idx] * cnt[idx];
            mountainHeight--;
        }
        int ret = 0;
        for(int x : cur) ret = Math.max(ret, x);
        return ret;
    }
}
