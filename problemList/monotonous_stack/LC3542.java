package problemList.monotonous_stack;

import java.util.Stack;

/**
单调栈

从对题意的贪心模拟发现单调栈做法

考虑从左到右遍历数组, 只有到了不得不进行操作的时候才进行操作
 */
public class LC3542 {
    public int minOperations(int[] nums) {
        int cnt = 0;
        Stack<Integer> stack = new Stack<>();
        for(int x : nums){
            while (!stack.isEmpty() && x < stack.peek()) {
                stack.pop();
                cnt++;
            }
            if(stack.isEmpty() || x > stack.peek()){
                if(x != 0) stack.add(x);
            }
        }
        return cnt + stack.size();
    }
}
