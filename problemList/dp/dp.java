// dp
// 1. 入门dp
//  1.1 爬楼梯
//      LC70 https://leetcode.cn/problems/climbing-stairs/description/
//      LC746 https://leetcode.cn/problems/min-cost-climbing-stairs/description/
//      LC377 https://leetcode.cn/problems/combination-sum-iv/description/  本质还是爬楼梯, 相当于每次向上爬nums[i]步
//      LC2466 https://leetcode.cn/problems/count-ways-to-build-good-strings/description/
//      LC2266 https://leetcode.cn/problems/count-number-of-texts/description/
//  1.2 打家劫舍
//      LC198 https://leetcode.cn/problems/house-robber/description/ 
//      LC740 https://leetcode.cn/problems/delete-and-earn/description/  值域上的打家劫舍
//      LC2320 https://leetcode.cn/problems/count-number-of-ways-to-place-houses/description/  统计方案数的打家劫舍, 注意状态转移方程
//      LC213 https://leetcode.cn/problems/house-robber-ii/description/  环形
//  挑战关: 
//      TODO
//      LC1388 
//      LC2597 https://leetcode.cn/problems/the-number-of-beautiful-subsets/description/  
//  1.3 最大子数组和
//      LC53 https://leetcode.cn/problems/maximum-subarray/description/
//      LC2606 https://leetcode.cn/problems/find-the-substring-with-maximum-cost/description/
//      LC1749 https://leetcode.cn/problems/maximum-absolute-sum-of-any-subarray/description/
//      LC1191 https://leetcode.cn/problems/k-concatenation-maximum-sum/description/  好好分析所有可能, 以及如何正确计算
//      LC918 https://leetcode.cn/problems/maximum-sum-circular-subarray/description/  环形数组
//      LC2321 https://leetcode.cn/problems/maximum-score-of-spliced-array/description/  
//      TODO: LC363
//      LC152 https://leetcode.cn/problems/maximum-product-subarray/description/  乘积最大子数组
// 挑战关: 
//      LC2272 https://leetcode.cn/problems/substring-with-largest-variance/description/  
// 2. 网格图dp
//  2.1 基础
//      LCR166 https://leetcode.cn/problems/li-wu-de-zui-da-jie-zhi-lcof/description/  
//      LC62 https://leetcode.cn/problems/unique-paths/description/ 
//      LC63 https://leetcode.cn/problems/unique-paths-ii/description/ 
//      LC64 https://leetcode.cn/problems/minimum-path-sum/description/ 
//      LC120 https://leetcode.cn/problems/triangle/description/  三角形中的最小路径和
//      LC931 https://leetcode.cn/problems/minimum-falling-path-sum/description/ 
//      LC2684 https://leetcode.cn/problems/maximum-number-of-moves-in-a-grid/description/
//      LC2304 https://leetcode.cn/problems/minimum-path-cost-in-a-grid/description/
//      LC1289 https://leetcode.cn/problems/minimum-falling-path-sum-ii/description/
//  2.2 进阶
//      LC1594 https://leetcode.cn/problems/maximum-non-negative-product-in-a-matrix/description/  相当于是二维的LC152
//      LC1301 https://leetcode.cn/problems/number-of-paths-with-max-score/description/  纯coding题, 很复杂但是没啥思维难度
//      LC2435 https://leetcode.cn/problems/paths-in-matrix-whose-sum-is-divisible-by-k/description/ 
//      LC174 https://leetcode.cn/problems/dungeon-game/description/  倒序DP + 细节问题
//      LC741 https://leetcode.cn/problems/cherry-pickup/description/  难题, 好题, 之前没见过
//      LC1463 https://leetcode.cn/problems/cherry-pickup-ii/description/  上一题的变形
//      LC329 https://leetcode.cn/problems/longest-increasing-path-in-a-matrix/description/  不是按照正常行列顺序的dp
//      LC2328 https://leetcode.cn/problems/number-of-increasing-paths-in-a-grid/description/  跟上一题一样, 但是这里对val排序的处理方式和上一题不同, 可以参考一下
//      LC2267 https://leetcode.cn/problems/check-if-there-is-a-valid-parentheses-string-path/description/  关键是如何判断一条路径是合法的括号路径
//      LC1937 https://leetcode.cn/problems/maximum-number-of-points-with-cost/description/  不大好想的优化: 拆项 + 预处理前后缀最大值, 好好考虑怎么得到的这个优化
// 3. 背包
//  3.1 0-1背包
//      LC2915 https://leetcode.cn/problems/length-of-the-longest-subsequence-that-sums-to-target/description/  
//      LCSF02 https://leetcode.cn/contest/sf-tech/problems/cINqyA/ 
//      LC416 https://leetcode.cn/problems/partition-equal-subset-sum/description/  
//      LC494 https://leetcode.cn/problems/target-sum/description/
//      LC2787 https://leetcode.cn/problems/ways-to-express-an-integer-as-sum-of-powers/description/  初始化需要注意, 不仅需要保证不越界, 还需要保证后续填表的正确性
//      LC474 https://leetcode.cn/problems/ones-and-zeroes/description/  二维0-1背包  这题我写了两种做法, 分别是 "恰好装满" 类型背包的做法, 以及 "不超过" 类型背包的做法
//      LC1049 https://leetcode.cn/problems/last-stone-weight-ii/description/  题目转化很重要, 转换完之后就是一个 "不超过" 类型的背包
//      LC1774 https://leetcode.cn/problems/closest-dessert-cost/description/  这题真的只有1700吗??
//      LC879 https://leetcode.cn/problems/profitable-schemes/description/  "恰好装满" & "不超过/至少" 两种方法, 注意: 如果是 "至少" 类型的背包, 那么除了需要修改初始化以及返回值, 还需要对状态转移方程做一定的修改
//      LC3082 https://leetcode.cn/problems/find-the-sum-of-the-power-of-all-subsequences/description/  难, 子序列的子序列, 普通O(n ^ 2 * k)的0-1背包方法就不好理解, 优化的O(nk)的方法更不好理解
//      LC956 https://leetcode.cn/problems/tallest-billboard/description/  使用 "左右两边的高度差" 来定义状态 很重要
//      LC2518 https://leetcode.cn/problems/number-of-great-partitions/description/  如何想到 "正难则反" 很关键, 我提供了 "恰好装满" 以及 "不超过" 两种0-1背包的写法
//      LC2742 https://leetcode.cn/problems/painting-the-walls/description/  dp做法 & 两种0-1背包做法, 0-1背包有 "至少装满" & "不超过" 两种做法, 其中, 这里 "不超过" 类型做法和之前的 "不超过" 类型做法有一点差别
//      