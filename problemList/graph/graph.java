// 图论
//      1. Dijkstra
//          板子题:             LC743 https://leetcode.cn/problems/network-delay-time/description/
//          板子题稍微改动一下:  LC3112 https://leetcode.cn/problems/minimum-time-to-visit-disappearing-nodes/description/   需要理解dijkstra的本质, 挺有意思的
//                             LC3341 & LC3342 网格图中dijkstra的应用   
//                             LC3341 https://leetcode.cn/problems/find-minimum-time-to-reach-last-room-i/description/
//                                 - 和LC3341类似的题: LC2577 https://leetcode.cn/problems/minimum-time-to-visit-a-cell-in-a-grid/description/      这题和LC3341类似, 但是也有一些不同, 需要看清楚, 并且这题中为了达到等待的效果, 用了一个很巧妙的小技巧
//                              LC3342 https://leetcode.cn/problems/find-minimum-time-to-reach-last-room-ii/description/    关键是如何处理每一步花费的时间, 剩下的问题就是LC3341
//                             LC2642 https://leetcode.cn/problems/design-graph-with-shortest-path-calculator/description/   纯板子题
//                             LC1514 https://leetcode.cn/problems/path-with-maximum-probability/description/    注意double类型的比较要用 Double.compare(), 直接减再转int太慢
//                             LC1631 https://leetcode.cn/problems/path-with-minimum-effort/description/    网格图 -> 图 的经典应用
//                             LC1786 https://leetcode.cn/problems/number-of-restricted-paths-from-first-to-last-node/description/      是个好题, dijksra + dp, 这里dp的顺序需要好好想清楚, 并且这题还有卡INF = 0x3f3f3f3f的数据
//                              