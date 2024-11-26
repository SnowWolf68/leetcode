package problemList.dp.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
树形DP
在做树形DP时, 一定要把握住一个核心: 树形DP 就是 从子节点到父节点 的状态转移

对于普通dp, dp[i]是状态表示, 但是对于树形dp, 这里的下标i就换成了节点的编号, 即 dp[i] --(转换为)--> 对于节点i, 此时的某些信息
普通dp的状态转移一般来说, 是从 dp[i - 1] 转移到 dp[i], 但是对于树形dp, 这里的状态转移就变成了: 从子节点到父节点的状态转移
即我们需要利用子节点的信息, 来计算父节点的信息
    -- 通过状态转移的分析也可以看出来, 对于树形dp, 一般我们都可以使用 递归 来实现状态转移的过程

对于本题来说, 如何进行树形DP?

1. 为什么本题是一个树形DP? 
    灵神的视频讲解中讲到了一种很重要的分析方法: 从特殊到一般
    先考虑本题在特殊情况下是一个什么问题, 再推广到一般情况, 进而得出解法
    本题的特殊情况: 树的特殊情况就是一条链表, 并且此时我们让k也取到特殊情况1, 因此对应的情况就是: 在一条链表中, 删除一些边, 并且保证每一个节点的度数不超过1, 求剩余边的权重的最大可能和
    在这种情况下, 可以看作是: 从链表中选择一些边, 保证选择的边不连续, 求选择的边的权重的最大可能和
    这显然就是 打家劫舍 问题, 需要用dp解决

    对于特殊情况而言, 此时是一个线性的dp, 那么对于普通情况而言(在树上的情况), 那么此时这就应该是一个 树上dp (树上打家劫舍) 问题

2. 本题的树形dp如何分析?
    在开头我也提到了, 对于树形dp, 其关键的转移过程是: 利用子节点的信息 计算 父节点的信息
    而从 特殊问题 的分析中我们可以知道, 本题是一个 打家劫舍 的 dp 问题, 对于打家劫舍的dp做法, 我们是从 选或不选 出发考虑

    既然是dp, 那么我们就从dp最基本的思路: 寻找重复子问题 开始分析
    如何寻找重复子问题?
    假设我们想要求以i为根节点的子树, 其删除后的最大边权和, 那么我们可以遍历i节点的所有子节点nxt, 此时有两种情况: 选 i -> nxt 这条边, 不选 i -> nxt 这条边
    如果选 i -> nxt 这条边, 此时问题转化为: 在nxt为根的子树中, 要求nxt的度不超过k - 1, 此时子树中删除后的所有边的最大权值和
    如果不选 i -> nxt 这条边, 此时问题转化为: 在nxt为根的子树中, 要求nxt的度不超过k, 此时子树中删除后的所有边的最大权值和

    显然 "nxt度数为 k, k - 1 时, 此时nxt子树中删除后的最大权值和" 就是重复子问题, 因此我们可以有如下的状态表示: 
        (也可以类比 数组上的打家劫舍问题, 定义出树上的状态表示)
    对于某一个节点i, 其状态包括两个信息: nc(not choose), c(choose)
    其中 nc 表示 不选择 pa -> i 这条边(也就是i的度数不超过k), 此时i这个子树中边权的最大可能和
         c表示 选择 pa -> i 这条边(也就是i的度数不超过k - 1), 此时i这个子树中边权的最大可能和 (pa: i的父节点)

    状态转移方程: 
        在树形dp中, 状态转移方程即为: 对于某一个节点i, 使用其子节点的信息, 计算出i的信息 (这里的信息指的就是 i 节点的 nc, c 值)
        1. nc值的计算: 
            nc代表的是 不选 pa -> i 这条边, 那么此时对于i来说, 其最多可以选择 k 条和其子节点nxt相连的边
            由于对于每一个子节点nxt来说, 其都有nc, c两种状态, 因此这里的选择操作实际上就是: 
                假设i的子节点nxt有cnt个, 那么我们把所有子节点nxt的nc, c值组成两个数组 nxtNc[cnt], nxtC[cnt]
                此时的选择操作即为: 从nxtNc[], nxtC[]中选择, 要求: 
                    1) 对于同一个下标j, nxtNc[j] 和 nxtC[j] 必须选择一个, 并且只能选择一个
                    2) nxtC[j] 最多选择k个
            那么我们怎么进行上面的这个选择操作?  -- 反悔贪心
            由于对于nxtC[j]的选择有限制, 最多就是选k个, 因此这里我们不妨假设, 一开始全都选的是nxtC[j], 然后在 "反悔" , 即再撤销一部分的选择
            那么我们应该撤销哪部分的选择?
            由于 nxtNc[j] 和 nxtC[j] 必须且只能选一个, 因此这里的撤销 nxtC[] 实际上就是 从选 nxtC[j] 变为 选择 nxtNc[j]
            因此贪心地想: 既然必须要撤销一部分操作, 那么如果我们应该让 "撤销" 操作带来的 "增量" 尽可能的大
            即我们应该选择 nxtNc[j] - nxtC[j] 最大的那些j来撤销

        2. c值的计算: 
            c代表的是 选 pa -> i 这条边, 那么此时i就最多可以选择 k - 1 条和其子节点nxt相连的边
            那么这里实际上和上面那种情况是一样的做法, 只不过这里将 选k个 改成了选 k - 1 个
    

 */
