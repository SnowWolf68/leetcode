package problemList.slidingWindow.solution;

import java.util.Arrays;

/**
刚拿到这题可能会觉得莫名其妙
我们可以分析一下题目中给的条件来找一下思路
第一个条件表明, 对于x来说, 如果y的年龄小于某个值, 那么此时不会发好友请求
第二个条件表明, 对于x来说, 如果y的年龄大于x的年龄, 那么此时也不会发好友请求
(第三个条件已经包括在第二个条件内了)

通过上面两个条件的分析可以得到: 符合要求的y, 其age[y], 应该在 (0.5 * age[x] + 7, age[x]] 左开右闭 区间内
因此我们可以遍历所有的x, 同时使用滑窗计算所有符合要求的y的数量
这显然是一个 越短越合法的滑窗计数问题

需要注意的是, 如果ages中存在重复元素, 那么对于这些重复元素, 我们可以放在一起计算
即首先找到重复的nums[i]中最右侧的那一个nums[i]的下标, 那么在这个过程中就可以顺便计算到cnt[nums[i]]
然后我们可以计算其中某一个nums[i]会发多少好友请求, 假设为cur, 那么此时这些重复的nums[i]所发的好友请求数量就应该是 cur * cnt[nums[i]]
这种思想在 LC3347 中也用到过
 */
public class LC825 {
    public int numFriendRequests(int[] ages) {
        Arrays.sort(ages);
        System.out.println(Arrays.toString(ages));
        int n = ages.length, ret = 0, left = 0, cnt = 0;
        for(int i = 0;i < n;i++){
            cnt++;
            while(i + 1 < n && ages[i + 1] == ages[i]){
                i++;
                cnt++;
            }
            while(left < i && ages[left] <= 0.5 * ages[i] + 7) left++;
            ret += (i - left) * cnt;
            cnt = 0;
        }
        return ret;
    }
}
