package problems.dp;
// LCS
// 板子题: LC1143 https://leetcode.cn/problems/longest-common-subsequence/description/
//        LC72 https://leetcode.cn/problems/edit-distance/description/
//  TODO: 更快的做法: SPOJ LCS0 https://www.luogu.com.cn/problem/SP12076    现在的水平还理解不了这种做法
// 简单题: LC97 https://leetcode.cn/problems/interleaving-string/description/ 一开始看起来是三维DP, 实际上还是二维DP
//        LC115 https://leetcode.cn/problems/distinct-subsequences/description/ 
//        LC583 https://leetcode.cn/problems/delete-operation-for-two-strings/description/ 
//        LC712 https://leetcode.cn/problems/minimum-ascii-delete-sum-for-two-strings/description/
//        LC1035 https://leetcode.cn/problems/uncrossed-lines/description/ 
//        LC1458 https://leetcode.cn/problems/max-dot-product-of-two-subsequences/description/  特殊情况如何处理
//        LC1092 https://leetcode.cn/problems/shortest-common-supersequence/description/  输出具体方案
// 略难一点: LC1639 https://leetcode.cn/problems/number-of-ways-to-form-a-target-string-given-a-dictionary/description/ 
// 当其中一个序列不包含重复元素时, LCS可以转化为LIS: LC1713 https://leetcode.cn/problems/minimum-operations-to-make-a-subsequence/description/ 
// 



// 0-1 背包
// 难题: BiWeekly126T4 https://leetcode.cn/problems/find-the-sum-of-the-power-of-all-subsequences/description/ 
// 