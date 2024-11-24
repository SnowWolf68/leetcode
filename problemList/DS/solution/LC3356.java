package problemList.DS.solution;

/**
这题是上一题 (LC3355) 的升级版, 这题对于每一个区间 [l, r] , 此时可以让区间中的每一个下标都 最多减少 val, 并且每个下标减少的值互相独立
最后要求的是: 最少顺序处理多少个 queries[i] , 可以使得nums[]变成零数组

观察题目性质: 对于这里的k, 当k比较小时, 显然不容易得到零数组, 而k比较大时, 此时显然更容易得到零数组
因此k的取值存在 单调性, 可以考虑使用二分来解决

二分k, 对于二分到的某一个m, 此时我们需要check, 顺序处理前m个 queries[] , 能否将nums变成零数组
这个check过程可以使用差分来解决, 具体过程和 LC3355 类似
 */
public class LC3356 {
    public int minZeroArray(int[] nums, int[][] queries) {
        int n = queries.length, l = 0, r = n - 1, max = 0;
        for(int x : nums) max = Math.max(max, x);
        if(max == 0) return 0;
        while(l < r){
            int mid = (l + r) >> 1;
            if(check(mid, nums, queries)) r = mid;
            else l = mid + 1;
        }
        return !check(l, nums, queries) ? -1 : l + 1;
    }

    // return: 顺序处理queries的前mid个查询, 能否让nums变成零数组
    private boolean check(int mid, int[] nums, int[][] queries){
        int n = nums.length;
        int[] diff = new int[n + 1];
        for(int i = 0;i <= mid;i++){
            diff[queries[i][0]] += queries[i][2];
            diff[queries[i][1] + 1] -= queries[i][2];
        }
        int sum = 0;
        for(int i = 0;i < n;i++){
            sum += diff[i];
            if(sum < nums[i]) return false;
        }
        return true;
    }
}
