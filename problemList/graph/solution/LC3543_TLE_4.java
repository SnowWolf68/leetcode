package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
对于拓扑序dp的这种方法来说, 总的写法只能是这么写了, 那么想要优化时间复杂度的话只能考虑一些剪枝

灵神的方法: 由于dp[i][j][w]的第三个维度w存在很多无效位置, 因此考虑将第三个维度使用Set保存而不是数组
这样更新dp[i][j][w]的时候就只需要更新那些有效的w, 也可以看作是剪枝

同时, 由于这里用了Set, 因此存储的就不再是true/false, 而是需要存具体的w

有一个小细节, 第一种(注释掉的)初始化方式是错的, 原因如下(来自deepseek):
    问题本质：
        Arrays.fill() 会将数组的所有元素设置为同一个对象引用
        整个 dp[i] 行（所有 j）共享同一个 HashSet 实例
        当修改 dp[i][j1] 时，实际上修改的是所有 dp[i][*] 的状态
        导致状态污染：不同边数 j 的状态互相覆盖

但是这样还是T了
 */
public class LC3543_TLE_4 {
    public int maxWeight(int n, int[][] edges, int k, int t) {
        List<int[]>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        int[] in = new int[n];
        for(int[] e : edges){
            g[e[0]].add(new int[]{e[1], e[2]});
            in[e[1]]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0;i < n;i++){
            if(in[i] == 0) queue.offer(i);
        }
        // boolean[][][] dp = new boolean[n][k + 1][t];
        Set<Integer>[][] dp = new Set[n][k + 1];    // dp[i][j]存此时所有可能的w
        // for(int i = 0;i < n;i++) Arrays.fill(dp[i], new HashSet<>());    // 这种方式初始化就不对, 用下面这种方式就对, 很奇怪
        for(int i = 0;i < n;i++){
            for(int j = 0;j <= k;j++){
                dp[i][j] = new HashSet<>();
            }
        }
        for(int i = 0;i < n;i++) dp[i][0].add(0);
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            for(int[] nxtArr : g[poll]){
                int nxt = nxtArr[0], weight = nxtArr[1];
                for(int j = 1;j <= k;j++){
                    for(int w = 0;w < t;w++){
                        if(w - weight >= 0 && dp[poll][j - 1].contains(w - weight)) dp[nxt][j].add(w);
                    }
                }
                if(--in[nxt] == 0) queue.offer(nxt);
            }
        }
        int ret = -1;
        for(int i = 0;i < n;i++) {
            for(int r : dp[i][k]){
                ret = Math.max(ret, r);
            }
        }
        return ret;
    }
}
