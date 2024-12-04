package problemList.misc.solution;

import java.util.HashMap;
import java.util.Map;

public class LC2056 {
    private static final Map<String, int[][]> dir = new HashMap<>();
    static {
        int[] dx = new int[]{0, 1, 0, -1, 1, -1, -1, 1};
        int[] dy = new int[]{1, 0, -1, 0, -1, 1, -1, 1};
        int[][] queen = new int[8][2];
        for(int i = 0;i < 8;i++) queen[i] = new int[]{dx[i], dy[i]};
        dir.put("queen", queen);
        int[][] rook = new int[4][2];
        for(int i = 0;i < 4;i++) rook[i] = new int[]{dx[i], dy[i]};
        dir.put("rook", rook);
        int[][] bishop = new int[4][2];
        for(int i = 4;i < 8;i++) bishop[i] = new int[]{dx[i], dy[i]};
        dir.put("bishop", bishop);
    }
    private int cnt = 0;
    public int countCombinations(String[] pieces, int[][] positions) {
        
        return cnt;
    }
}
