// misc
//
//  跳跃游戏 O(n)的解法
//      跳跃游戏模版: LC45 https://leetcode.cn/problems/jump-game-ii/description/
//      进阶:       LC1326 https://leetcode.cn/problems/minimum-number-of-taps-to-open-to-water-a-garden/description/
//                 LC1024 https://leetcode.cn/problems/video-stitching/description/
//          跳跃游戏和字符串哈希的结合: LC3292 https://leetcode.cn/problems/minimum-number-of-valid-strings-to-form-target-ii/description/  代码见stringHash文件夹
//  
//
//  交替组: 
//      基础版(无环, 无修改, size = 2): LC3101 https://leetcode.cn/problems/count-alternating-subarrays/description/
//      进阶1(带环, 无修改, size = 3): LC3206 https://leetcode.cn/problems/alternating-groups-i/description/    实际上就是下一题的 size = 3 的特殊情况
//      进阶2(带环, 无修改): LC3208 https://leetcode.cn/problems/alternating-groups-ii/description
//      终极版(带环, 带修改, 多次查询): LC3245 https://leetcode.cn/problems/alternating-groups-iii/description/