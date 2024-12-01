package problemList.dp.solution;

/**
枚举连续的0和连续的1的分割点, 分别处理每一个前缀处理成全0 和 每一个后缀处理成全1 所需要的最少翻转次数

计算某一个前缀处理成全0/后缀处理成全1 所需要的最少翻转次数: 只需要一次简单的遍历即可    这里你也可以将这个过程理解为 递推
为了代码简洁, 这里我使用类似dp中辅助节点的思想, 给prefix和suffix分别在开头和结尾添加一个辅助节点
 */
public class LC926 {
    public int minFlipsMonoIncr(String s) {
        int n = s.length();
        int[] prefix = new int[n + 1], suffix = new int[n + 1];
        for(int i = 1;i <= n;i++){
            prefix[i] = s.charAt(i - 1) == '0' ? prefix[i - 1] : prefix[i - 1] + 1;
        }
        for(int i = n - 1;i >= 0;i--){
            suffix[i] = s.charAt(i) == '1' ? suffix[i + 1] : suffix[i + 1] + 1;
        }
        int ret = suffix[0];
        for(int i = 0;i < n;i++){
            // 前后缀分解: 分解成 [0, i] 和 [i + 1, n - 1]
            ret = Math.min(ret, prefix[i + 1] + suffix[i + 1]);
        }
        return ret;
    }
}
