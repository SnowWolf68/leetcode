package problemList.dp.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
距离第一次做这个题已经过去了将近一年了, 现在再来做这个换根dp, 又有一些新的理解
因此这里我就写一下这次我做的时候的思路: 

我们可以看示例2: 
    假设这棵树原本的根节点是下标为3的节点, 如果我们将根节点换到了下标为4的节点, 此时如何计算以4为根节点的树的高度
    换根之后, 以4为根的树的高度有以下几种可能: 
        1. 如果以3为根的树中, 取得最大高度时, 最大高度所在路径中, 3的下一个节点编号是4, 那么此时换根后的最大高度为: max(以3为根时的次大高度 + 1, 以3为根时 4这棵子树的最大高度)
        2. 如果以3为根的树中, 取得最大高度时, 最大高度所在路径中, 3的下一个节点编号不是4, 那么换根后的最大高度为: max(以3为根时的最大高度 + 1, 以3为根时 4这棵子树的最大高度)
        需要注意的是, 在上面的分析中, 如果对于以3为根的树中, 此时有多个相同的最大高度, 那么我们此时会将多出来的最大高度, 更新到次大高度中
        这也是上面的这种方式能够正确更新的前提
    可以发现: 要想将根从 i 换到 nxt , 此时需要记录以下几个信息: 
        1. 以i为根时的最大高度
        2. 以i为根时, 取得最大高度时, 最大高度所在路径中, i的下一个节点编号
        3. 以i为根时的次大高度
因此通过上面的分析可以知道, 如果我们知道了 以i为根节点时, 最大高度, 最大高度对应的下一个节点, 次大高度, 这三个信息, 就能够O(1)地计算出, 以i的某一个子节点nxt为根节点时的最大高度
正因如此, 我们可以使用换根dp来计算以每个节点为根节点时, 此时的树高, 然后遍历所有的树高, 就可以得到答案

这里还需要强调一些关于换根dp的细节: 
    需要注意的是, 在换根的过程中 (从i为根节点换到以nxt为根节点) , 实际上我们要计算的并不只是 以 nxt 为根节点的树的最大树高 这一个信息
    因为在换根到nxt之后, 显然我们还要继续换根到nxt的子节点, 那么在从nxt换根到nxt的子节点时, 还需要依赖nxt节点对应的上面说过的三个信息
    因此这里换根的过程, 实际上是: 利用i为根节点时的三个信息(最大高度, 最大高度对应的下一个节点编号, 次大高度) 换根到nxt时, 计算出 nxt 对应的这三个信息
    这里的计算过程并不复杂, 这里不再赘述, 可以看代码中reroot()部分的实现

并且需要注意的是, 由于我们每次都是 利用 i的三个信息 计算 其子节点 nxt 的三个信息, 因此在第一次dfs的时候, 我们只需要计算出 以 0 为根节点的树对应的上述三个信息即可

通过上面的换根过程我们可以知道, 在换根结束时, 我们就得到了以每一个节点为根节点时, 此时的 最大高度, 最大高度对应的下一个节点编号, 次大高度
因此我们只需要统计此时所有的最大高度, 就可以得到答案

 */
public class LC310 {
    private List<Integer>[] g;
    private int[] first, second, nextIdx;   // 最大, 次大, 最大的下一个节点编号
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
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
        int minHeight = n;
        List<Integer> ret = new ArrayList<>();
        for(int i = 0;i < n;i++){
            if(first[i] < minHeight){
                minHeight = first[i];
                ret.clear();
                ret.add(i);
            }else if(first[i] == minHeight){
                ret.add(i);
            }
        }
        return ret;
    }

    private void dfs(int i, int pa){
        for(int nxt : g[i]){
            if(nxt != pa){
                dfs(nxt, i);
            }
        }
        for(int nxt : g[i]){
            if(nxt != pa){
                if(first[nxt] + 1 == first[i]){
                    second[i] = first[nxt] + 1;
                }else if(first[nxt] + 1 > first[i]){
                    second[i] = first[i];
                    first[i] = first[nxt] + 1;
                    nextIdx[i] = nxt;
                }else if(first[nxt] + 1 > second[i]){
                    second[i] = first[nxt] + 1;
                }else if(second[nxt] + 1 > second[i]){
                    second[i] = second[nxt] + 1;
                }
            }
        }
    }

    // 换根计算出height[]数组
    private void reroot(int i, int pa){
        for(int nxt : g[i]){
            if(nxt != pa){
                // 从i换到nxt
                if(nextIdx[i] == nxt){
                    if(second[i] + 1 > first[nxt]){
                        first[nxt] = second[i] + 1;
                        nextIdx[nxt] = i;
                    }else if(second[i] + 1 > second[nxt]){
                        second[nxt] = second[i] + 1;
                    }
                }else{
                    if(first[i] + 1 > first[nxt]){
                        first[nxt] = first[i] + 1;
                        nextIdx[nxt] = i;
                    }else if(first[i] + 1 > second[nxt]){
                        second[nxt] = first[i] + 1;
                    }
                }
                reroot(nxt, i);
            }
        }
    }
}
