package problemList.slidingWindow.solution;

/**
这题很有意思, 在做这题之前, 需要先做 LC3258 , 首先要对这个滑窗很熟悉才行
首先看数据范围, 本题的数据范围要求: 1 <= s.length <= 1e5, 1 <= queries.length <= 1e5
因此显然只能写一个O(n)或O(n * logn)的算法

这里先回顾一下LC3258这题, 考虑一下这题的滑窗能够给我们提供什么信息?
对于[left, right]的这个滑窗来说, 由于滑窗的性质, 我们可以知道, 在while循环更新完left之后, 此时的left, 一定是以right为结尾元素的符合要求的子串的最左端的下标
换句话说, 此时的[left, right]区间的 所有 子串, 都是符合要求的子串

假设我们将所有的这种信息都存储在一个数组left[]中, 其中left[i]表示以i下标为右端点的符合要求的子串的最左端的下标
显然, left[]数组我们可以在一次O(n)的滑窗中计算得到

接下来考虑如何利用left[]数组提供的信息处理所有的查询queries[]

接下来的分析可能会比较抽象, 需要好好思考理解一下

首先关于这题的滑窗, 有以下三个hint: 
    1. 对于某一个符合要求的窗口 [left[i], i] 来说, 这个窗口内的所有子串, 都是符合要求的子串
        那么我们显然能够得出: 符合要求的子串有 1 + 2 + 3 + ... + (i - left[i] + 1) = (n * (n + 1)) / 2 , 其中这里 n = i - left[i] + 1
    2. 对于本题中的滑窗来说, 其满足的是 越短越合法
    3. 滑窗的本质, 可以理解为 枚举子串的右端点

综合上面三条信息, 我们可以这样计算一个查询 [l, r] 区间的结果: 
    对于查询区间 [l, r] 来说, 一定存在一个最大的 L (l < L <= r) , 使得 [l, L] 区间的所有子串都是符合要求的
    换句话说, [l, r] 区间的左边一部分区间, 一定满足: 这段区间中的所有子串都是符合要求的子串
        为什么这里取的是 左边的一段区间, 而不是右边? 
        因为这里的信息是由滑窗提供的, 并且由上面的第3个hint可知, 滑窗实际上就是 枚举右端点
        再结合这里 left[] 数组的定义, 我们可以通过left[]数组, 很快 (O(logn)) 得到 L 的值
            -- 注: 这里其实是一个 查找左区间右端点的二分

        如果取右边的区间, 那么滑窗得到的left[]就不能有效的提供信息了 (因为滑窗实际上就是枚举右端点)
    
    通过上面的分析可以知道, 对于 [l, L] 区间的所有子串, 其都是符合要求的子串, 因此我们可以直接计算得到这段区间的符合要求的子串数量

    如何计算这里的L?
        根据滑窗的特性, 显然我们可以使用一个 O(logn) 的查找左区间右端点的二分得到L

    然后是 [L + 1, r] 这个区间, 这个区间中的子串数量应该如何计算?
    由于这里的left[], 实际上就是枚举子串右端点得到的信息, 因此这里的分析我们也可以从枚举右端点入手
    枚举这个区间的所有子串的右端点i, 即 L + 1 <= i <= r, 对于每一个i来说: 
        以i结尾的子串中, 最长的子串的左端点就是left[i], 换句话说, [left[i], i]这个区间内 以i为右端点的所有子串都是符合要求的
        显然这些子串一共有 i - left[i] + 1 个 (注意这里并不是 [left[i], i]] 区间的所有子串, 而是这个区间内以i为右端点的所有子串)
    类似的, 对于每一个i, 我们都可以计算 以 i 为右端点的符合要求的子串的个数
    因此对于 [L + 1, r] 区间的子串数量, 我们可以枚举i依次累加 i - left[i] + 1 得到

    需要注意的是, 这里枚举i (范围: [L + 1, r]) 的时间复杂度其实也是线性的, 即O(n), 这样显然是不行的, 因此我们需要优化, 显然这部分是 区间和问题 , 因此我们可以使用前缀和优化

时间复杂度: 这里对于每一个查询, 计算L花费O(logn)的时间, 计算[L + 1, r]区间的子串数量经过前缀和优化之后可以做到O(1), 再加上滑窗的时间复杂度 O(n)
因此总的时间复杂度是: O(n + m * logn), 其中m = queries.length
 */
public class LC3261 {
    public long[] countKConstraintSubstrings(String s, int k, int[][] queries) {
        int n = s.length(), m = queries.length, le = 0;
        int[] left = new int[n], cnt = new int[2];
        for(int i = 0;i < n;i++){
            cnt[s.charAt(i) - '0']++;
            while(cnt[0] > k && cnt[1] > k){
                cnt[s.charAt(le) - '0']--;
                le++;
            }
            left[i] = le;
        }
        long[] preSum = new long[n + 1];
        for(int i = 0;i < n;i++){
            preSum[i + 1] = preSum[i] + i - left[i] + 1;
        }
        long[] ret = new long[m];
        for(int i = 0;i < m;i++){
            int[] query = queries[i];
            int l = query[0], r = query[1];
            int bisectL = l, bisectR = r;   // 二分用到的 l, r
            while(bisectL < bisectR){
                int mid = (bisectL + bisectR + 1) >> 1;
                if(left[mid] <= l) bisectL = mid;
                else bisectR = mid - 1;
            }
            int L = bisectL;
            long num = L - l + 1;
            ret[i] += (num * (num + 1)) / 2;   // 计算 [l, L] 区间的符合要求的子串数量
            ret[i] += preSum[r + 1] - preSum[L + 1];
        }
        return ret;
    }
}
