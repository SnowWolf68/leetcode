package problemList.slidingWindow.solution;

import java.util.Stack;

/**
这题非常有意思, 也比较难

首先我们抛开如何维护窗口内的元素的or不谈, 先分析本题为什么可以使用滑动窗口, 并且要使用什么样的滑动窗口
首先题目的数据范围限定了, 数组nums[]中的元素nums[i]都是大于1的数
因此对于子数组的or来说, 子数组越长, 子数组的or就会越大, 具有单调性
因此可以考虑使用滑窗优化

本题中求的子数组是要满足 和给定的k的绝对差 要尽可能小
因此可以拆分成两个问题: 
    1. 当子数组的or比k大时, 求 子数组or - k 的最小值
    2. 当子数组的or比k小时, 求 k - 子数组or 的最大值
这实际上就是 求 滑窗最小值 以及 求 滑窗最大值 的两个子问题

先考虑这两个子问题如何解决
    1. 当子数组的or比k大时, 求 子数组or - k 的最小值
        为什么可以使用滑窗?
        
        
 */
public class LC3171 {
    public int minimumDifference(int[] nums, int k) {
        Stack<Integer> stack = new Stack<>();
        int rightOr = 0, n = nums.length, ret = Integer.MAX_VALUE, left = 0;
        for(int i = 0;i < n;i++){
            rightOr |= nums[i];
            while(left <= i && ((stack.isEmpty() ? 0 : stack.peek()) | rightOr) >= k){
                ret = Math.min(ret, ((stack.isEmpty() ? 0 : stack.peek()) | rightOr) - k);
                left++;
                if(stack.isEmpty()){
                    int curOr = 0;
                    for(int j = i;j >= left;j--){
                        curOr |= nums[j];
                        stack.add(curOr);
                    }
                    rightOr = 0;
                }else{
                    stack.pop();
                }
            }
            // 之所以判断left <= i时, 才更新ret, 是因为有可能走到这里时, left = i + 1, 此时窗口中没有任何元素, 显然不能更新ret
            if(left <= i) ret = Math.min(ret, k - ((stack.isEmpty() ? 0 : stack.peek()) | rightOr));
        }
        return ret;
    }
}
