package problemList.dp.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
这题和 LC310 最小高度树 很类似, 只不过这里每一条边的长度不再是1, 而是根据节点编号动态变化

题意翻译: 返回一个数组times[], 其中times[i]表示在以i为根节点的树中, 从i(根节点)走到叶子结点的最远距离
    需要注意的是, 这里之所以是 "最远距离" 而不是 "树高" , 是因为这里边的长度会动态变化, 具体来说: 
    对于 i -- nxt 这条无向边来说: 
        1. 如果 nxt 是奇数, 那么 i -- nxt 这条边的边长为1
        2. 如果 nxt 是偶数, 那么 i -- nxt 这条边的边长为2

因此实际上这题就是 边长可变 的 最小高度树 (LC310)

通过LC310的分析我们可以知道, 对于某一个节点i, 我们需要维护三个信息: 最大高度, 取得最大高度时下一个节点编号, 次大高度
那么转化到本题而言, 对应就是: 最长路径, 取得最长路径时下一个节点的编号, 次长路径

 */
public class LC3241 {
    private List<Integer>[] g;
    private int[] first, second, nextIdx;
    public int[] timeTaken(int[][] edges) {
        int n = edges.length + 1;
        g = new List[n];
        first = new int[n];
        second = new int[n];
        nextIdx = new int[n];
        Arrays.fill(nextIdx, -1);
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }
        dfs(0, -1);
        reroot(0, -1);
        return first;
    }

    private void dfs(int i, int pa){
        for(int nxt : g[i]){
            if(nxt != pa){
                dfs(nxt, i);
            }
        }
        for(int nxt : g[i]){
            if(nxt != pa){
                int curLen = nxt % 2 == 0 ? 2 : 1;
                if(first[nxt] + curLen > first[i]){
                    second[i] = first[i];
                    first[i] = first[nxt] + curLen;
                    nextIdx[i] = nxt;
                }else if(first[nxt] + curLen > second[i]){
                    second[i] = first[nxt] + curLen;
                }
            }
        }
    }

    private void reroot(int i, int pa){
        for(int nxt : g[i]){
            if(nxt != pa){
                // 换根: 从i换到nxt
                int curLen = i % 2 == 0 ? 2 : 1;
                if(nextIdx[i] != nxt){
                    if(first[i] + curLen > first[nxt]){
                        second[nxt] = first[nxt];
                        first[nxt] = first[i] + curLen;
                        nextIdx[nxt] = i;
                    }else{
                        if(first[i] + curLen > second[nxt]){
                            second[nxt] = first[i] + curLen;
                        }else if(second[i] + curLen > second[nxt]){
                            second[nxt] = second[i] + curLen;
                        }
                    }
                }else{
                    if(second[i] + curLen > first[nxt]){
                        second[nxt] = first[nxt];
                        first[nxt] = second[i] + curLen;
                        nextIdx[nxt] = i;
                    }else{
                        if(second[i] + curLen > second[nxt]){
                            second[nxt] = second[i] + curLen;
                        }
                    }
                }
                reroot(nxt, i);
            }
        }
    }
}
