package problemList.slidingWindow.solution;

import java.util.Stack;

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
