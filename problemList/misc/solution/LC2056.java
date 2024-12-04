package problemList.misc.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LC2056 {
    private static final Map<String, int[][]> dirs = new HashMap<>();
    static {
        int[] dx = new int[]{0, 1, 0, -1, 1, -1, -1, 1};
        int[] dy = new int[]{1, 0, -1, 0, -1, 1, -1, 1};
        int[][] queen = new int[8][2];
        for(int i = 0;i < 8;i++) queen[i] = new int[]{dx[i], dy[i]};
        dirs.put("queen", queen);
        int[][] rook = new int[4][2];
        for(int i = 0;i < 4;i++) rook[i] = new int[]{dx[i], dy[i]};
        dirs.put("rook", rook);
        int[][] bishop = new int[4][2];
        for(int i = 4;i < 8;i++) bishop[i - 4] = new int[]{dx[i], dy[i]};
        dirs.put("bishop", bishop);
    }
    private int cnt = 0, n;
    private String[] pieces;
    private int[][] positions;
    public int countCombinations(String[] pieces, int[][] positions) {
        this.pieces = pieces;
        this.positions = positions;
        this.n = pieces.length;
        List<int[]> path = new ArrayList<>();   // dx, dy, step
        for(int i = 0;i < n;i++) path.add(new int[]{0, 0, 0});
        dfs(0, path);
        return cnt;
    }

    private void dfs(int i, List<int[]> path){
        if(i == n){
            cnt++;
            return;
        }
        int[][] dir = dirs.get(pieces[i]);
        dfs(i + 1, path);
        for(int[] d : dir){
            for(int step = 1;step < 8;step++){
                int[] copy = path.get(i).clone();
                path.set(i, new int[]{d[0], d[1], step});
                if(!check(d[0], d[1], step, path)){
                    path.set(i, copy);
                    continue;
                }else{
                    dfs(i + 1, path);
                    path.set(i, copy);
                }
            }
        }
    }

    private boolean check(int dx, int dy, int step, List<int[]> path) {
        int maxStep = 0;
        for(int i = 0;i < path.size();i++) maxStep = Math.max(maxStep, path.get(i)[2]);
        List<int[]> pos = new ArrayList<>();
        for(int i = 0;i < n;i++) pos.add(new int[]{positions[i][0], positions[i][1]});
        int[] steps = new int[n];
        for(int i = 0;i < n;i++) steps[i] = path.get(i)[2];
        for(int i = 0;i < maxStep;i++){
            boolean[] setX = new boolean[8], setY = new boolean[8];
            for(int j = 0;j < n;j++){
                if(steps[j] > 0){
                    int nx = pos.get(j)[0] + path.get(j)[0], ny = pos.get(j)[1] + path.get(j)[1];
                    if(nx < 0 || nx >= 8 || ny < 0 || ny >= 8 || (setX[nx] && setY[ny])) return false;
                    setX[nx] = true;
                    setY[ny] = true;
                    pos.set(j, new int[]{nx, ny});
                    steps[j]--;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // 15
        String[] pieces = new String[]{"rook"};
        int[][] positions = new int[][]{{1, 1}};
        System.out.println(new LC2056().countCombinations(pieces, positions));

        // 22
        pieces = new String[]{"queen"};
        positions = new int[][]{{1, 1}};
        System.out.println(new LC2056().countCombinations(pieces, positions));

        // 12
        pieces = new String[]{"bishop"};
        positions = new int[][]{{4,3}};
        System.out.println(new LC2056().countCombinations(pieces, positions));

        // 223
        pieces = new String[]{"rook","rook"};
        positions = new int[][]{{1, 1}, {8, 8}};
        System.out.println(new LC2056().countCombinations(pieces, positions));

        // 281
        pieces = new String[]{"queen","bishop"};
        positions = new int[][]{{5,7}, {3,4}};
        System.out.println(new LC2056().countCombinations(pieces, positions));
    }
}
