package problemList.slidingWindow.solution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.HashMap;
import java.util.Map;

public class CF1208B {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        
        in.nextToken(); int n = (int)in.nval;

        int[] nums = new int[n];
        for(int i = 0;i < n;i++){
            in.nextToken(); nums[i] = (int)in.nval;
        }

        out.println(solve(nums));


        out.flush();
		out.close();
		br.close();
    }

    private static int solve(int[] nums) {
        int n = nums.length;
        // <val, cnt>
        Map<Integer, Integer> cnt = new HashMap<>();
        for(int x : nums) cnt.put(x, cnt.getOrDefault(x, 0) + 1);
        if(check(cnt)) return 0;
        int left = 0, ret = Integer.MAX_VALUE;
        for(int i = 0;i < n;i++){
            cnt.put(nums[i], cnt.get(nums[i]) - 1);
            while(left <= i && check(cnt)){
                ret = Math.min(ret, i - left + 1);
                // System.out.println("ret = " + ret + " left = " + left + " right = " + i);
                cnt.put(nums[left], cnt.getOrDefault(nums[left], 0) + 1);
                left++;
            }
        }
        return ret;
    }

    private static boolean check(Map<Integer, Integer> cnt) {
        for(int key : cnt.keySet()) if(cnt.get(key) > 1) return false;
        return true;
    }
}
