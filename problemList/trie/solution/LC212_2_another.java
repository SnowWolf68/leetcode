package problemList.trie.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
时间复杂度: 一共有m * n个起点, 每次能往4个方向搜索, 并且搜索的长度不会超过10, 因此整体复杂度为O(m * n * 4^10)

1603 ms

和第二种方法的思路完全相同, 只不过换了一种写法
 */
public class LC212_2_another {
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
                dfs(i, j, sb, vis);
            }
        }
        List<String> ret = new ArrayList<>(set);
        return ret;
    }

    private void dfs(int i, int j, StringBuilder sb, boolean[][] visited) {
        // 必须加上这个剪枝才可过
        if(sb.length() > 10) return;
        if(trie.search(sb.toString())){
            set.add(sb.toString());
        }
        for(int k = 0;k < 4;k++){
            int nx = i + dx[k], ny = j + dy[k];
            if(nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]){
                sb.append(board[nx][ny]);
                visited[nx][ny] = true;
                dfs(nx, ny, sb, visited);
                sb.deleteCharAt(sb.length() - 1);
                visited[nx][ny] = false;
            }
        }
    }

    public class Trie {

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
