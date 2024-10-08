package problemList.misc.solution;

/**
和LC1326类似, 也是需要mostRight数组, 而不是 "最多跳数" 的数组
 */
public class LC1024 {
    public int videoStitching(int[][] clips, int time) {
        int n = time + 1;
        // 这题比较奇葩, 有些数据中视频的最长长度会超过time + 1, 因此这里把长度开到最大
        int[] mostRight = new int[101];
        for(int[] clip : clips){
            mostRight[clip[0]] = Math.max(clip[1], mostRight[clip[0]]);
        }
        int curR = 0, nxtR = 0, cnt = 0;
        for(int i = 0;i < n;i++){
            nxtR = Math.max(nxtR, mostRight[i]);
            if(i == curR && i != n - 1){
                if(curR == nxtR) return -1;
                curR = nxtR;
                cnt++;
            }
        }
        return cnt;
    }
}
