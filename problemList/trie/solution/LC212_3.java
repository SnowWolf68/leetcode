package problemList.trie.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
时间复杂度: 一共有m * n个起点, 每次能往4个方向搜索, 并且搜索的长度不会超过10, 因此整体复杂度为O(m * n * 4^10)

如何充分利用 Trie 的特性, 在复杂度无法继续优化的情况下, 做一些进一步的优化?

这就需要利用到 Trie 中 前缀 的特性, 即在拓展(nx, ny)的时候, 必须保证board[i][j]有board[nx][ny]这个子节点才可以拓展, 否则不拓展

947 ms

 */
public class LC212_3 {
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
                StringBuilder sb = new StringBuilder();
                sb.append(board[i][j]);
                boolean[][] vis = new boolean[m][n];
                vis[i][j] = true;
                Trie.TrieNode root = trie.root.tns[board[i][j] - 'a'];
                if(root == null) continue;
                dfs(i, j, sb, vis, root);
            }
        }
        List<String> ret = new ArrayList<>(set);
        return ret;
    }

    private void dfs(int i, int j, StringBuilder sb, boolean[][] visited, Trie.TrieNode node) {
        // 必须加上这个剪枝才可过
        if(sb.length() > 10) return;
        if(trie.search(sb.toString())){
            set.add(sb.toString());
        }
        for(int k = 0;k < 4;k++){
            int nx = i + dx[k], ny = j + dy[k];
            if(nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny] && node.tns[board[nx][ny] - 'a'] != null){
                Trie.TrieNode next = node.tns[board[nx][ny] - 'a'];
                sb.append(board[nx][ny]);
                visited[nx][ny] = true;
                dfs(nx, ny, sb, visited, next);
                sb.deleteCharAt(sb.length() - 1);
                visited[nx][ny] = false;
            }
        }
    }

    static class Trie {

        static class TrieNode{
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
