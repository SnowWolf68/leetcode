package problemList.trie.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
时间复杂度: 一共有m * n个起点, 每次能往4个方向搜索, 并且搜索的长度不会超过10, 因此整体复杂度为O(m * n * 4^10)

如何充分利用 Trie 的特性, 在复杂度无法继续优化的情况下, 做一些进一步的优化?

更进一步, 我们可以把 Trie 的end成员改为String str, 这样就能省去dfs中的StringBuilder
实际上StringBuilder每次增删字符也是比较耗费时间的, 节省掉这些时间以后, 能够大大加快运行速度

281 ms

 */
public class LC212_4 {
    private Trie trie = new Trie();
    private Set<String> set = new HashSet<>();
    private int[] dx = new int[]{1, 0, -1, 0};
    private int[] dy = new int[]{0, 1, 0, -1};
    private char[][] board;
    private int m, n;
    public List<String> findWords(char[][] _board, String[] words) {
        board = _board;
        for(String s : words) trie.insert(s);
        m = board.length; n = board[0].length;
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                // [i, j]
                boolean[][] vis = new boolean[m][n];
                vis[i][j] = true;
                Trie.TrieNode root = trie.root.tns[board[i][j] - 'a'];
                if(root == null) continue;
                dfs(i, j, vis, root);
            }
        }
        List<String> ret = new ArrayList<>(set);
        return ret;
    }

    private void dfs(int i, int j, boolean[][] visited, Trie.TrieNode node) {
        if(node.str != null){
            set.add(node.str);
        }
        for(int k = 0;k < 4;k++){
            int nx = i + dx[k], ny = j + dy[k];
            if(nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny] && node.tns[board[nx][ny] - 'a'] != null){
                Trie.TrieNode next = node.tns[board[nx][ny] - 'a'];
                visited[nx][ny] = true;
                dfs(nx, ny, visited, next);
                visited[nx][ny] = false;
            }
        }
    }

    static class Trie {

        static class TrieNode{
            String str;
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
            p.str = word;
        }
        
        public boolean search(String word) {
            TrieNode p = root;
            for(int i = 0;i < word.length();i++){
                int u = word.charAt(i) - 'a';
                if(p.tns[u] == null) return false;
                p = p.tns[u];
            }
            return p.str != null;
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
