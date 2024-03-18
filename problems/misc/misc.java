package problems.misc;
// 数组中选取子集, 达到某一目标 -- 折半枚举
// LC1755 https://leetcode.cn/problems/closest-subsequence-sum/description/ 2364 折半枚举 + 排序 + 双指针 时间复杂度O(n * 2 ^ (n / 2))
//  TODO: 这题有O(2 ^ (n / 2))的归并写法, 详见题解: https://leetcode.cn/problems/closest-subsequence-sum/solutions/595419/o2n2de-zuo-fa-by-heltion-0yn7/
// 进阶版: LC2035 https://leetcode.cn/problems/partition-array-into-two-arrays-to-minimize-sum-difference/description/ 2490
//  note: 最后的 "查找最近的数" 部分, 也可以使用 二分 来进行查找, 详见灵神题解: https://leetcode.cn/problems/partition-array-into-two-arrays-to-minimize-sum-difference/solutions/1039419/zhe-ban-mei-ju-pai-xu-er-fen-by-endlessc-04fn/
// 变形/进阶: LC805 https://leetcode.cn/problems/split-array-with-same-average/description/ 1983

// 求max - min: 转化为 枚举所有可能的max与min
// LC3085 https://leetcode.cn/problems/minimum-deletions-to-make-string-k-special/description/ 
