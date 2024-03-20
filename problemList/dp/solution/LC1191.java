package problemList.dp.solution;

/**
串联后的最大子数组和有三种可能
    1. sum(arr) * k
    2. arr中的最大子数组和
    3. suffix(arr) + (k - 2) * arr + preffix(arr)
针对第3中情况, 应使得suffix(arr) + preffix(arr)最大, 我们可以首先求arr中的最小子数组和minSum, 此时max(suffix(arr) + preffix(arr)) = sum(arr) - minSum
 */
public class LC1191 {
    public int kConcatenationMaxSum(int[] arr, int k) {
        int n = arr.length;
        int sum = 0;
        for(int x : arr) sum += x;
        int f = arr[0], ret1 = f;
        for(int i = 1;i < n;i++){
            f = Math.max(f + arr[i], arr[i]);
            ret1 = Math.max(ret1, f);
        }
        ret1 = Math.max(ret1, 0);
        int[] nums = new int[2 * n];
        for(int i = 0;i < 2 * n;i++) nums[i] = arr[i % n];
        int g = nums[0], ret2 = g;
        for(int i = 1;i < 2 * n;i++){
            g = Math.min(g + nums[i], nums[i]);
            ret2 = Math.min(ret2, g);
        }
        ret2 = Math.min(ret2, 0);
        System.out.println(sum * 4 + (7+6+2+4+1));
        // TODO 
        return Math.max(sum * k, Math.max(ret1, k < 2 ? 0 : Math.max(2 * sum - ret2 + (k - 2) * sum, 2 * sum - ret2)));
    }
    public static void main(String[] args) {
        // 20
        // int[] nums = new int[]{-5,-2,0,0,3,9,-2,-5,4};
        // int k = 5;
        // 88
        int[] nums = new int[]{-1,5,-7,2,-1,0,7,6,2,4};
        int k = 5;
        System.out.println(new LC1191().kConcatenationMaxSum(nums, k));
    }
}
