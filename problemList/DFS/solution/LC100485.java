package problemList.DFS.solution;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.cn/problems/maximize-the-number-of-target-nodes-after-connecting-trees-ii/

/**
首先看看距离为 偶数 和 奇数 的目标节点有什么性质:
    对于树2来说, 假设其中的某个节点i, 到i的边数为偶数的节点个数为 cnt1[i], 到i的边数为奇数的节点个数为 cnt2[i]
    那么我们注意到, 可以将 cnt1[i] 和 cnt2[i] 转化为: 到节点0的边数为偶数(cnt1[0])/奇数(cnt2[0])的节点的个数 上
    即: 如果节点i到节点0之间的边数为偶数, 那么 cnt1[i] = cnt1[0], cnt2[i] = cnt2[0]
        如果节点i到节点0之间的边数为奇数, 那么 cnt1[i] = cnt2[0], cnt2[i] = cnt1[0]

通过上面的分析可以发现, 如果我们想要找树2中, 到节点i的边数为奇数/偶数的目标节点数量, 实际上就是找 到节点0的边数为奇数/偶数的目标节点数量

再分析一下对于树1中的某个节点j, 此时我们需要将树2中的哪个节点连到树1的哪个节点, 能够让树1中的节点j的目标节点数量最多?
还是利用这里 边数为奇数/偶数 的性质, 此时如果树2中 到节点0的边数为偶数的目标节点数量cnt1[0] > 到节点0的边数为奇数的目标节点数量cnt2[0]
那么我们可以让树2中的0节点, 连接到树1中 距离节点j的边数为偶数的节点上, 这样对于树1的j节点来说, 其目标节点的增量为 cnt1[0]
类似的, 如果树2中 cnt1[0] < cnt2[0], 那么我们此时应该让树2的0节点, 连接到树1中 距离节点j的边数为奇数的节点上, 这样j节点的目标节点的数量的增量为 cnt2[0]

而对于树1中的节点j来说, 到节点j的边数为奇数/偶数的目标节点数量, 又可以转化为 到0节点的边数为奇数/偶数的目标节点数量
因此: 
    假设树1中, 到节点0的边数为奇数的节点数量为cnt10, 到节点0的边数为偶数的节点数量为cnt11
        树2中, 到节点0的边数为奇数的节点数量为cnt20, 到节点0的边数为偶数的节点数量为cnt21, 并且有maxCnt2 = max(cnt20, cnt21)
    显然上面 cnt10, cnt11, cnt20, cnt21 这四个值, 可以通过分别对 树1 和 树2 进行一次dfs计算得到
    并且由于我们还需要知道树1中, 节点j到节点0之间的边数是奇数还是偶数, 因此还需要预处理出一个数组 cnt[], 其中 cnt[j] 表示在树1中, 下标为j的节点, 到下标为0的节点之间的边数是奇数还是偶数
    cnt[j] = 1 表示j到0的边数为奇数, cnt[j] = 0 表示j到0的边数为偶数, 显然这个cnt[]数组, 也可以通过对树1进行一次dfs得到
有了上面这些信息, 我们就可以计算 ans[i]: 
ans[i] = 
    1. cnt[i] == 1 (i到0的边数为奇数): cnt10 + maxCnt2
    2. cnt[i] == 0 (i到0的边数为偶数): cnt11 + maxCnt2
 */
public class LC100485 {
    private int[] cnt;
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2) {
        int n = edges1.length + 1;
        cnt = new int[n];
        List<Integer>[] g1 = buildTree(edges1);
        List<Integer>[] g2 = buildTree(edges2);
        dfs2(g1, 0, -1, 0);
        int[] ret1 = dfs1(g1, 0, -1);
        int[] ret2 = dfs1(g2, 0, -1);
        int cnt10 = ret1[0], cnt11 = ret1[1], cnt20 = ret2[0], cnt21 = ret2[1], maxCnt2 = Math.max(cnt20, cnt21);
        int[] ans = new int[n];
        for(int i = 0;i < n;i++){
            ans[i] = cnt[i] == 1 ? cnt10 + maxCnt2 : cnt11 + maxCnt2;
        }
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

    // return: (i的子树中) [到0节点的边数为奇数的节点数量, 到0节点的边数为偶数的节点数量]
    private int[] dfs1(List<Integer>[] g, int i, int pa){
        int[] ret = new int[2];
        ret[1] = 1;
        for(int nxt : g[i]){
            if(nxt != pa){
                int[] nxtRet = dfs1(g, nxt, i);
                ret[0] += nxtRet[1];
                ret[1] += nxtRet[0];
            }
        }
        return ret;
    }

    // 计算cnt[]数组, 其中cnt[i]表示: 树1中, 节点i到节点0的边数是奇数还是偶数
    private void dfs2(List<Integer>[] g, int i, int pa, int len){
        cnt[i] = len % 2;
        for(int nxt : g[i]){
            if(nxt != pa){
                dfs2(g, nxt, i, len + 1);
            }
        }
    }
}
