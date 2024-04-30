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
//      LCP47 https://leetcode.cn/problems/oPs9Bm/description/  看起来很复杂, 但是其实就是一个很简单的求方案数的 "恰好装满" 类型的0-1背包
//  3.2 完全背包
//      LC322 https://leetcode.cn/problems/coin-change/description/ 
//      LC518 https://leetcode.cn/problems/coin-change-ii/description/ 
//      LC279 https://leetcode.cn/problems/perfect-squares/description/ 
//      LC1449 https://leetcode.cn/problems/form-largest-integer-with-digits-that-add-up-to-target/description/  有点难, 需要想明白 "长度" 与 "数字大小" 的优先级关系, 然后转化成 先dp, 再构造 类型的题目
//  3.3 多重背包
//      LC1155 https://leetcode.cn/problems/number-of-dice-rolls-with-target-sum/description/  两种方法: 1) 常规多重背包 O(n * target * k)  2) 前缀和优化 O(n * target) 
//      LC2585 https://leetcode.cn/problems/number-of-ways-to-earn-points/description/  多重背包 & 同余前缀和优化
//      LC2902 https://leetcode.cn/problems/count-of-sub-multisets-with-bounded-sum/description/  和上一题差不多, 只是有一点小区别  这题使用不优化的多重背包会TLE, 需要使用同余前缀和优化
//  3.4 分组背包
//      LC1981 https://leetcode.cn/problems/minimize-the-difference-between-target-and-chosen-elements/description/  每组只能选一个
//      LC2218 https://leetcode.cn/problems/maximum-value-of-k-coins-from-piles/description/  前缀和转化 + 每组至多选一个的分组背包
// 4. 经典线性DP
//  4.1 最长公共子序列(LCS)
//      板子题: LC1143 https://leetcode.cn/problems/longest-common-subsequence/description/
//             LC72 https://leetcode.cn/problems/edit-distance/description/
//      简单题: LC97 https://leetcode.cn/problems/interleaving-string/description/ 一开始看起来是三维DP, 实际上还是二维DP
//             LC115 https://leetcode.cn/problems/distinct-subsequences/description/ 
//             LC583 https://leetcode.cn/problems/delete-operation-for-two-strings/description/ 
//             LC712 https://leetcode.cn/problems/minimum-ascii-delete-sum-for-two-strings/description/
//             LC1035 https://leetcode.cn/problems/uncrossed-lines/description/ 
//             LC1458 https://leetcode.cn/problems/max-dot-product-of-two-subsequences/description/  特殊情况如何处理
//             LC1092 https://leetcode.cn/problems/shortest-common-supersequence/description/  输出具体方案
//      略难一点: LC1639 https://leetcode.cn/problems/number-of-ways-to-form-a-target-string-given-a-dictionary/description/ 
//      比较难: LC44 https://leetcode.cn/problems/wildcard-matching/description/
//             LC10 https://leetcode.cn/problems/regular-expression-matching/description/  
//  4.2 最长递增子序列(LIS)
//      板子题: LC300 https://leetcode.cn/problems/longest-increasing-subsequence/description/  O(n ^ 2)的普通dp, O(n * logn)的 贪心 + 二分 & 基于值域的dp + 树状数组优化
//             LC673 https://leetcode.cn/problems/number-of-longest-increasing-subsequence/description/  dp求最大长度的同时, 求对应数量  两种方法: 常规dp: O(n ^ 2)  值域dp + 树状数组: O(n * logn)
//             LC2826 https://leetcode.cn/problems/sorting-three-groups/description/  也是板子题, 常规dp/贪心 + 二分/值域dp + 树状数组
//             LC1671 https://leetcode.cn/problems/minimum-number-of-removals-to-make-mountain-array/description/  常规dp: O(n ^ 2) & 贪心 + 二分: O(n * logn)
//             LC1964 https://leetcode.cn/problems/find-the-longest-valid-obstacle-course-at-each-position/description/  贪心 + 二分/值域dp + 树状数组
//      进阶: LC1626 https://leetcode.cn/problems/best-team-with-no-conflicts/description/  二维LIS: 双关键字排序 + 一维LIS
//           LC354 https://leetcode.cn/problems/russian-doll-envelopes/description/  这才是最 "模版" 的二维LIS, 注意这题要求两个维度都是 "严格大于" , 并且这题的数据范围还限制了只能使用O(n * logn)的做法
//           TODO: O(n * logn)的做法  LC1691 https://leetcode.cn/problems/maximum-height-by-stacking-cuboids/description/  "可旋转" 该如何分析? 三维LIS 这里我只会O(n ^ 2)的写法, 按理说还有值域dp + 二维树状数组的O(n * logn)的写法, 但是我不会, 加一个TODO
//           LC960 https://leetcode.cn/problems/delete-columns-to-make-sorted-iii/description/  看起来很难, 其实还是一维LIS, 只不过判断j是否合法要稍微复杂一点
//           LC2407 https://leetcode.cn/problems/longest-increasing-subsequence-ii/description/  加了一个限制的一维LIS, 数据范围1e5, 只能用O(n * logn)的方法, 并且这里涉及 "区间查询最大值" 因此需要用值域dp + 线段树
//      难题: TODO: 时间复杂度我分析的好像有问题 LC1187 https://leetcode.cn/problems/make-array-strictly-increasing/description/  两种方法: 1) dp角度出发  2) LIS角度出发
//           LC1713 https://leetcode.cn/problems/minimum-operations-to-make-a-subsequence/description/  LCS -> LIS 前提是LCS的两个数组中有一个数组中不包含重复元素
//      思维扩展: LC368 https://leetcode.cn/problems/largest-divisible-subset/description/  最大整除子集的寻找也能使用类似LIS的方法来解决, 注意这里还要求你输出具体方案
// 6. 划分型DP
//  6.1 判定能否划分
//          简单题:  LC2369 https://leetcode.cn/problems/check-if-there-is-a-valid-partition-for-the-array/description/
//                  LC139 https://leetcode.cn/problems/word-break/description/  
//  6.2 计算划分个数
//          简单题:  LC132 https://leetcode.cn/problems/palindrome-partitioning-ii/description/  
//                  LC2707 https://leetcode.cn/problems/extra-characters-in-a-string/description/  给我晃到了, 其实这题没有必要将 "最少" 转化成 "最多", 直接按照题意dp就可以, 转化之后倒也可以做, 但是相当于多绕了个圈子, 没必要
//                  LC2767 https://leetcode.cn/problems/partition-string-into-minimum-beautiful-substrings/description/  
//                  LC91 https://leetcode.cn/problems/decode-ways/description/  
//                  LC639 https://leetcode.cn/problems/decode-ways-ii/description/  看起来不难, 但是状态转移方程一定要想清楚
//                  LCR165 https://leetcode.cn/problems/ba-shu-zi-fan-yi-cheng-zi-fu-chuan-lcof/description/  和LC91一样
//                  LC1416 https://leetcode.cn/problems/restore-the-array/description/  
//          进阶:    LC2472 https://leetcode.cn/problems/maximum-number-of-non-overlapping-palindrome-substrings/description/  
//                  LC1105 https://leetcode.cn/problems/filling-bookcase-shelves/description/  这题其实是 "划分型dp" , 从划分型dp的角度出发分析, 其实很简单
//          难题:   LC2547 https://leetcode.cn/problems/minimum-cost-to-split-an-array/description/  常规O(n ^ 2)的方法可过, 但是还有O(n * logn)的状态转移优化 + 线段树优化 的方法
//                  TODO: 上题如果不使用状态转移优化, 能不能直接使用线段树优化???  -- 暂时认为不行     还要多想想优化状态转移方程之后的线段树优化的做法, 好好理解一下
//                  LC2430 https://leetcode.cn/problems/maximum-deletions-on-a-string/description/  其实不难, a不出来面壁思过
//                  LC1478 https://leetcode.cn/problems/allocate-mailboxes/description/  其实不复杂, 但是细节比较多, 需要好好考虑
//                  LC2463 https://leetcode.cn/problems/minimum-total-distance-traveled/description/  和上面那题结合起来看, 有相似之处, 也有不同的地方
//          超难:   LC2977 https://leetcode.cn/problems/minimum-cost-to-convert-string-ii/description/  字典树 + floyd + 划分型dp   好玩, 字典树用的太妙了, 以后多来看看
//  6.3 约束划分个数
//          注意: 这里常用的状态定义为: dp[i][j] 表示将数组[0, j]区间的元素划分成i个连续子数组, 此时...
//              之所以使用这种状态定义, 是因为有些题使用这种定义更利于优化
//          简单题:  LC410 https://leetcode.cn/problems/split-array-largest-sum/description/  
//                  LC1043 https://leetcode.cn/problems/partition-array-for-maximum-sum/description/  这题的限制是子数组长度, 还不是 "约束划分个数" , 直接秒了
//                  LC1745 https://leetcode.cn/problems/palindrome-partitioning-iv/description/   TODO: O(n ^ 2)的方法很简单, 但是有没有O(n)的做法呢???
//                  LC813  https://leetcode.cn/problems/largest-sum-of-averages/description/  O(k * n ^ 2)的方法同样很简单, 秒了
//                  LC1278 https://leetcode.cn/problems/palindrome-partitioning-iii/description/  还是简单题, 秒了
//                  LC1335 https://leetcode.cn/problems/minimum-difficulty-of-a-job-schedule/description/  简单题, 秒
//          进阶:    LC1478 https://leetcode.cn/problems/allocate-mailboxes/description/  在上一个部分中写过了
//                  LC1959 https://leetcode.cn/problems/minimum-total-space-wasted-with-k-resizing-operations/description/  如果能够想到从划分型dp的角度出发考虑, 其实并不难
//          难题:    LC1473 https://leetcode.cn/problems/paint-house-iii/description/  两种写法: 划分型dp & 状态转移优化, 这里的状态转移优化很巧妙, 优化后无需遍历最后一个划分区间的起始位置, 并且让求min的过程可以使用预处理优化, 进一步降低复杂度
//                  LC2478 https://leetcode.cn/problems/number-of-beautiful-partitions/description/  前缀和优化, 两种前缀和的写法, 不是很好想明白 TODO: 以后多来看看
//                  LC3077 https://leetcode.cn/problems/maximum-strength-of-k-disjoint-subarrays/description/  推式子优化, 优化很巧妙, 多看看 TODO: 多看看这种优化
//                  LC2911 https://leetcode.cn/problems/minimum-changes-to-make-k-semi-palindromes/description/  难点在预处理上, 要预处理半回文串就要预处理真因子, 预处理真因子使用贡献法能够有更低的复杂度
//  6.4 不相交区间: 这类题主要的特征是 给你一些确定的区间
//                  LC2830 https://leetcode.cn/problems/maximize-the-profit-as-the-salesman/description/  两种做法, 从下标的角度出发考虑 & 从区间的角度出发考虑
//                  LC2008 https://leetcode.cn/problems/maximum-earnings-from-taxi/description/  和上一题一模一样
//                  LC1235 https://leetcode.cn/problems/maximum-profit-in-job-scheduling/description/  和前几题还是一眼, 只不过使用第一种方法会MLE, 因为entTime范围能到1e9, 所以只能使用第二种方法
//                  LC1751 https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended-ii/description/  和上面几题类似, 但是有一些小细节需要注意, 其实并不难
// 7. 其他线性dp
//  7.1 一维
//                  LC2944 https://leetcode.cn/problems/minimum-number-of-coins-for-fruits/description/  从前往后的dp/从后往前的dp + 单调队列优化
//                  LC2140 https://leetcode.cn/problems/solving-questions-with-brainpower/description/  填表法 & 刷表法
//                  LC983 https://leetcode.cn/problems/minimum-cost-for-tickets/description/  TODO: 我写的复杂度是O(n * logn), 是否有更快的写法? 比如O(n)?
//                  LC2901 https://leetcode.cn/problems/longest-unequal-adjacent-groups-subsequence-ii/description/  构造输出具体方案
//          难题:    LC2896 https://leetcode.cn/problems/apply-operations-to-make-two-strings-equal/description/  "均摊法"很妙, 能够将第一种操作的代价分散到某一个下标, 从而不需要确定j的位置, 妙
//                  LC2167 https://leetcode.cn/problems/minimum-time-to-remove-all-cars-containing-illegal-goods/description/  前后缀分解典型题目
//                  LC2188 https://leetcode.cn/problems/minimum-time-to-finish-the-race/description/  难点1: 如何计算maxLaps上限, 难点2: 如何解决cur, sum的溢出问题, 难点3: 如何正确初始化, 使得初始化能够保证后续的填表是正确的
//  7.2 特殊子序列
//                  LC2501 https://leetcode.cn/problems/longest-square-streak-in-an-array/description/  
//                  LC1218 https://leetcode.cn/problems/longest-arithmetic-subsequence-of-given-difference/description/  两种写法: 基于下标O(n ^ 2) & 基于值域O(n), 体会两种写法的差别
//                  LC1027 https://leetcode.cn/problems/longest-arithmetic-subsequence/description/  多种写法
//                  LC873 https://leetcode.cn/problems/length-of-longest-fibonacci-subsequence/description/  和上面几题类似
//                  