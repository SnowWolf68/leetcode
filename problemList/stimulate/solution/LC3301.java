package problemList.stimulate.solution;

import java.util.Arrays;

/**
模拟题? 思维题? 贪心题? 
不需要想得那么复杂

首先最大的那个数肯定是不需要变的
然后对于下一个数而言, 如果下一个数 == 前一个数, 那么下一个数 应该设置为前一个数 - 1
                   如果下一个数 != 前一个数, 那么下一个数保持不变
按照这个规律遍历一次原数组即可得出答案
 */
public class LC3301 {
    public long maximumTotalSum(int[] height) {
        Arrays.sort(height);
        long ret = height[height.length - 1];
        int prev = height[height.length - 1];
        for(int i = height.length - 2;i >= 0;i--){
            int cur = Math.min(height[i], prev - 1);
            if(cur <= 0) return -1;
            prev = cur;
            ret += cur;
        }
        return ret;
    }
}
