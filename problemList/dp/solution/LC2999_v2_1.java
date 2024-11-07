package problemList.dp.solution;

import java.util.Arrays;

/**
灵神数位DP模版 v2.0

v1.0版本的缺点: v1.0计算带有上下限的数位dp时, 需要使用dfs(high) - dfs(low - 1)来得到最终结果
如果这里的high和low都比较大, 超过了int或long的表示范围, 需要用String来表示的话, 那么这里的low - 1就无法进行了
因此这里灵神推出了v2.0版本的模版
v2.0相比于v1.0, 最大的好处就是, 对于同时存在上下界的情况, 可以通过一次dfs, 就可以得到最终的答案

dfs参数: 
    1. int i, 当前填到的数位下标
    2. boolean isLimitHigh, 前面填的数字是否都是上界high中的每个数位
        如果isLimitHigh == true, 那么意味着当前要填的数上界至多就是 high[i] , 否则当前填的数的上界可以是 9
    3. boolean isLimitLow, 前面填的数字是否都是下界low中的每个数位
        和isLimitHigh类似, 如果isLimitLow == true, 那么意味着当前要填的数的下界至少是 low[i] , 否则当前填的数的下界可以是0
    4. isNumber: 前一位有没有填数字, 需要注意的是, 这个参数可以加也可以不加, 这需要看题目是不是需要这部分的信息 (即是否包含前导零的信息)
        如果不加isNumber, 那么这里的low就需要考虑取0的情况, 换句话说, 在枚举当前数位要填的数字d的时候, 就可能填到0, 需要考虑0的情况
        如果加isNumber, 那么就需要分 1)当前数位不填 2)当前数位填 两种情况来分析, 那么显然在第二种情况下, 当前数位要填的数字d显然就不能取到0了
预处理的小tips: 
    对于start和finish来说, 有可能这两个数的长度不一样, 那么转成字符串之后, 字符串的长度也不一样, 那么在取high[i]或low[i]的过程中, 有可能越界
    因此对于low的长度小于high的情况, 我们可以手动在low的前面补0, 使得补完0之后的low和high的长度是对齐的, 方便后续的处理

记忆化需要记忆的状态: 和v1.0版本中的分析类似, 这里我们只需要记忆isLimitHigh == false && isLimitLow == false的状态
 */
public class LC2999_v2_1 {
    private String start, finish, s;
    private int n, limit;
    private long[] memo;
    public long numberOfPowerfulInt(long start, long finish, int limit, String s) {
        this.start = String.valueOf(start);
        this.finish = String.valueOf(finish);
        this.n = this.finish.length();
        this.limit = limit;
        this.start = "0".repeat(this.finish.length() - this.start.length()) + start;    // 填充前导0
        this.s = s;
        this.memo = new long[n];
        Arrays.fill(memo, -1);
        return dfs(0, true, true);
    }

    // 不考虑前导0的情况, 即在dfs的参数中, 不包含isNumber这一个参数
    private long dfs(int i, boolean isHighLimit, boolean isLowLimit){
        if(i == n){
            return 1;
        }
        if(!isHighLimit && !isLowLimit && memo[i] != -1) return memo[i];
        long ret = 0;
        int lo = isLowLimit ? (start.charAt(i) - '0') : 0, hi = isHighLimit ? Math.min((finish.charAt(i) - '0'), limit) : limit;
        if(i < n - s.length()){
            // 需要注意的是, 这里我们不区分 当前数位填 和 当前数位不填 两种情况, 因此这里填的数字可以是0
            for(int d = lo;d <= hi;d++){
                ret += dfs(i + 1, isHighLimit && d == (finish.charAt(i) - '0'), isLowLimit && d == (start.charAt(i) - '0'));
            }
        }else{
            int d = s.charAt(i - (n - s.length())) - '0';
            if(d >= lo && d <= hi){
                ret += dfs(i + 1, isHighLimit && d == (finish.charAt(i) - '0'), isLowLimit && d == (start.charAt(i) - '0'));
            }else{
                return 0;
            }
        }
        if(!isHighLimit && !isLowLimit) memo[i] = ret;
        return ret;
    }
}
