package problemList.trie.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
时间复杂度: 一共有m * n个起点, 每次能往4个方向搜索, 并且搜索的长度不会超过10, 因此整体复杂度为O(m * n * 4^10)

和前一种思路完全相同, 只不过换了一种写法

1443ms
 */
public class LC212_1_another {
    private List<String> ret = new ArrayList<>();
    private int[] dx = new int[]{1, 0, -1, 0};
    private int[] dy = new int[]{0, 1, 0, -1};
    private char[][] board;
    private int m, n;
    private Set<String> wordsSet = new HashSet<>();
    public List<String> findWords(char[][] _board, String[] words) {
        board = _board;
        for(String s : words) wordsSet.add(s);
        m = board.length; n = board[0].length;
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                // [i, j]
                boolean[][] visited = new boolean[m][n];
                visited[i][j] = true;
                StringBuilder sb = new StringBuilder();
                sb.append(board[i][j]);
                dfs(i, j, sb, visited);
            }
        }
        return ret;
    }

    private void dfs(int i, int j, StringBuilder sb, boolean[][] visited) {
        // 必须加上这个剪枝才可过
        if(sb.length() > 10) return;
        if(wordsSet.contains(sb.toString())){
            ret.add(sb.toString());
            // 必须加上这个剪枝才可过
            wordsSet.remove(sb.toString());
        }
        for(int k = 0;k < 4;k++){
            int nx = i + dx[k], ny = j + dy[k];
            if(nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]){
                visited[nx][ny] = true;
                sb.append(board[nx][ny]);
                dfs(nx, ny, sb, visited);
                visited[nx][ny] = false;
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }
}
