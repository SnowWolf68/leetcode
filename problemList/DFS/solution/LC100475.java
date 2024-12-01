package problemList.DFS.solution;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.cn/problems/maximize-the-number-of-target-nodes-after-connecting-trees-i/description/

/**
假设 cnt2[i] 表示第二棵树中, 节点i的距离不超过k - 1目标节点数量, 
那么对于第一棵树中的每一个节点j来说, 此时显然让j连到max(cnt2[i])对应的节点上是最优的
换句话说, 假设cnt1[j]表示第一棵树中, 节点j的目标节点数量, 并且有 maxCnt2 = max(cnt2[i])
那么ans[j] = cnt1[j] + maxCnt2;

要计算cnt1[]和cnt2[], 只需要对这两棵树中的每一个节点分别进行一次dfs即可
总的时间复杂度: O(n ^ 2 + m ^ 2), 本题的数据范围: 2 <= n, m <= 1000, 这个复杂度显然不会超时
 */
public class LC100475 {
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2, int k) {
        List<Integer>[] g1 = buildTree(edges1);
        List<Integer>[] g2 = buildTree(edges2);
        int n = edges1.length + 1, m = edges2.length + 1;
        int[] cnt1 = new int[n], ans = new int[n];
        int maxCnt2 = 0;
        for(int i = 0;i < m;i++) maxCnt2 = Math.max(maxCnt2, dfs(g2, i, -1, k - 1, 0));
        for(int i = 0;i < n;i++) ans[i] = dfs(g1, i, -1, k, 0) + maxCnt2;
        return ans;
    }

    private List<Integer>[] buildTree(int[][] edges){
        int n = edges.length + 1;
        List<Integer>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }
        return g;
    }

    // return: 到节点i的距离不超过k的节点个数
    private int dfs(List<Integer>[] g, int i, int pa, int k, int d){
        int cnt = 1;
        for(int nxt : g[i]){
            if(nxt != pa){
                if(d + 1 <= k) cnt += dfs(g, nxt, i, k, d + 1);
            }
        }
        return d <= k ? cnt : 0;
    }
}
