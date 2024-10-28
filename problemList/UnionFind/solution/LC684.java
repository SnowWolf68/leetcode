package problemList.UnionFind.solution;

/**
这题的关键在于题目中的这句话: 如果有多个答案，则返回数组 edges 中最后出现的那个。
因为对于多种可能的删除选择, 我们只需要保留edges中出现最晚的那条边, 因此我们只需要从前往后遍历edges数组
对于当前 e[0] -- e[1] 这条边, 首先判断 e[0] 和 e[1] 是否在同一个连通块中, 如果e[0] 和 e[1]已经在同一个连通块中, 那么显然当前 e[0] - e[1] 这条边就是要返回的答案
否则将e[0] 和 e[1]分别所在的这两个连通块合并为一个

时间复杂度的分析摘自 ylb 佬的题解: https://leetcode.cn/problems/redundant-connection/solutions/2966837/python3javacgotypescript-yi-ti-yi-jie-bi-3xsi/?envType=daily-question&envId=2024-10-28
时间复杂度 O(nα(n))，空间复杂度 O(n)。其中 n 为边的数量，而 α(n) 是阿克曼函数的反函数，可以认为是一个很小的常数。
 */
public class LC684 {
    public int[] findRedundantConnection(int[][] edges) {
        UnionFind uf = new UnionFind(edges.length + 1);
        for(int[] e : edges){
            if(!uf.isSameSet(e[0], e[1])){
                uf.union(e[0], e[1]);
            }else{
                return new int[]{e[0], e[1]};
            }
        }
        return null;    // 理论上不会走到这里
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

