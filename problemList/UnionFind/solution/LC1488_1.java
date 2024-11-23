package problemList.UnionFind.solution;

import java.util.HashMap;
import java.util.Map;

/**
这题并查集的思路其实并不好想

首先我们从头来分析一下这题的思路
我们可以在遍历的同时使用一个HashMap来记录 当前湖泊上一次下雨的时间
    为什么不用数组而要用HashMap?
        因为本题的数据范围: 1 <= rains.length <= 1e5, 0 <= rains[i] <= 1e9
        也就是说, 湖泊的编号范围可以到 1e9 , 因此数组显然存不下, 因此需要用HashMap
怎么选择在哪一个晴天放哪一个湖泊的水?
    我们可以先把所有的晴天的下标都保存起来, 在每次遍历到某一个rains[i]时, 如果rains[i] != 0, 说明在i这个时间, rains[i]这个湖泊会下雨
    这里不妨假设rains[i]这个湖泊上一次下雨的时间是hashMap.get(rains[i]), 那么我们可以贪心地选择要放水的日期:
    假设所有晴天的下标都保存在set这个有序集合中, 并且按照时间升序排序
    那么我们可以从set中二分查找第一个 > hashMap.get(rains[i]) 的日期, 并且在这天放水
        ps: 为什么这样的贪心策略是正确的?   
            这里的贪心策略为: 选择第一个符合要求的晴天放水
            如果想证明这个贪心策略的正确性, 可以用反证法, 即如果选择不是第一个符合要求的晴天放水
            那么显然我们可以分析出来, 此时都有可能不是最优的选择, 这里的证明不难, 自己想想即可, 不再赘述
因此, 通过上面的分析, 我们就得到了第一种解法: 有序集合二分, 时间复杂度: O(n * logn)

第二种做法: 使用并查集
由于对于并查集来说, 如果使用了扁平化(路径压缩), 那么当查询find()操作数量较多时, 单次查询的时间复杂度可以看作是 O(1)
因此使用并查集之后, 可以将时间复杂度优化到 O(n)

如何使用并查集实现 上面的过程?      其实这种并查集的做法不是很好想, 但是这种做法其实还是很巧妙的
首先我们可以把连续的下雨天(rains[i] != 0的下标)连起来, 放在一个连通块中
那么, 两个相邻的连通块之间的下标(时间), 就是晴天的下标

这里的并查集我们还是使用 类似 数组上的并查集 这种的合并思路, 即每次都向右合并
需要注意的是, 在本题的情境下, 按照上面这种方式实现的并查集, 其某一个连通块的根节点是: 当前这一串连续的雨天右侧的第一个晴天的下标

具体来说: 每次遍历到一个rains[i] != 0的天(下雨天), 我们从HashMap中找到rains[i]这个湖泊上一次下雨的时间hashMap.get(rains[i])
然后我们找到hashMap.get(rains[i])所在的连通块的根节点, 那么这个根节点就是 当前这一串下雨天右侧的第一个晴天
可以发现, 上述的根节点, 就是之前我们按照贪心策略想要找的这个晴天
如果这个晴天的下标 >= 当前下标i, 说明不存在这样的晴天, 也就是没办法阻止洪水, 因此返回空数组
反之, 我们可以置 ret[] 中对应的下标为 当前湖泊的编号 rains[i], 并且将这个晴天添加到其附近相邻的并查集中 (向右合并)

并且如果当前rains[i] != 0, 那么也需要将当前下标向右合并

最后需要注意的是, 由于题目中要求, 只要rains[i] == 0, 就要选择一个要抽干的湖泊编号, 但是实际上有可能并不需要在每一个晴天都抽水
因此我们可以在按照上面计算完成之后, 再遍历一遍ret, 如果某一个ret[i] == 0, 说明i这一个晴天不需要抽任何湖泊的水
为了符合题目要求, 我们可以选择任何一个湖泊抽水 (题目要求对于没有水的湖泊抽水, 此时无事发生), 这里我们不妨选择编号最小的湖泊, 即1号湖泊, 即将ret[i]置为1
 */
public class LC1488_1 {
    class UnionFind {
        public int[] pa;
    
        UnionFind(int n){
            pa = new int[n];
            for(int i = 0;i < n;i++){
                pa[i] = i;
            }
        }

        public int find(int x){
            if(pa[x] != x){
                pa[x] = find(pa[x]);
            }
            return pa[x];
        }
    }

    public int[] avoidFlood(int[] rains) {
        int n = rains.length;
        UnionFind uf = new UnionFind(n + 1);
        int[] ret = new int[n];
        // <湖编号, 上一次下雨的时间>
        Map<Integer, Integer> hashMap = new HashMap<>();
        for(int i = 0;i < n;i++){
            if(rains[i] != 0){
                ret[i] = -1;
                if(hashMap.containsKey(rains[i])){
                    int fa = uf.find(hashMap.get(rains[i]));
                    if(fa >= i) return new int[]{};
                    else{
                        ret[fa] = rains[i];
                        uf.pa[fa] = uf.find(fa + 1);
                    }
                    
                }
                int fa = uf.find(i + 1);    // 这里也可以改成 int fa = i + 1; 因为对于本题中的过程来说, 显然此时 find(i + 1) = i + 1
                uf.pa[i] = fa;
                hashMap.put(rains[i], i);
            }
        }
        for(int i = 0;i < n;i++){
            if(ret[i] == 0) ret[i] = 1;
        }
        return ret;
    }
}
