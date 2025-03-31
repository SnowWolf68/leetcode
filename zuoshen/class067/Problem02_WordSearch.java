package zuoshen.class067;

/**
不能由递归改成dp的典型例子

以下内容摘自左神ppt (https://github.com/algorithmzuo/algorithm-journey): 
    能改成动态规划的递归，统一特征：
    决定返回值的可变参数类型往往都比较简单，一般不会比int类型更复杂。为什么？

    从这个角度，可以解释 带路径的递归（可变参数类型复杂），不适合或者说没有必要改成动态规划
    题目2就是说明这一点的

    一定要 写出可变参数类型简单（不比int类型更复杂），并且 可以完全决定返回值的递归，
    保证做到 这些可变参数可以完全代表之前决策过程对后续过程的影响！再去改动态规划！

注: 递归搜索超时不一定是死循环, 还有可能是因为剪枝不够, 导致递归层数过多    递归的精髓 --> 剪枝

 */
public class Problem02_WordSearch {
    private int[] dx = new int[]{1, 0, -1, 0};
    private int[] dy = new int[]{0, -1, 0, 1};
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                if(dfs(board, i, j, word, new boolean[m][n], 0)) return true;
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, int i, int j, String target, boolean[][] vis, int index){
        int m = board.length, n = board[0].length;
        if(board[i][j] != target.charAt(index)) return false;
        if(index == target.length() - 1) return true;
        vis[i][j] = true;
        for(int k = 0;k < 4;k++){
            int nx = i + dx[k], ny = j + dy[k];
            if(nx < 0 || ny < 0 || nx >= m || ny >= n || vis[nx][ny]) continue;
            if(dfs(board, nx, ny, target, vis, index + 1)) return true;
        }
        vis[i][j] = false;
        return false;
    }

    public static void main(String[] args) {
        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        String word = "ABCCED";
        Problem02_WordSearch solution = new Problem02_WordSearch();
        System.out.println(solution.exist(board, word));
    }
}
