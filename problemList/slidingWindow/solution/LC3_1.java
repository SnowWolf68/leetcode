package problemList.slidingWindow.solution;

/**
滑动窗口本质上是对 "枚举区间右端点" 的优化
    优化的点在于: 在当前枚举到某个右端点i的时候, 继续枚举左端点时, 并不是从0开始枚举, 而是利用上一个右端点i - 1的信息
        简化左端点枚举的起始范围
    例如: 假设上一个枚举的右端点是i - 1, 其对应的左端点为left, 那么当枚举右端点为i时, 此时再枚举左端点, 就不必要从0开始
    而是考虑在上一个区间[left, i - 1]的基础上, 在后面追加一个下标i, 之后组成的[left, i]区间是否满足要求
    如果不满足要求, 那么在继续缩小区间范围, 即left++, 直到找到第一个符合要求的区间

    通过上面的分析不难看出, 能够利用滑动窗口的前提, 是符合要求的区间要能够由所谓的单调性
    即区间长度越短, 就越能够符合要求
 */
public class LC3_1 {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), left = 0;
        int[] cnt = new int[128];
        int ret = 0;
        for(int i = 0;i < n;i++){
            cnt[s.charAt(i)]++;
            while(!check(cnt)){
                cnt[s.charAt(left)]--;
                left++;
            }
            ret = Math.max(ret, i - left + 1);
        }
        return ret;
    }

    private boolean check(int[] cnt){
        for(int i = 0;i < 128;i++){
            if(cnt[i] >= 2) return false;
        }
        return true;
    }
}
