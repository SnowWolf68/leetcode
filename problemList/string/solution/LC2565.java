package problemList.string.solution;

/**
1. 对于删除区间[left, right]来说, 其对应得分为right - left + 1
    题目中的要求是删除区间[left, right]中的一个 子序列, 但是其实如果删除一个子序列可以的话, 那么直接删除[left, right]区间的整个子串显然也是可以的
    因此将问题从 删除[left, right]区间的一个子序列, 转化成直接删除[left, right]区间的子串
2. 删除完t的子串之后, t[:left - 1], [right + 1:]这两个区间的子串, 要能够和s的一个子序列前缀, 和一个子序列后缀匹配
    换句话说, 将s分割为一个前缀和一个后缀, 那么这个前缀和后缀要能够分别和t的[:left - 1]和[right + 1:]匹配
        其中[left, right]就是t要删除的子串的范围
3. 因此我们可以枚举s的分割点i, s[:i]和s[i + 1:]要分别能够和t的[:left], [right + 1:]匹配 (子序列匹配)
    此时的得分为: right - left + 1
    枚举所有的分割点, 即可得到所有可能的得分right - left + 1, 取最小值就是最小得分
4. 如何计算s[:i]和s[i + 1:]和t的前缀和后缀子序列匹配的长度?
    由于这里匹配的是子序列, 因此可以使用双指针来进行匹配

pre[i] 表示s[:i]和t的前缀子序列匹配的最长长度的下标
suf[i] 表示s[i:]和t的后缀子序列匹配的最长长度的下标
 */
public class LC2565 {
    public int minimumScore(String s, String t) {
        int m = s.length(), n = t.length();
        int[] pre = new int[m], suf = new int[m];
        int j = -1;
        for(int i = 0;i < m;i++){
            if(j + 1 < n && s.charAt(i) == t.charAt(j + 1)) j++;
            pre[i] = j;
        }
        j = n;
        for(int i = m - 1;i >= 0;i--){
            if(j - 1 >= 0 && s.charAt(i) == t.charAt(j - 1)) j--;
            suf[i] = j;
        }
        int ret = suf[0];
        for(int i = 0;i < m;i++){
            // [:i], [i + 1:]
            int left = pre[i] + 1, right = i == m - 1 ? n - 1 : suf[i + 1] - 1;
            ret = Math.min(ret, right - left + 1 < 0 ? 0 : right - left + 1);
        }
        return ret;
    }
}
