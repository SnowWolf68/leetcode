package problemList.slidingWindow.solution;

import java.util.Arrays;

/**
这题的滑窗有点难

当carpet的右端点不在某一段tiles的结束位置时, 让carpet的右端点移动到当前这段瓷砖的右端点, 此时覆盖的瓷砖数一定不会减少
因此可以认为carpet的右端点一定在某段tiles的区间的右端点
因此对titles的 下标数组 进行滑窗

注意: 左端点left所在的这段瓷砖tiles[left]的左端点tiles[left][0]不一定被当前的carpet覆盖
如果覆盖, 那么总的覆盖瓷砖数量就是cover, 如果没有覆盖, 那么需要在cover的基础上减掉没有被覆盖的部分
 */
public class LC2271 {
    public int maximumWhiteTiles(int[][] tiles, int carpetLen) {
        Arrays.sort(tiles, (o1, o2) -> o1[0] - o2[0]);
        int n = tiles.length, left = 0, cover = 0, ret = 0;
        for(int i = 0;i < n;i++){
            cover += tiles[i][1] - tiles[i][0] + 1;
            while(tiles[i][1] - tiles[left][1] + 1 > carpetLen){
                cover -= tiles[left][1] - tiles[left][0] + 1;
                left++;
            }
            ret = Math.max(ret, tiles[left][0] < tiles[i][1] - carpetLen + 1 ?
                             cover - (tiles[i][1] - carpetLen + 1 - tiles[left][0]) : cover);
        }
        return ret;
    }
}
