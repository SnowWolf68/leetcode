package solution;

import java.util.Arrays;

/**
时间复杂度要求O(n ^ 2), 而如果使用map, set来查询一个字符串, 那么计算hashCode()的复杂度是O(n)的, 即复杂度为字符串的长度, 这显然不行
因此我们可以使用字典树来让这个查询过程优化到O(1)

由于字典树查询一个字符串都是从前往后进行查询, 也就是说, 每次查询的都是一个 "前缀"
因此为了让dp中 "枚举最后一个划分的字符串的起始位置" 和 字典树的查询过程 相匹配, 我们需要 倒序dp
这样, 我们就能够在遍历划分出来的这个字符串的结束位置j的同时, 得到这个字符串对应的编号, 得到编号的时间复杂度是O(1)的

定义dp[i] 表示将source[i, n - 1]区间的字符串变成target[i, n - 1]区间的字符串, 此时所需要的最小代价
    dp[i]: 注意这题还有不替换的可能
        1. 不替换: 前提source[i] == target[i], 那么dp[i] = dp[i + 1];
        2. 替换: 
            枚举最后一个划分出来的字符串的起始下标j, 那么最后一个字符串的范围就是[i, j]
            假设cost[i][j]表示将source[i, j]区间的子串变成target[i, j]区间的子串的最小代价, 那么有以下的状态转移方程
            dp[i] = dp[j + 1] + cost[i][j];
    对于所有可能的j, 我们只需要让dp[i]取一个min即可

初始化: 这里j + 1有可能越界, 我们需要在dp表最后添加一个辅助节点, 这个辅助节点的值显然应该初始化为0
return dp[0];

由于这里original和changed之间可以进行多次转化, 因此我们需要使用floyd来求两个字符串相互转换所需要的最小花费

这题最妙的地方在于, 利用字典树每次查询前缀的功能, 在O(1)的时间内, 得到一个字符串对应的编号
 */
public class LC2977 {
    private Node root = new Node();
    private int sid = 0;
    class Node{
        Node[] son = new Node[26];
        // 节点编号, 默认初始化为-1, 意味着此时没有对当前节点编号, 即此时当前节点不是某个子串的结束位置
        int sid = -1;
    }
    /**
     * 向字典树中添加一个字符串
     * @param str
     * @return 字符串的编号sid
     */
    private int put(String str){
        Node cur = root;
        for(char ch : str.toCharArray()){
            if(cur.son[ch - 'a'] == null) cur.son[ch - 'a'] = new Node();
            cur = cur.son[ch - 'a'];
        }
        // 由于当前这个str字符串有可能在之前出现过, 因此这里需要对cur.sid进行判断, 当cur.sid >= 0时, 再赋值
        if(cur.sid < 0) cur.sid = sid++;
        return cur.sid;
    }
    public long minimumCost(String source, String target, String[] original, String[] changed, int[] cost) {
        int n = original.length, m = source.length();
        long INF = Long.MAX_VALUE / 2;
        // dist[i][j]中的i, j都表示字符串的编号sid, 由于最多就2 * n个字符串, 因此编号的范围是[0, 2 * n - 1]
        long[][] dist = new long[2 * n][2 * n];
        for(int i = 0;i < 2 * n;i++){
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }
        for(int i = 0;i < n;i++){
            int x = put(original[i]);
            int y = put(changed[i]);
            // 这里需要取min的原因是, 有可能dist[x][y]在这之前被更新过了
            dist[x][y] = Math.min(dist[x][y], cost[i]);
        }
        // floyd
        for(int k = 0;k < sid;k++){
            for(int i = 0;i < sid;i++){
                for(int j = 0;j < sid;j++){
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
        // dp
        long[] dp = new long[m + 1];
        for(int i = m - 1;i >= 0;i--){
            dp[i] = INF;
            if(source.charAt(i) == target.charAt(i)) dp[i] = Math.min(dp[i], dp[i + 1]);
            // p代表source中的字符串, q代表target中的字符串
            Node p = root, q = root;
            for(int j = i;j < m;j++){
                p = p.son[source.charAt(j) - 'a'];
                q = q.son[target.charAt(j) - 'a'];
                if(p == null || q == null) break;
                if(p.sid == -1 || q.sid == -1) continue;
                dp[i] = Math.min(dp[i], dp[j + 1] + dist[p.sid][q.sid]);
            }
        }
        // System.out.println(Arrays.toString(dp));
        return dp[0] == INF ? -1 : dp[0];
    }
}
