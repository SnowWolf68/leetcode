package problemList.dp.solution;

import java.util.ArrayList;
import java.util.List;

/**
前后缀分解的简单题 (虽然我不看题解也没想出来...)
首先递推统计每一天前有多少天是非递增的, 再用类似的方法统计每一天之后有多少天是非递减的, 然后枚举分割点即可
 */
public class LC2100 {
    public List<Integer> goodDaysToRobBank(int[] security, int time) {
        int n = security.length;
        int[] prefix = new int[n], suffix = new int[n];
        for(int i = 1;i < n;i++) prefix[i] = security[i] <= security[i - 1] ? prefix[i - 1] + 1 : 0;
        for(int i = n - 2;i >= 0;i--) suffix[i] = security[i] <= security[i + 1] ? suffix[i + 1] + 1 : 0;
        List<Integer> ret = new ArrayList<>();
        for(int i = time;i < n - time;i++){
            if(prefix[i] >= time && suffix[i] >= time) ret.add(i);
        }
        return ret;
    }
}
