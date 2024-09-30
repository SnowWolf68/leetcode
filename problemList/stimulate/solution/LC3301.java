package problemList.stimulate.solution;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class LC3301 {
    public long maximumTotalSum(int[] maximumHeight) {
        long ret = 0;
        int max = 0;
        Set<Integer> heightSet = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        Arrays.sort(maximumHeight);
        for(int x : maximumHeight){
            if(!heightSet.contains(x)){
                heightSet.add(x);
                ret += x;
                max = Math.max(max, x);
            }else{
                stack.push(x);
            }
        }
        while(!stack.isEmpty()){
            int poll = stack.pop();
            while(heightSet.contains(max) || max > poll) max--;
            if(max <= 0) return -1;
            ret += max;
            heightSet.add(max);
        }
        return ret;
    }
}
