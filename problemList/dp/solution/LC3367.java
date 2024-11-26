package problemList.dp.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
这题其实有点卡常, 我们如果把之前TLE的代码中 贪心选择的部分, 不再用一个单独的方法抽取出来做
而是和dfs的过程结合起来, 这样可以优化一点常数
这样就可以过了, 而且跑的非常快
 */
public class LC3367 {
    private List<int[]>[] g;
    private int k;
    public long maximizeSumOfWeights(int[][] edges, int k) {
        int n = edges.length + 1;
        this.k = k;
        g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        long sum = 0;
        for(int[] e : edges){
            g[e[0]].add(new int[]{e[1], e[2]});
            g[e[1]].add(new int[]{e[0], e[2]});
            sum += e[2];
        }
        boolean flag = true;
        for(List<int[]> nxList : g){
            if(nxList.size() > k) {
                flag = false;
                break;
            }
        }
        if(flag){
            return sum;
        }
        return dfs(0, -1)[0];
    }

    // return: i节点度数为k和k - 1时(nc, c), 子树的权重最大和
    private long[] dfs(int i, int pa){
        long[] ret = new long[2];
        List<Long> diff = new ArrayList<>();
        long retSum = 0;
        for(int[] nxtArr : g[i]){
            int nxt = nxtArr[0], weight = nxtArr[1];
            if(nxt != pa){
                long[] nxtRet = dfs(nxt, i);
                long nc = nxtRet[0], c = nxtRet[1] + weight;
                if(c - nc > 0) diff.add(c - nc);
                retSum += nc;
            }
        }
        Collections.sort(diff, (o1, o2) -> Long.compare(o2, o1));
        long[] sum = new long[diff.size() + 1];
        for(int j = 1;j <= diff.size();j++) sum[j] = sum[j - 1] + diff.get(j - 1);
        return new long[]{retSum + sum[Math.min(k, sum.length - 1)], retSum + sum[Math.min(k - 1, sum.length - 1)]};
    }
}
