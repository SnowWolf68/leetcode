package lanqiao.B14th;

import java.util.Scanner;

public class D {
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
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
    }
}
