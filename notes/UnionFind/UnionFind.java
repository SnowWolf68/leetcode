package notes.UnionFind;

/**
并查集的时间复杂度: 在进行操作次数较多的情况下, 并查集单次操作的时间复杂度可以认为是O(1)

通俗理解为什么在find操作较多时, 平均时间复杂度可以看作是O(1):
    关键在于这里的find()方法中的实现, 由于进行find()的过程中, 我们会在找parent的过程中, 使用递归, 将沿途遍历过的所有节点全都挂到最终的parent上
    因此对于沿途的这些节点的后续find()来说, 其只需要递归一次即可找到parent, 即对于这些沿途的节点, 后续的find()均是O(1)的

    换句话说, 如果有一条链很长, 那么对于这个长链, 我们只会遍历一次, 是线性的复杂度, 但是在遍历一次之后, 对于这条链上的所有点, 后续的find()操作
        的时间复杂度都只需要O(1)即可
    需要注意的是, 要想能够让并查集有上面的这个效果, 我们必须要在find()中进行扁平化操作

时间复杂度的分析摘自 ylb 佬的题解: https://leetcode.cn/problems/redundant-connection/solutions/2966837/python3javacgotypescript-yi-ti-yi-jie-bi-3xsi/?envType=daily-question&envId=2024-10-28
并查集的时间复杂度应该是 O(α(n)), 其中 n 为边的数量，而 α(n) 是阿克曼函数的反函数，可以认为是一个很小的常数。
 */
public class UnionFind {
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
