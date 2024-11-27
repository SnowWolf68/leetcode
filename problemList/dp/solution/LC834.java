package problemList.dp.solution;

import java.util.ArrayList;
import java.util.List;

/**
换根dp的典型题

所谓换根dp, 即先假设某一个节点i为根, 此时计算出所有节点的某种信息, 然后对于所有和i相邻的这些节点
此时如果我们把根换到和i相邻的这些节点上, 如果此时对于换根之后的这棵树, 其所有节点的信息, 能够根据换根之前的信息, O(1)地计算得到
那么我们就可以使用换根dp来解决这种问题

对于本题来说, 对于示例1, 假设一开始我们选择以 0 节点为根节点, 那么我们此时计算出下标为 0 的节点, 和树中其余节点之间的距离和, 即: 
1 -> 0 的距离: 1
0 -> 0 的距离: 0
2 -> 0 的距离: 1
3 -> 0 的距离: 2
4 -> 0 的距离: 2
5 -> 0 的距离: 2
此时如果我们把根节点从0换到2, 我们看看这些距离会发生什么样的变化: 
1 -> 2 的距离: 1 + 1 = 2
0 -> 2 的距离: 0 + 1 = 1
2 -> 2 的距离: 1 - 1 = 0
3 -> 2 的距离: 2 - 1 = 1
4 -> 2 的距离: 2 - 1 = 1
5 -> 2 的距离: 2 - 1 = 1
不难发现, 对于以2为根的这棵子树中的这些节点, 其到2的距离, 等于其到0的距离 - 1
对于剩下的节点, 其到2的距离, 等于其到0的距离 + 1
因此如果我们得到了以0为根的树中, 所有节点到0的距离之和ret[0], 并且还知道了以2为根的这棵子树的大小size[2], 那么我们可以O(1)计算得到: 
以2为根的这棵子树中所有节点到2的距离之和ret[2] = ret[1] - size[2] + (n - size[2])

代码实现中, 我们可以首先进行一次dfs, 计算出来以0为根的树中, 所有节点到0的距离之和ret[0], 并且计算出每一棵子树的大小size[i]
然后再进行一次换根的dfs, 在这次dfs中, 依次将根节点从0换到其余的所有节点, 并且按照上面总结出来的规律, 计算ret[i]
 */
public class LC834 {
    private int[] ret, size;
    private List<Integer>[] g;
    private int n;
    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        this.n = n;
        ret = new int[n];
        size = new int[n];
        g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }
        dfs(0, -1, 0);
        reroot(0, -1);
        return ret;
    }

    // return: 以i为根节点的子树的大小
    private int dfs(int i, int pa, int depth){
        size[i] = 1;
        ret[0] += depth;
        for(int nxt : g[i]){
            if(nxt != pa){
                size[i] += dfs(nxt, i, depth + 1);
            }
        }
        return size[i];
    }

    // 开始换根
    private void reroot(int i, int pa){
        for(int nxt : g[i]){
            if(nxt != pa){
                // 从根节点为i, 换根到根节点为nxt
                ret[nxt] = ret[i] - size[nxt] + n - size[nxt];
                reroot(nxt, i);
            }
        }
    }
}
