// 图论
//      1. Dijkstra
//          板子题:             LC743 https://leetcode.cn/problems/network-delay-time/description/
//          板子题稍微改动一下:  LC3112 https://leetcode.cn/problems/minimum-time-to-visit-disappearing-nodes/description/   需要理解dijkstra的本质, 挺有意思的
//                             LC3341 & LC3342 网格图中dijkstra的应用   
//                             LC3341 https://leetcode.cn/problems/find-minimum-time-to-reach-last-room-i/description/
//                                 - 和LC3341类似的题: LC2577 https://leetcode.cn/problems/minimum-time-to-visit-a-cell-in-a-grid/description/      这题和LC3341类似, 但是也有一些不同, 需要看清楚, 并且这题中为了达到等待的效果, 用了一个很巧妙的小技巧     学习了灵神的二分 + bfs的做法, 一次偶然的Hack!, 详情见LC2577_hack.md
//                              LC3342 https://leetcode.cn/problems/find-minimum-time-to-reach-last-room-ii/description/    关键是如何处理每一步花费的时间, 剩下的问题就是LC3341
//                             LC2642 https://leetcode.cn/problems/design-graph-with-shortest-path-calculator/description/   纯板子题
//                             LC1514 https://leetcode.cn/problems/path-with-maximum-probability/description/    注意double类型的比较要用 Double.compare(), 直接减再转int太慢
//                             LC1631 https://leetcode.cn/problems/path-with-minimum-effort/description/    网格图 -> 图 的经典应用
//                             LC778  https://leetcode.cn/problems/swim-in-rising-water/description/    和LC3341, LC3342差不多, 只不过初始化需要注意一下
//         Dijkstra + 其他应用: 
//                             LC1786 https://leetcode.cn/problems/number-of-restricted-paths-from-first-to-last-node/description/      是个好题, dijksra + dp, 这里dp的顺序需要好好想清楚, 并且这题还有卡INF = 0x3f3f3f3f的数据
//                             LC3123 https://leetcode.cn/problems/find-edges-in-shortest-paths/description/  注意bfs要加vis, 否则时间复杂度能到O(n ^ 2), 同一个节点重复入队没有意义
//                             LC1976 https://leetcode.cn/problems/number-of-ways-to-arrive-at-destination/   和上面那题一起看, 考虑这题为什么不能用bfs序进行dp?    这题要用到拓扑序, 可以用正序也可以用逆序
//      
//          特殊图上的Dijkstra:  LC2662 https://leetcode.cn/problems/minimum-cost-of-a-path-with-special-roads/description/
//                             LC3377 https://leetcode.cn/problems/digit-operations-to-make-two-integers-equal/description/     也算是抽象图上的dijkstra, 不过实现起来有点复杂  还要想清楚这题为什么不能dp
//                              
//                              
//        分层图最短路:          LCP35 https://leetcode.cn/problems/DFPeFJ/description/  有一个挺有意思的剪枝
//                     
//        图上拓扑序DP:         LC2050 https://leetcode.cn/problems/parallel-courses-iii/description/   板子题
//                            LC1857 https://leetcode.cn/problems/largest-color-value-in-a-directed-graph/description/     是个好题, 这种dp的状态定义方式虽然很简单, 但是我也没想到, 再好好品味一下     路径相关的问题是不是就要往拓扑序dp上考虑??
//                  TODO:     LC3543 https://leetcode.cn/problems/maximum-weighted-k-edge-path/description/     时间复杂度怎么分析?   
//                  