public class LC3367 {
    private List<int[]>[] g;
    private int k;
    public long maximizeSumOfWeights(int[][] edges, int k) {
        int n = edges.length + 1;
        this.k = k;
        g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0]].add(new int[]{e[1], e[2]});
            g[e[1]].add(new int[]{e[0], e[2]});
        }
        return dfs(0, -1)[0];
    }

    // return: i节点度数为k和k - 1时(nc, c), 子树的权重最大和
    private int[] dfs(int i, int pa){
        int[] ret = new int[2];
        List<Integer> nc = new ArrayList<>(), c = new ArrayList<>();    // nc: not choose, c: choose
        for(int[] nxtArr : g[i]){
            int nxt = nxtArr[0], weight = nxtArr[1];
            if(nxt != pa){
                nc.add(dfs(nxt, i)[0]);
                c.add(dfs(nxt, i)[1] + weight);
            }
        }
        // ret[0] = choose(c, nc, k);
        // ret[1] = choose(c, nc, k - 1);
        ret[0] = choose1(c, nc, k);
        ret[1] = choose1(c, nc, k - 1);
        // System.out.println("i = " + i + " nc = " + ret[0] + " c = " + ret[0]);
        return ret;
    }

    // 贪心选择
    private int choose(List<Integer> c, List<Integer> nc, int k){
        // [从c[i]到nc[i]的增量, i]
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for(int i = 0;i < c.size();i++) maxHeap.offer(c.get(i) - nc.get(i));
        int chooseCnt = Math.min(k, c.size()), ret = 0;
        for(int x : nc) ret += x;
        while(chooseCnt > 0){
            if(maxHeap.peek() <= 0) break;
            ret += maxHeap.poll();
            chooseCnt--;
        }
        // System.out.println("c = " + c.toString() + " nc = " + nc.toString() + " k = " + k + " ret = " + ret);
        return ret;
    }

    // 贪心选择
    private int choose1(List<Integer> c, List<Integer> nc, int k){
        // [从c[i]到nc[i]的增量, i]
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for(int i = 0;i < c.size();i++) maxHeap.offer(nc.get(i) - c.get(i));
        int chooseCnt = c.size() - k, ret = 0;
        for(int x : c) ret += x;
        while(chooseCnt > 0){
            ret += maxHeap.poll();
            chooseCnt--;
        }
        // 可以多撤销几个
        while(!maxHeap.isEmpty() && maxHeap.peek() >= 0) ret += maxHeap.poll();
        return ret;
    }

    public static void main(String[] args) {
        int[][] edges = new int[][]{{0,1,4},{0,2,2},{2,3,12},{2,4,6}};
        int k = 2;
        System.out.println(new LC3367().maximizeSumOfWeights(edges, k));
    }
}
