package problemList.misc.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LC2056 {
    // TODO: 还有问题
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
            for(int[] pa : path){
                System.out.print(Arrays.toString(pa) + " ");
            }
            System.out.println();
            return;
        }
        int[][] dir = dirs.get(pieces[i]);
        dfs(i + 1, path);
        for(int[] d : dir){
            for(int step = 1;step < 8;step++){
                int[] copy = path.get(i).clone();
                path.set(i, new int[]{d[0], d[1], step});
                if(!check(i, path)){
                    path.set(i, copy);
                    continue;
                }else{
                    dfs(i + 1, path);
                    path.set(i, copy);
                }
            }
        }
    }

    private boolean check(int idx, List<int[]> path){
        if(idx == 0){
            int[] move = path.get(0);
            int nx = positions[0][0] - 1 + move[0] * move[2], ny = positions[0][1] - 1 + move[1] * move[2];
            if(nx < 0 || nx >= 8 || ny < 0 || ny >= 8) return false;
            else return true;
        }else{
            for(int i = 0;i <= idx;i++){
                for(int j = i + 1;j <= idx;j++){
                    if(!checkTwo(i, j, path.get(i), path.get(j))) return false;
                }
            }
            return true;
        }
    }

    private boolean checkTwo2(int i, int j, int[] move1, int[] move2){
        int x1 = positions[i][0] - 1, y1 = positions[i][1] - 1;
        int x2 = positions[j][0] - 1, y2 = positions[j][1] - 1;
        
        // 检查初始位置
        if((x1 == x2 && y1 == y2)) return false;
        
        // 获取最终位置
        int ex1 = x1 + move1[0] * move1[2];
        int ey1 = y1 + move1[1] * move1[2];
        int ex2 = x2 + move2[0] * move2[2];
        int ey2 = y2 + move2[1] * move2[2];
        
        // 检查边界
        if(ex1 < 0 || ex1 >= 8 || ey1 < 0 || ey1 >= 8 || 
           ex2 < 0 || ex2 >= 8 || ey2 < 0 || ey2 >= 8) {
            return false;
        }
        
        // 检查最终位置是否相同
        if(ex1 == ex2 && ey1 == ey2) return false;
        
        // 检查移动过程中是否相交
        for(int step = 1; step <= Math.max(move1[2], move2[2]); step++) {
            int nx1 = x1, ny1 = y1, nx2 = x2, ny2 = y2;
            
            if(step <= move1[2]) {
                nx1 = x1 + move1[0] * step;
                ny1 = y1 + move1[1] * step;
            } else {
                nx1 = ex1;
                ny1 = ey1;
            }
            
            if(step <= move2[2]) {
                nx2 = x2 + move2[0] * step;
                ny2 = y2 + move2[1] * step;
            } else {
                nx2 = ex2;
                ny2 = ey2;
            }
            
            // 检查当前步是否相交，包括交叉移动的情况
            if(nx1 == nx2 && ny1 == ny2) return false;
            
            // 检查是否存在交叉移动
            if(step > 1 && 
               nx1 == prevX2 && ny1 == prevY2 && 
               nx2 == prevX1 && ny2 == prevY1) {
                return false;
            }
            
            prevX1 = nx1; prevY1 = ny1;
            prevX2 = nx2; prevY2 = ny2;
        }
        return true;
    }
    
    private int prevX1, prevY1, prevX2, prevY2; // 添加为类成员变量

    private boolean checkTwo(int i, int j, int[] move1, int[] move2){
        // int x1 = positions[i][0] - 1, y1 = positions[i][1] - 1, x2 = positions[j][0] - 1, y2 = positions[j][1] - 1;
        // for(int step = 1;step <= Math.max(move1[2], move2[2]);step++){
        //     int nx1 = x1, nx2 = x2, ny1 = y1, ny2 = y2;
        //     if(step <= move1[2]){
        //         nx1 = x1 + move1[0];
        //         ny1 = y1 + move1[1];
        //     }
        //     if(step <= move2[2]){
        //         nx2 = x2 + move2[0];
        //         ny2 = y2 + move2[1];
        //     }
        //     if(nx1 < 0 || nx1 >= 8 || ny1 < 0 || ny1 >= 8 || nx2 < 0 || ny2 >= 8 || ny2 < 0|| ny2 >= 8 || (nx1 == nx2 && ny1 == ny2)) {
        //         // System.out.println("false");
        //         return false;
        //     }
        //     // System.out.println("nx1 = " + nx1 + " nx2 = " + nx2 + " ny1 = " + ny1 + " ny2 = " + ny2);
        //     x1 = nx1;
        //     x2 = nx2;
        //     y1 = ny1;
        //     y2 = ny2;
        // }
        // return true;

        // int x1 = positions[i][0] - 1, y1 = positions[i][1] - 1;
        // int x2 = positions[j][0] - 1, y2 = positions[j][1] - 1;
        
        // for(int step = 1;step <= Math.max(move1[2], move2[2]);step++){
        //     int nx1 = x1, nx2 = x2, ny1 = y1, ny2 = y2;
        //     if(step <= move1[2]){
        //         nx1 = x1 + move1[0] * step;
        //         ny1 = y1 + move1[1] * step;
        //     }
        //     if(step <= move2[2]){
        //         nx2 = x2 + move2[0] * step;
        //         ny2 = y2 + move2[1] * step;
        //     }
        //     if(nx1 < 0 || nx1 >= 8 || ny1 < 0 || ny1 >= 8 || 
        //     nx2 < 0 || nx2 >= 8 || ny2 < 0 || ny2 >= 8 || 
        //     (nx1 == nx2 && ny1 == ny2)) {
        //         return false;
        //     }
        // }
        // return true;

        int x1 = positions[i][0] - 1, y1 = positions[i][1] - 1;
        int x2 = positions[j][0] - 1, y2 = positions[j][1] - 1;
        
        for(int step = 1;step <= Math.max(move1[2], move2[2]);step++){
            int nx1 = x1, nx2 = x2, ny1 = y1, ny2 = y2;
            // 计算第一个棋子的位置
            if(step <= move1[2]){
                nx1 = x1 + move1[0] * step;
                ny1 = y1 + move1[1] * step;
            }else{
                nx1 = x1 + move1[0] * move1[2];
                ny1 = y1 + move1[1] * move1[2];
            }
            
            // 计算第二个棋子的位置
            if(step <= move2[2]){
                nx2 = x2 + move2[0] * step;
                ny2 = y2 + move2[1] * step;
            }else{
                nx2 = x2 + move2[0] * move2[2];
                ny2 = y2 + move2[1] * move2[2];
            }
            
            // 检查边界和碰撞
            if(nx1 < 0 || nx1 >= 8 || ny1 < 0 || ny1 >= 8 || 
            nx2 < 0 || nx2 >= 8 || ny2 < 0 || ny2 >= 8 || 
            (nx1 == nx2 && ny1 == ny2)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkOld(int idx, List<int[]> path) {
        int maxStep = 0;
        for(int i = 0;i < path.size();i++) maxStep = Math.max(maxStep, path.get(i)[2]);
        List<int[]> pos = new ArrayList<>();
        for(int i = 0;i < n;i++) pos.add(new int[]{positions[i][0] - 1, positions[i][1] - 1});
        int[] steps = new int[n];
        for(int i = 0;i < n;i++) steps[i] = path.get(i)[2];
        boolean[] setX = new boolean[8], setY = new boolean[8];
        for(int j = 0;j <= idx;j++){
            setX[pos.get(j)[0]] = true;
            setY[pos.get(j)[1]] = true;
        }
        for(int i = 0;i < maxStep;i++){
            for(int j = 0;j <= idx;j++){
                if(steps[j] > 0){
                    setX[pos.get(j)[0]] = false;
                    setY[pos.get(j)[1]] = false;
                    int nx = pos.get(j)[0] + path.get(j)[0], ny = pos.get(j)[1] + path.get(j)[1];
                    if(nx < 0 || nx >= 8 || ny < 0 || ny >= 8 || (setX[nx] && setY[ny])) {
                        System.out.println("false: " + (nx < 0 || nx >= 8 || ny < 0 || ny >= 8));
                        return false;
                    }
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

        System.out.println("-----------------------------");

        // // 22
        // pieces = new String[]{"queen"};
        // positions = new int[][]{{1, 1}};
        // System.out.println(new LC2056().countCombinations(pieces, positions));

        // // 12
        // pieces = new String[]{"bishop"};
        // positions = new int[][]{{4,3}};
        // System.out.println(new LC2056().countCombinations(pieces, positions));

        // // 223
        // pieces = new String[]{"rook","rook"};
        // positions = new int[][]{{1, 1}, {8, 8}};
        // System.out.println(new LC2056().countCombinations(pieces, positions));

        // // System.out.println("-----------------------------");

        // // 281
        // pieces = new String[]{"queen","bishop"};
        // positions = new int[][]{{5,7}, {3,4}};
        // System.out.println(new LC2056().countCombinations(pieces, positions));

        // 293
        pieces = new String[]{"queen","rook"};
        positions = new int[][]{{1, 2}, {7, 2}};
        System.out.println(new LC2056().countCombinations(pieces, positions));
    }
}
