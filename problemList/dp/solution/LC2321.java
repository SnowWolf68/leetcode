package problemList.dp.solution;

/**
nums1各位 - nums2各位 得到的数组表示nums1中对应元素交换给nums2后, nums2中对应元素的变化量
nums2各位 - nums1各位 得到的数组同理

假设nums1 - nums2 -> arr2, nums2 - nums1 ->arr1
那么相当于求arr1, arr2中的 最大子数组和 即可
然后用sum1, sum2分别加上arr1, arr2中的最大子数组和, 就是nums2中子数组交换给nums1, 以及nums1中子数组交换给nums2, 两种情况下nums1, nums2各自和的最大值

注意这里arr1, arr2记录的是 "变化量" , 因此求变化后的nums1/nums2的所有元素和, 直接让sum1(或sum2) + arr1(或arr2)中最大子数组和即可
 */
public class LC2321 {
    public int maximumsSplicedArray(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] arr1 = new int[n], arr2 = new int[n];
        int sum1 = 0, sum2 = 0;
        for(int i = 0;i < n;i++){
            arr2[i] = nums1[i] - nums2[i];
            arr1[i] = nums2[i] - nums1[i];
            sum1 += nums1[i];
            sum2 += nums2[i];
        }
        int f = arr1[0], g = arr2[0], maxSum1 = f, maxSum2 = g;
        for(int i = 1;i < n;i++){
            f = Math.max(arr1[i], f + arr1[i]);
            g = Math.max(arr2[i], g + arr2[i]);
            maxSum1 = Math.max(maxSum1, f);
            maxSum2 = Math.max(maxSum2, g);
        }
        return Math.max(sum1 + maxSum1, sum2 + maxSum2);
    }
}
