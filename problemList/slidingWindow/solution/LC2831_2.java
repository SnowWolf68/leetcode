package problemList.slidingWindow.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
这题其实还有O(n)的解法      -- 参考灵神题解
我们可以将nums中的元素按照值进行分组, 每组中保存当前值在nums中出现的下标
然后我们可以对其中的每一组, 进行滑窗
 */
public class LC2831_2 {
    public int longestEqualSubarray(List<Integer> nums, int k) {
        int n = nums.size();
        List<Integer>[] posList = new ArrayList[n + 1];
        // 使用下面这种创建方式创建出来的posList, 其中每个postList[i]都引用的是同一个ArrayList对象, 这显然是错误的
        // Arrays.fill(posList, new ArrayList<>());
        // 正确的方法有下面几种
        // for(int i = 0;i < n + 1;i++) posList[i] = new ArrayList<>();     // 第一种方式
        Arrays.setAll(posList, i -> new ArrayList<>());                     // 第二种方式
        for(int i = 0;i < n;i++){
            // 注意题目数据范围限制: 1 <= nums[i] <= nums.length, 因此这里直接让nums[i]作为posList的下标没问题
            posList[nums.get(i)].add(i);
        }
        int ret = 0;
        for(int i = 0;i < posList.length;i++){
            // 对每组下标进行滑窗
            if(posList[i].size() < ret) continue;   // 当前这一组下标一定无法更新ret为更大的值, 因此无需滑窗
            int left = 0;
            for(int j = 0;j < posList[i].size();j++){
                while(posList[i].get(j) - posList[i].get(left) + 1 - (j - left + 1) > k){
                    left++;
                }
                ret = Math.max(ret, j - left + 1);
            }
        }
        return ret;
    }
}
