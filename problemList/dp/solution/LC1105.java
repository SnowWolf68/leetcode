package solution;

/**
其实就是将所有的书 "划分" 到不同的层中, 求整体的最小层高
 */
public class LC1105 {
    public int minHeightShelves(int[][] books, int shelfWidth) {
        int n = books.length, INF = 0x3f3f3f3f;
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            dp[i] = INF;
            int curWidth = 0, maxHeight = 0;
            for(int j = i;j > 0;j--){
                curWidth += books[j - 1][0];
                maxHeight = Math.max(maxHeight, books[j - 1][1]);
                if(curWidth > shelfWidth) break;
                else{
                    dp[i] = Math.min(dp[i], dp[j - 1] + maxHeight);
                }
            }
        }
        return dp[n];
    }
}
