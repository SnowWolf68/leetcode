// 字符串哈希
//  LC187 https://leetcode.cn/problems/repeated-dna-sequences/description/      单哈希(MOD取的不合适就会出现哈希冲突), 双哈希(哈希冲突的概率就小很多)
//  LC472 https://leetcode.cn/problems/concatenated-words/description/      使用HashSet也能做, 但是需要注意, DP中有一个额外条件, 这个额外条件很容易被忽略
//  LC1044 https://leetcode.cn/problems/longest-duplicate-substring/description/    使用HashSet会超时, 单字符串哈希和双字符串哈希我都写了, 注意单字符串哈希为了避免哈希冲突, p和MOD的值需要仔细选取
//  LC686 https://leetcode.cn/problems/repeated-string-match/description/       比较简单, 巧妙之处在于 如何分析一共有几种可能的匹配情况
//  LC3213 https://leetcode.cn/problems/construct-string-with-minimum-cost/description/