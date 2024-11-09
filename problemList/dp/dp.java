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
//      LC871 https://leetcode.cn/problems/minimum-number-of-refueling-stops/description/
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
//
//      难题: Bitset优化0-1背包DP 附Java手写Bitset板子 LC3181 https://leetcode.cn/problems/maximum-total-reward-using-operations-ii/description/?envType=problem-list-v2&envId=z2BnCgtB
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
//             LC3290 https://leetcode.cn/problems/maximum-multiplication-score/    看起来可能比较复杂, 其实也是板子题
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
// 5. 状态机DP
//          LC3335 https://leetcode.cn/problems/total-characters-in-string-after-transformations-i/description/     状态表示不好想
//          LC3259 https://leetcode.cn/problems/maximum-energy-boost-from-two-drinks/description/
//          
//      矩阵快速幂优化DP LC3337 https://leetcode.cn/problems/total-characters-in-string-after-transformations-ii/description/
//
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
//                  LC3332 https://leetcode.cn/problems/maximum-points-tourist-can-earn/description/        比较明显的dp
//                  LC2944 https://leetcode.cn/problems/minimum-number-of-coins-for-fruits/description/  从前往后的dp/从后往前的dp + 单调队列优化
//                  LC2140 https://leetcode.cn/problems/solving-questions-with-brainpower/description/  填表法 & 刷表法
//                  LC983 https://leetcode.cn/problems/minimum-cost-for-tickets/description/  TODO: 我写的复杂度是O(n * logn), 是否有更快的写法? 比如O(n)?
//                  LC2901 https://leetcode.cn/problems/longest-unequal-adjacent-groups-subsequence-ii/description/  构造输出具体方案
//                  LC3144 https://leetcode.cn/problems/minimum-substring-partition-of-equal-character-frequency/description/       感觉其实就是一个简单的划分型DP
//          难题:    LC2896 https://leetcode.cn/problems/apply-operations-to-make-two-strings-equal/description/  "均摊法"很妙, 能够将第一种操作的代价分散到某一个下标, 从而不需要确定j的位置, 妙
//                  LC2167 https://leetcode.cn/problems/minimum-time-to-remove-all-cars-containing-illegal-goods/description/  前后缀分解典型题目
//                  LC2188 https://leetcode.cn/problems/minimum-time-to-finish-the-race/description/  难点1: 如何计算maxLaps上限, 难点2: 如何解决cur, sum的溢出问题, 难点3: 如何正确初始化, 使得初始化能够保证后续的填表是正确的
//  7.2 特殊子序列
//                  LC2501 https://leetcode.cn/problems/longest-square-streak-in-an-array/description/  
//                  LC1218 https://leetcode.cn/problems/longest-arithmetic-subsequence-of-given-difference/description/  两种写法: 基于下标O(n ^ 2) & 基于值域O(n), 体会两种写法的差别
//                  LC1027 https://leetcode.cn/problems/longest-arithmetic-subsequence/description/  多种写法
//                  LC873 https://leetcode.cn/problems/length-of-longest-fibonacci-subsequence/description/  和上面几题类似
//                  LC446 https://leetcode.cn/problems/arithmetic-slices-ii-subsequence/description/  
//                  LC1048 https://leetcode.cn/problems/longest-string-chain/description/  两种状态转移的写法, 感觉第二种写法更优雅, 但是第一种写法更易懂
//          难题:   TODO: LC3098
//  7.3 矩阵快速幂优化: TODO
//  7.4 多维: 
//          简单题:  LC2400 https://leetcode.cn/problems/number-of-ways-to-reach-a-position-after-exactly-k-steps/description/
//                  LC2370 https://leetcode.cn/problems/longest-ideal-subsequence/description/  用到了字符串dp的另一种巧妙的状态表示, 这种状态表示有一些细节需要好好考虑, 详情可以看代码中的注释
//                  LC1269 https://leetcode.cn/problems/number-of-ways-to-stay-in-the-same-place-after-some-steps/description/  可能会MLE, TLE, 如何巧妙优化?? 这里的优化其实很简单, 但是有点巧妙
//                  LC3122 https://leetcode.cn/problems/minimum-number-of-operations-to-satisfy-conditions/description/
//                  LC576 



