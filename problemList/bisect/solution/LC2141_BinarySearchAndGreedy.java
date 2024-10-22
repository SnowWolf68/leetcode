package problemList.bisect.solution;

/**
LC上别人写的比较好的题解: https://leetcode.cn/problems/maximum-running-time-of-n-computers/solutions/1213931/er-fen-da-an-by-newhar-swi2/

Binary Search + Greedy
一点启发: 假如给定运行时间time, 怎么判断batteries能否支持n台电脑运行time时间?
    1. 对于batteries[i] >= time的这些电池来说, 此时这些电池只能供电time时间, 多余的电也用不了 (因为一个电池同一时间只能给一台电脑供电)
    2. 对于batteries[i] < time的这些电池来说, 显然一个电脑使用一块这种的电池是不够的
        假设batteries[i] >= time的电池数量是cnt, 那么剩下的n - cnt这些电脑就需要使用剩下的这些batteries[i] < time的电池来供电了
        通过题目中示例1: n = 2, batteries = [3,3,3] 的供电过程, 我们可以大胆猜测: 
            假设剩下的这些电池的供电总时长为sum, 剩下未被供电的电脑数量为leftCnt, 那么能够供电的最长时间为 sum / leftCnt
        (事实上, 我在写的时候也没有考虑这个结论的证明, 直接用这个思想写了一个check, 然后二分时间就过了)

        这里我还是尝试分析一下这个结论的证明: -- 暂时还不会
        
 */
public class LC2141_BinarySearchAndGreedy {
    public long maxRunTime(int n, int[] batteries) {
        long sum = 0;
        for(int x : batteries) sum += x;
        long l = 0, r = sum / n;
        while(l < r){
            long mid = (l + r + 1) >> 1;
            if(check(batteries, mid, n)) l = mid;
            else r = mid - 1;
        }
        return l;
    }

    private boolean check(int[] batteries, long time, int n){
        long sum = 0;
        for(int x : batteries){
            if(x >= time) n--;
            else sum += x;
        }
        if(n <= 0) return true;
        return sum / n >= time;
    }
}
