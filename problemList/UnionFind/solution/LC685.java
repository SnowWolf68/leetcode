package problemList.UnionFind.solution;

import java.util.ArrayList;
import java.util.List;

/**
参考题解: https://leetcode.cn/problems/redundant-connection-ii/solutions/2968048/python3javacgotypescript-yi-ti-yi-jie-bi-yng0/?envType=daily-question&envId=2024-10-28

这题是LC684的变种, 题意都是相同的, 只不过这里将原本的无向图换成了有向图

为什么使用LC684的套路不行了? 可以看这个测试用例: edges = [[2,1],[3,1],[4,2],[1,4]]

应该怎么做?     考虑添加了一条有向边之后, 对原来的树有什么影响
根据添加的这一条边指向的节点的类型不同, 可以分为两种类型
    这里我们会根据节点的入度进行分析, 因此首先需要说一下, 对于题目中描述的正常符合要求的树, 其节点的入度会有什么样的关系
        1. 根节点的入度一定是0      2. 其余节点的入度一定是1
    1. 添加的有向边指向的节点不是根节点: 首先在这种情况下, 要删除的边只有一种可能, 即要删除的边只能是最后添加的这条边
        并且, 此时添加的这条有向边执行的节点的入度一定会变成2 (因为指向的节点不是根节点, 故原本就有1的入度, 此时添加了这条边之后, 又增加了1的入度)
        因此在这种情况下, 我们只需要找到树中入度为2的节点, 并且要删除的边的结束节点一定就是这个入度为2的节点
        那么问题又来了, 我们如何判断两条指向这个入度为2的节点的有向边中, 哪一条是要删除的边?
            其实也很简单, 我们只需要依次尝试删除这两条边, 并且判断删除之后的有向图还是否能够构成一棵树, 如果可以, 那么说明这条边就是答案
            否则, 另外的那一条边就是答案

            如何判断删除之后的有向图是否是一棵树?
            比较自然的想法就是从根节点跑一遍dfs, 如果能够遍历到所有的节点, 那么说明剩下的边可以构成一棵树, 否则不行
            需要注意的是, 在这种情况下, 根节点的入度一定是0, 因此我们可以在一开始计算所有节点的入度的时候, 就找出根节点, 因此这里进行dfs是可行的
    2. 添加的有向边指向的节点是根节点: 在这种情况下, 要删除的边有很多种可能, 因为在这种情况下, 添加了指向根节点的一条有向边之后, 会出现一个回路, 那么显然删除这个回路上的任意一条边都可以让剩下的部分变成一棵树
        那么在这种情况下, 显然就可以使用和LC684相同的方法来解决了

时间复杂度: O(n * 很小的常数)   第一种情况的处理中, 只需要一次O(n)的dfs即可, 第二中情况的处理的复杂度是O(n * 很小的常数)
 */
public class LC685 {
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;
        int[] in = new int[n];
        List<Integer>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        int idx = -1;           // 入度为2的节点下标
        int startNode = -1;     // 入度为2的节点其中一条入边的起始节点的下标
        for(int[] e : edges){
            int from = e[0] - 1, to = e[1] - 1;     // 节点编号从1开始, 这里 - 1 调整到从0开始
            g[from].add(to);
            in[to]++;
            if(in[to] == 2) {
                idx = to;
                startNode = from;
            }
        }
        if(idx != -1){
            // 第一种情况
            // 尝试删除 startNode -> idx 的这一条边, 看剩下的边能否构成一棵树
            int root = -1;
            for(int i = 0;i < n;i++) {
                if(in[i] == 0) {
                    root = i;
                    break;
                }
            }
            if(check(root, startNode, idx, g)){
                return new int[]{startNode + 1, idx + 1};       // 在建图的时候将节点编号 -1, 在返回的时候就需要加回去
            }else{
                // return: 另外一条指向idx的边
                for(int[] e : edges){
                    if(e[1] - 1 == idx){                        // 这里也别忘了 e[1] - 1 才是这里我们用到的点的下标
                        return new int[]{e[0], e[1]};           // 由于这里返回的直接是 e[0] 和 e[1] , 因此可以直接返回, 不需要 + 1
                    }
                }
            }
        }else{
            // 第二种情况   使用和LC684相同的方法解决
            UnionFind uf = new UnionFind(edges.length + 1);
            for(int[] e : edges){
                if(!uf.isSameSet(e[0], e[1])){
                    uf.union(e[0], e[1]);
                }else{
                    return new int[]{e[0], e[1]};       // 这里由于往并查集中添加节点的时候, 就是按照1 ~ n的编号添加的, 因此这里不需要 + 1
                }
            }
        }
        return null;    // 理论上不会走到这里
    }

    // 删除 startNode -> idx 的这一条边, 看剩下的边能否构成一棵树
    private boolean check(int root, int startNode, int idx, List<Integer>[] g){
        int n = g.length;
        boolean[] vis = new boolean[n];
        vis[root] = true;
        dfs(root, -1, startNode, idx, g, vis);
        for(boolean bool : vis){
            if(!bool) return false;
        }
        return true;
    }

    private void dfs(int i, int pa, int startNode, int idx, List<Integer>[] g, boolean[] vis){
        for(int nxt : g[i]){
            if(nxt != pa && !(i == startNode && nxt == idx)){
                vis[nxt] = true;
                dfs(nxt, i, startNode, idx, g, vis);
            }
        }
    }
}

class UnionFind {
    private int[] pa;
    private int[] size;     // size[i]表示以i为代表节点的连通块的大小, 合并的时候可以通过size判断两个连通块的大小, 让小的合并到大的上面

    UnionFind(int n){
        pa = new int[n];
        size = new int[n];
        for(int i = 0;i < n;i++){
            pa[i] = i;
            size[i] = 1;
        }
    }

    /**
     * 为了满足扁平化的要求, 在调用find(x)进行查询的过程中, 将沿途遇到的 所有节点 , 都直接挂到当前这个连通块的根节点上
     * 要想实现上面的这个需求, 我们可以利用递归的特性, 在 递 的过程中找当前这个连通块的代表节点, 在 归 的过程中, 将沿途走过的所有节点, 
     * 都直接挂到当前这个连通块的代表节点上
     * @param x
     * @return: x所在连通块的代表节点
     */ 
    public int find(int x){
        if(pa[x] != x){
            pa[x] = find(pa[x]);
        }
        return pa[x];
    }

    // return x和y是否在同一个连通块中
    public boolean isSameSet(int x, int y){
        return find(x) == find(y);
    }

    // 将x和y两个连通块合并
    public void union(int x, int y){
        int px = find(x), py = find(y);
        // 扁平化要求: 小挂大
        if(size[px] >= size[py]){
            pa[py] = px;
            size[px] += size[py];
        }else{
            pa[px] = py;
            size[py] += size[px];
        }
    }
}

