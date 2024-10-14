package problemList.slidingWindow.solution;

import java.util.Stack;

/**
如果while循环中的条件我就想写成 left < i, 此时就一定不行吗?
显然不是的, 此时出现问题的原因在于, 在while循环外面更新的时候, 有可能更新出来一个负数, 而这个负数的绝对值就是此时应该更新的结果
那么我们可以在外面更新ret的时候, 使用Math.abs取一个绝对值, 这样就能够避免ret更新成一个负数

并且由于while循环的条件变成了 left < i , 那么走到while循环外面时, 就不会出现left > i的情况, 即出现窗口中一个元素都没有的情况
因此while循环外面更新ret时, 也不需要使用if限制left <= i时, 才进行更新了
 */
public class LC3171_2 {
    public int minimumDifference(int[] nums, int k) {
        Stack<Integer> stack = new Stack<>();
        int rightOr = 0, n = nums.length, ret = Integer.MAX_VALUE, left = 0;
        for(int i = 0;i < n;i++){
            rightOr |= nums[i];
            while(left < i && ((stack.isEmpty() ? 0 : stack.peek()) | rightOr) >= k){
                ret = Math.min(ret, ((stack.isEmpty() ? 0 : stack.peek()) | rightOr) - k);
                left++;
                if(stack.isEmpty()){
                    int curOr = 0;
                    // 虽然这里添加了一个for循环, 但是实际上栈中的元素至多就是n个, 因此总的复杂度还是 O(n) 的
                    for(int j = i;j >= left;j--){
                        curOr |= nums[j];
                        stack.add(curOr);
                    }
                    rightOr = 0;
                }else{
                    stack.pop();
                }
            }
            // 这里需要改动一下
            ret = Math.min(ret, Math.abs(k - ((stack.isEmpty() ? 0 : stack.peek()) | rightOr)));
        }
        return ret;
    }
}
