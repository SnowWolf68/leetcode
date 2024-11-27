package problemList.dp.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
注意到: 假设初始的根节点为i, 那么如果将根节点从i换到i的子节点nxt中时, 注意到此时只有 i 和 nxt 的父子关系会发生变化, 其余节点的父子关系不会发生变化
因此可以使用换根dp求出: 以所有节点为根时, 分别猜对了多少次, 然后统计猜对次数 >= k 的根节点个数即为答案

还有一个小问题: 在第一次dfs中, 如何判断guesses中有多少个是猜对的?
需要注意的是, 这里guesses判断的是 父节点 的关系, 而不是 祖先节点 的关系, 这里一开始我还想复杂了
我们可以将 gusses 转换成一个哈希表 Map<childIdx, Set<parentIdx>> , 当每次遍历到一组 i -> nxt 时, 判断 i 是否在 nxt 对应的Set中即可
 */
public class LC2581 {
    private List<Integer>[] g;
    private int[] ans;  // ans[i] 表示以i为根时, guesses中有ans[i]个是正确的
    private Map<Integer, Set<Integer>> hashMap = new HashMap<>();   // Map<childIdx, Set<parentIdx>>
    public int rootCount(int[][] edges, int[][] guesses, int k) {
        int n = edges.length + 1;
        ans = new int[n];
        g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }
        for(int[] gues : guesses){
            int pa = gues[0], child = gues[1];
            Set<Integer> set = hashMap.getOrDefault(child, new HashSet<>());
            set.add(pa);
            hashMap.put(child, set);
        }
        // 计算以0为根时, 此时gusses中有多少个是正确的
        dfs(0, -1);
        reroot(0, -1);
        int cnt = 0;
        for(int x : ans){
            if(x >= k) cnt++;
        }
        return cnt;
    }

    private void dfs(int i, int pa){
        for(int nxt : g[i]){
            if(nxt != pa){
                if(hashMap.getOrDefault(nxt, new HashSet<>()).contains(i)) ans[0]++;
                dfs(nxt, i);
            }
        }
    }

    // 换根dp, 计算ans[i]
    private void reroot(int i, int pa){
        for(int nxt : g[i]){
            if(nxt != pa){
                // 将根节点从i换到nxt
                if(hashMap.getOrDefault(nxt, new HashSet<>()).contains(i) && !hashMap.getOrDefault(i, new HashSet<>()).contains(nxt)) ans[nxt] = ans[i] - 1;
                else if(!hashMap.getOrDefault(nxt, new HashSet<>()).contains(i) && hashMap.getOrDefault(i, new HashSet<>()).contains(nxt)) ans[nxt] = ans[i] + 1;
                else ans[nxt] = ans[i];
                reroot(nxt, i);
            }
        }
    }
}
