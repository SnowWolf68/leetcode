package problemList.dp.solution;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
之前的代码会T的根本原因在于dp的时间复杂度过高, 到了O(n ^ 3)的级别
而dp的时间复杂度高的原因, 在于每次计算dp[i]的时候, 都要遍历所有的j, 并且每次取子串的substring()操作还是一个O(n)的操作

在之前的dp方法中, 我们是使用的 填表法 , 即使用dp[i - j]位置的dp值来更新dp[i]的值, 在这个过程中, 我们需要遍历所有的j, 并且花费O(n)的时间来截取子串
因此我们可以尝试将 填表法 改为 刷表法, 即使用dp[i]来更新dp[i + j]位置的dp值, 在这个更新的过程中, 我们可以首先通过target的i这个下标往后的子串, 
得到从当前位置往后最多能够匹配多长, 即得出target[i:]区间的最长前缀匹配的长度, 这个长度可以使用 字典树 在O(n)的时间内计算得到
这里假设这个最长长度为len, 那么对于[i, i + len]区间的每一个位置, 都是从i可以转移到的地方, 即从dp[i]可以转移到dp[i + 1, i + len]区间的任意一个位置
那么根据刷表法的思想, 就可以使用dp[i]来更新dp[i + 1, i + len]区间的所有dp值, 这个过程的时间复杂度为O(n), 因此总的dp的时间复杂度就优化成了O(n ^ 2)

回顾上面的dp优化过程, 主要还是利用了将 填表法 转换为 刷表法, 因为刷表法能够和 字典树 结合起来, 事先找到能够 "向后跳" 的最长长度, 之后的j的遍历, 
就可以只在这个区间内进行, 并且由于在这个最长长度之内的所有位置, 都是可以转移到的, 因此无需每次转移之前都花费O(n)的时间来判断是否能够转移

由于是刷表法, 因此i的遍历就需要从0开始, 到n - 1结束, 因此dp[n]不会再继续向后转移了
 */
public class LC3291_1 {
    public int minValidStrings(String[] words, String target) {
        int n = target.length(), m = words.length, INF = 0x3f3f3f3f;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;

        
        for(int i = 0;i < n;i++){
            int maxLen = ;
            for(int j = 1;j <= maxLen;j++){
                dp[i + j] = Math.min(dp[i + j], dp[i] + 1);
            }
        }
        return dp[n] == INF ? -1 : dp[n];
    }

    class Trie{
        class TrieNode{
            boolean end;
            TrieNode[] tns = new TrieNode[26];      // TireNodes
        }
    
        private TrieNode root;
    
        public Trie() {
            root = new TrieNode();
        }
        
        public void insert(String word) {
            TrieNode p = root;
            for(int i = 0;i < word.length();i++){
                int u = word.charAt(i) - 'a';
                if(p.tns[u] == null) p.tns[u] = new TrieNode();
                p = p.tns[u];
            }
            p.end = true;
        }

        // 查询target从下标pos开始在字典树中的最大前缀匹配长度
        public int getMaxMatchLen(String target, int pos){
            TrieNode p = root;
            int len = 0;
            for(int i = pos;i < target.length();i++){
                
            }
        }

        
        public boolean search(String word) {
            TrieNode p = root;
            for(int i = 0;i < word.length();i++){
                int u = word.charAt(i) - 'a';
                if(p.tns[u] == null) return false;
                p = p.tns[u];
            }
            return p.end;
        }
        
        public boolean startsWith(String prefix) {
            TrieNode p = root;
            for(int i = 0;i < prefix.length();i++){
                int u = prefix.charAt(i) - 'a';
                if(p.tns[u] == null) return false;
                p = p.tns[u];
            }
            return true;
        }
    }
}