// 9. 状压DP
//  9.1 排列型①相邻无关
//          简单题: LC526 https://leetcode.cn/problems/beautiful-arrangement/description/
//                 LC1879 https://leetcode.cn/problems/minimum-xor-sum-of-two-arrays/description/
//                 LC2850 https://leetcode.cn/problems/minimum-moves-to-spread-stones-over-grid/description/ 关键在于转化, 转化完之后就和上面那题一样   TODO: 研究一下这题的 最小费用最大流 做法
//                 LC1947 https://leetcode.cn/problems/maximum-compatibility-score-sum/description/  和前几题差不多 简单题
//                 LC1799 https://leetcode.cn/problems/maximize-score-after-n-operations/description/  稍微抽象一点的状压DP, 但是好好想想其实也还是 排列 问题
//          难题:   LC2172 https://leetcode.cn/problems/maximum-and-sum-of-array/description/   题意转化很关键, 考虑清楚如何抽象出来 "排列" , 好好考虑两种优化思路
//  9.2 排列型②相邻相关
//          简单题: LC996 https://leetcode.cn/problems/number-of-squareful-arrays/description/      关键在于如何去重
//                 LC2741 https://leetcode.cn/problems/special-permutations/description/    也算是板子题, 这题使用dfs好像会快很多, 因为dp会计算到一些不可能的情况, 而dfs对于这些不可能的情况可以通过剪枝剪掉
//       稍微难一点: LC1681 https://leetcode.cn/problems/minimum-incompatibility/description/   枚举子集
//           难题: LC3149 https://leetcode.cn/problems/find-the-minimum-cost-array-permutation/description/    难点在于构造具体方案, 我分别写了 "正序" 以及 "倒序" 的两种状压DP写法
//  9.3 旅行商问题(TSP) 本质上就是 排列型②
//          有点难: LC943 https://leetcode.cn/problems/find-the-shortest-superstring/description/    1) 如何计算commLen?   2) 如何构造最后的具体方案? 
//          简单题: LC847 https://leetcode.cn/problems/shortest-path-visiting-all-nodes/description/     这题其实并不难, 就是一个floyd + 最基本的 相邻相关 排列型 状压DP
//           难题:  LCP13 https://leetcode.cn/problems/xun-bao/description/     说难也难, 说简单也简单, 关键是要把题目分析清楚, 细节比较多, 实现比较复杂, 但是思路并不难
//  9.4 枚举子集的子集
//          简单题: LC2305 https://leetcode.cn/problems/fair-distribution-of-cookies/description/    最基本的 枚举子集的子集 的状压DP
//                 LC1986 https://leetcode.cn/problems/minimum-number-of-work-sessions-to-finish-the-tasks/description/     也是简单题, 但是注意理解 这题的状态定义, 和 上一题的空间优化版本的状态定义的区别    为什么上一题必须要枚举i? 好好想想
//         
//
//  其他类型的状压DP: 
//          LC638 https://leetcode.cn/problems/shopping-offers/description/     看起来并不是很难, 但是我写的状态压缩跑的很慢, 我也不知道为什么
//
// 10. 数位DP
//      灵神数位DP模版 v1.0  LC2376 https://leetcode.cn/problems/count-special-integers/description/
//      灵神数位DP模版 v2.0  (v1.0和v2.0都可以做, 但是v2.0更好) LC2999 https://leetcode.cn/problems/count-the-number-of-powerful-integers/description/
//          LC2719 https://leetcode.cn/problems/count-of-integers/description/      v2.0版本, 搞懂了上面那个模版, 这题很简单
//          LC788 https://leetcode.cn/problems/rotated-digits/description/      v1.0版本
//          LC902 https://leetcode.cn/problems/numbers-at-most-n-given-digit-set/description/     v1.0版本, 很简单
//          LC233 https://leetcode.cn/problems/number-of-digit-one/description/     理解cnt含义, memo[][]第二个维度应该开多大
//          LC600 https://leetcode.cn/problems/non-negative-integers-without-consecutive-ones/description/      简单题
//          
//


// unsorted 
//      扔鸡蛋问题: 重复子问题不大好想
//      两个鸡蛋  LC1884 https://leetcode.cn/problems/egg-drop-with-2-eggs-and-n-floors/description/?envType=daily-question&envId=2024-10-13
//      多个鸡蛋  LC887 https://leetcode.cn/problems/super-egg-drop/?envType=daily-question&envId=2024-10-14
//      
//      逆序对问题: 
//          基础版: LC629 https://leetcode.cn/problems/k-inverse-pairs-array/description/       这题的状态表示不好想, 状态转移也不好想, 为什么是枚举逆序对数量, 而不是枚举最后一个填的元素? 关键还是要能够分析出 重复子问题, 从重复子问题出发考虑状态定义以及状态转移
//          加强版: LC3193 https://leetcode.cn/problems/count-the-number-of-inversions/description/     上面那题的加强版
//    其他的版本, 这题太有意思了: LC903 https://leetcode.cn/problems/valid-permutations-for-di-sequence/    如何将不是子问题的问题, 转化成原问题的子问题, 用到了类似 离散化 的思想, 或者说 平移区间 的思想? 这题的思想很好, 第一次见, 很有意思, 很巧妙
//          LC1866 https://leetcode.cn/problems/number-of-ways-to-rearrange-sticks-with-k-sticks-visible/description/   重复子问题以及状态表示有点难想, 要想找重复子问题, 也需要使用类似上一题中的思想, 将[2, 3, ..., i] 区间的问题 转化成 [1, 2, ..., i - 1] 区间的问题
//
//
//
//
//      正难则反 + 前缀和优化DP  LC3333 https://leetcode.cn/problems/find-the-original-typed-string-ii/description/
//
//      线段树上DP 第一次见, 思路很巧妙 将线段树的build和up与dp的状态转移结合  LC3165 https://leetcode.cn/problems/maximum-sum-of-subsequence-with-non-adjacent-elements/description/
//
//
//      比较奇怪的dp
//          LC3202 https://leetcode.cn/problems/find-the-maximum-length-of-valid-subsequence-ii/description/    这题dp表两个维度的遍历顺序比较奇怪, 很容易写错

// 树形DP
//  1. 数的直径
//      LC543 https://leetcode.cn/problems/diameter-of-binary-tree/description/
//      LC124 https://leetcode.cn/problems/binary-tree-maximum-path-sum/description/    稍难, 要考虑左/右子节点出发的路径不选的情况
//
//      LC1373 https://leetcode.cn/problems/maximum-sum-bst-in-binary-tree/description/     单论树形DP, 并不难, 难的是计算当前节点返回的ret[]的逻辑
//      