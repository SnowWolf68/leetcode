package problemList.misc.solution;

import java.util.Arrays;

/**
和LC1326类似, 也是需要mostRight数组, 而不是 "最多跳数" 的数组
 */
public class LC1024 {
    public int videoStitching(int[][] clips, int time) {
        int n = time + 1;
        int[] mostRight = new int[n];
        for(int[] clip : clips){
            mostRight[clip[0]] = Math.max(clip[1], mostRight[clip[0]]);
        }
        System.out.println(Arrays.toString(mostRight));
        int curR = 0, nxtR = 0, cnt = 0;
        for(int i = 0;i < n;i++){
            nxtR = Math.max(nxtR, mostRight[i]);
            if(i == curR){
                if(curR == nxtR && i != n - 1) return -1;
                curR = nxtR;
                cnt++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        int[][] clips = new int[][]{{0,2},{4,6},{8,10},{1,9},{1,5},{5,9}};
        int time = 10;
        System.out.println(new LC1024().videoStitching(clips, time));
    }
}
