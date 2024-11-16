// 滑动窗口
//
//      滑动窗口求最小值:   LC76 https://leetcode.cn/problems/minimum-window-substring/description/
//      滑动窗口计数:   
//                  至少型滑动窗口: (板子题) LC2962 https://leetcode.cn/problems/count-subarrays-where-max-element-appears-at-least-k-times/description/
//                                (恰好 -> 两个至少) LC3306 https://leetcode.cn/problems/count-of-substrings-containing-every-vowel-and-k-consonants-ii/description/
//                                (巧妙的优化) LC3298 https://leetcode.cn/problems/count-substrings-that-can-be-rearranged-to-contain-a-string-ii/description/

// 二: 不定长滑动窗口
//      2.1 求最长/最大     --> 窗口越短越合法, 求最长的窗口
//          LC3 https://leetcode.cn/problems/longest-substring-without-repeating-characters/description/
//          LC3090 https://leetcode.cn/problems/maximum-length-substring-with-two-occurrences/description/
//          LC1493 比较显然的转化 https://leetcode.cn/problems/longest-subarray-of-1s-after-deleting-one-element/description/
//          LC1208 https://leetcode.cn/problems/get-equal-substrings-within-budget/description/
//          LC2730 https://leetcode.cn/problems/find-the-longest-semi-repetitive-substring/description/
//          LC904 https://leetcode.cn/problems/fruit-into-baskets/description/
//          LC1695 https://leetcode.cn/problems/maximum-erasure-value/description/
//          LC2958 https://leetcode.cn/problems/length-of-longest-subarray-with-at-most-k-frequency/description/
//          LC2779 对数组排序, 将子序列转化成子数组很关键, 滑窗也和最原始的滑窗有些差别 https://leetcode.cn/problems/maximum-beauty-of-an-array-after-applying-operation/description/
//          LC2024 比较显然的转化 https://leetcode.cn/problems/maximize-the-confusion-of-an-exam/description/
//          LC1004 https://leetcode.cn/problems/max-consecutive-ones-iii/description/
//          LC1658 比较显然的转化 https://leetcode.cn/problems/minimum-operations-to-reduce-x-to-zero/description/
//          LC1838 这题竟然也能用滑窗, 巧妙至极 https://leetcode.cn/problems/frequency-of-the-most-frequent-element/description/
//          LC2516 https://leetcode.cn/problems/take-k-of-each-character-from-left-and-right/description/
//          LC2831 O(n * logn)和O(n) 两种写法 https://leetcode.cn/problems/find-the-longest-equal-subarray/description/
//  
//          LC2271 好题 难题 对下标进行滑窗 https://leetcode.cn/problems/maximum-white-tiles-covered-by-a-carpet/
//          LC2106 难题 好题 也是对下标数组进行的滑窗 并且这个滑窗也很巧妙, 很难看出来是滑窗 https://leetcode.cn/problems/maximum-fruits-harvested-after-at-most-k-steps/description/
//          LC2555 难题 https://leetcode.cn/problems/maximize-win-from-two-segments/description/
//          LC2271, LC2106, LC2555 其实都很像, 可以放在一起看, 都可以看作是 对元素的下标的滑窗(即 要滑窗的数组nums[i]表示第i个元素所在的位置, 或者理解为对元素下标数组的滑窗), 而不是对元素所在位置的下标的滑窗
//
//          LC2009 https://leetcode.cn/problems/minimum-number-of-operations-to-make-array-continuous/description/
//
//      2.2 求最短/最小     --> 窗口越长越合法, 求最短的窗口
//          基础题  LC209 https://leetcode.cn/problems/minimum-size-subarray-sum/description/
//                 LC2904 https://leetcode.cn/problems/shortest-and-lexicographically-smallest-beautiful-string/description/
//          进阶    LC1234 https://leetcode.cn/problems/replace-the-substring-for-balanced-string/description/
//                 LC2875 https://leetcode.cn/problems/minimum-size-subarray-in-infinite-array/description/
//                 LC1574 https://leetcode.cn/problems/shortest-subarray-to-be-removed-to-make-array-sorted/description/     如何判断剩下的数组元素是非递减? 我的方法是维护逆序对
//                  
//          


//      越长越合法: CF1208B https://codeforces.com/contest/1208/problem/B
//
//      滑动窗口计数: LC3325 https://leetcode.cn/problems/count-substrings-with-k-frequency-characters-i/description/   比较简单的滑动窗口计数题, ret += left 
//      
//      越短越合法 滑动窗口计数  LC3258 https://leetcode.cn/problems/count-substrings-that-satisfy-k-constraint-i/description/
//
//      滑动窗口的巧妙应用: LC3261 https://leetcode.cn/problems/count-substrings-that-satisfy-k-constraint-ii/description/
//