package problemList.dp.solution;

import java.util.*;

/**
首先对nums进行排序, 这样假设我们从前往后取数, 那么此时如果我们想要判断当前元素能不能加入前面的某一个整除子集
    我们只需要判断当前元素能不能被前面的整除子集的最后一个元素(最大元素)即可
dp[i] 表示以下标为i的元素结尾的最长整除子集的长度
    dp[i]: 枚举nums[i]可以跟在前面哪个元素(假设为j)后面, 其中j < i
        dp[i] = Math.max(dp[i], dp[j] + 1);
初始化: 每一次循环中, 都能够保证j的范围合法, 因此不需要初始化

先dp, 然后根据dp结果进行构造具体方案
 */
public class LC368 {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int[] dp = new int[n];
        for(int i = 0;i < n;i++){
            dp[i] = 1;
            for(int j = 0;j < i;j++){
                if(nums[i] % nums[j] == 0) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        // 根据dp构造具体方案
        List<Integer> ret = new ArrayList<>();
        int max = Arrays.stream(dp).max().getAsInt();
        int index = -1;
        for(int i = 0;i < n;i++){
            if(dp[i] == max){
                index = i;
                break;
            }
        }
        // 由于dp值相同的情况下还是可能出现多种情况, 因此这里加一个判断preNum % nums[index] == 0
        int preNum = nums[index];
        while(index >= 0){
            if(preNum % nums[index] == 0) {
                ret.add(nums[index]);
                preNum = nums[index];
                max--;
            }
            index--;
            while(index >= 0 && dp[index] != max){
                index--;
            }
        }
        return ret;
    }
}
