package problemList.dp.solution;

import java.util.Arrays;

/**
灵神的数位DP模版 v1.0

记忆化搜索: 
    dfs参数: 
        1. i: 当前填到了下标为i的数位
        2. mask: 之前填过的数字集合
        3. isLimit: 假设数字n对应的字符串为s
            那么 isLimit 表示前面填过的数字是否依次是s[0, i - 1]中的各个位
            如果前面填过的数字依次是s[0, i - 1]中的各个位, 那么当前下标为i的位填的数最大就是s[i]
            否则当前下标为i的位最大可以填9
        4. isNumber: 前面填的数字是否是有效数字 (开头的0看做是无效数字)
            因为开头的0可以看做无效数字, 不算在mask中, 但是在中间的有效的0就需要被看作是有效数字, 需要记录在mask中


首先把n转换成字符串的形式
dfs过程:    dfs(i, mask, isLimit, isNumber)     并且这里假设String s为整数n的字符串表示
    首先判断当前是否填到了最后, 如果已经填到了最后一个位置 (i == s.length())
    那么此时如果isNumber == false, 说明前面填的都是无效数字, 那么此时return 0;
    如果此时isNumber == true, 说明找到了一种合法的填法, return 1;
    对于当前位置 (下标为i的位置), 此时有两种选择
        1. 不填数字: 
            首选需要满足 isNumber == false , 即前一位也不填数字, 那么此时递归到dfs(i + 1, mask, false, false)
            其中由于当前位不填数字, 因此 isNumber 显然为false
            需要注意的是此时isLimit应该为什么
                这里由于当前这一位已经没填数字了, 因此显然接下来的一位可以填 0 ~ 9 中的任何数字, 因此isLimit = false
                举个例子: n = 1000, 当前i == 0, 并且当前这一位不填数字, 那么对于i == 1的这一位来说, 此时可以填 0 ~ 9 中的任何数字
        2. 填数字: 
            分析当前位填数字的上界: up = isLimit ? s[i] - '0' : 9;
                           下界: low = isNumber ? 0 : 1;
                                这里需要注意, 由于当前这种情况是一定要填数字, 因此如果前一位没填数字, 那么这一位填的数字至少是1, 而不能是0
                                类似的, 如果前一位填了数字, 那么这里填的数字就可以是0
            遍历范围中的所有可能的数字, 假设当前数字是d
            如果当前这个数字不在mask中 (满足 ((mask >> d) & 1) == 0 ), 那么可以填这个数字, dfs(i + 1, mask | (1 << d), isLimit & d == (s[i] - '0'), true);

初始状态: dfs(0, 0, true, false)
解释: 一开始显然 i == 0, mask == 0, 并且由于第一个数的上界显然要受到s[0]的限制, 因此isLimit = true, 并且由于第一个位置也可以不填数字, 因此isNumber = false

如何记忆化? 
    这里的dfs参数比较多, 我们首先需要分析一下对于哪些参数需要记忆化? 
        对于记忆化要包含什么参数, 这里其实有一个规则: 如果某一个参数代表的某一个状态, 在后面会被重复递归到, 那么这个参数就需要记忆化
        那么对于这里的dfs来说, 首先i和mask肯定要进行记忆化, 因为对于这两个参数来说, 后面肯定有可能被重复递归到某一个重复的值
            例如 n = 34, 那么递归到第一位填3, 第二位填2 之后, 后面有可能会递归到 第一位填2, 第二位填3 这种情况
        然后对于isLimit和isNumber, 这里情况就不一样了
        对于 isLimit == true 的情况, 只要之前递归到一次, 那么后面一定不会递归到同样的isLimit == true的情况
            比如 n = 345, 第一位填3, 第二位填4, 那么这种情况只会被递归到一次, 后续一定不会被递归到这种情况
        对于 isNumber == false 的情况也类似, 只要之前递归到过一次, 那么后面一定不会递归到同样的isNumber == true的情况
            比如 n = 345, 此时第一位不填, 第二位也不填, 这种情况只要递归到一次, 后续就永远不会被递归到
    
    综上, 我们只需要记忆 i, mask, isLimit = false, isNumber = true的情况, 因此这里memo[]记忆化数组只需要开二维即可
 */ 
public class LC2376 {
    private String s;
    private int[][] memo;
    public int countSpecialNumbers(int n) {
        s = Integer.toString(n);
        memo = new int[s.length()][1 << 10];
        for(int[] row : memo) Arrays.fill(row, -1);
        return dfs(0, 0, true, false);
    }

    private int dfs(int i, int mask, boolean isLimit, boolean isNumber){
        if(i == s.length()) return isNumber ? 1 : 0;
        if(!isLimit && isNumber && memo[i][mask] != -1) return memo[i][mask];
        int up = isLimit ? s.charAt(i) - '0' : 9, low = isNumber ? 0 : 1, ret = 0;
        if(!isNumber) ret += dfs(i + 1, mask, false, false);
        for(int d = low;d <= up;d++){
            if(((mask >> d) & 1) == 0){
                ret += dfs(i + 1, mask | (1 << d), isLimit & (d == (s.charAt(i) - '0')), true);
            }
        }
        if(!isLimit && isNumber) memo[i][mask] = ret;
        return ret;
    }
}
