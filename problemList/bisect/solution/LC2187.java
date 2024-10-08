package problemList.bisect.solution;

public class LC2187 {
    public long minimumTime(int[] time, int totalTrips) {
        int minTime = Integer.MAX_VALUE;
        for(int x : time) minTime = Math.min(minTime, x);
        long l = 0, r = (long)minTime * totalTrips;
        // 二分寻找右区间的左端点
        while(l < r){
            long mid = (l + r) >> 1;
            if(check(mid, time, totalTrips)) r = mid;
            else l = mid + 1;
        }
        return l;
    }

    // return 是否可以在curTime时间内完成totalTrips这么多次旅程
    private boolean check(long curTime, int[] time, int totalTrips) {
        long curTrips = 0;
        for(int x : time){
            curTrips += curTime / x;
        }
        return curTrips >= totalTrips;
    }
}
