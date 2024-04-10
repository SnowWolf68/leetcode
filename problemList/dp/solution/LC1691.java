package problemList.dp.solution;

import java.util.*;

/**
这题的关键是如何处理 "可以旋转" 这个条件
假设这里有两个长方体, l1, w1, h1, l2, w2, h2, 如果第二个长方体能够放在第一个长方体上面(可以旋转), 求两个长方体三个维度需要满足的条件
猜想: 首先我们对每一个长方体的长宽高升序排序, 那么此时一定有l2 <= l1, w2 <= w1, h2 <= h1
证明: 反证法, 这里我们假设此时有l2 > l1 或 w2 > w1 或 h2 > h1, 注: 此时依然满足对于单个长方体来说, 其长宽高满足升序排序
    即此时的前提是: l1 <= w1 <= h1, l2 <= w2 <= h2
    分三种情况讨论: 
        1. l2 > l1: 由于l2 <= w2 <= h2, 即h2 >= w2 >= l2 > l1, 那么此时显然l1这个维度无法符合要求
        2. w2 > w1: 同理有h2 >= w2 > w1 >= l1, 即此时h2, w2中至少有一个维度无法符合要求
        3. h2 > h1: 同理有h2 > h1 >= w1 >= l1, 即此时h2这个维度无法符合要求
    因此假设不成立, 因此必须满足l2 <= l1, w2 <= w1, h2 <= h1
这样就变成了一个三维的LIS问题, 我们只需要继续三关键字排序 + dp即可, 时间复杂度: O(n ^ 2)

TODO: 如果想要优化, 也可以用二维树状数组/线段树优化到O(n * logn), 我还没整明白, 留一个TODO
 */
public class LC1691 {
    public int maxHeight(int[][] cuboids) {
        int n = cuboids.length;
        for(int[] c : cuboids) Arrays.sort(c);
        List<int[]> info = new ArrayList<>();
        for(int[] c : cuboids){
            info.add(new int[]{c[0], c[1], c[2]});
        }
        Collections.sort(info, (o1, o2) -> o1[0] == o2[0] ? (o1[1] == o2[1] ? o1[2] - o2[2] : o1[1] - o2[1]) : o1[0] - o2[0]);
        int[] dp = new int[n];
        for(int i = 0;i < n;i++){
            dp[i] = info.get(i)[2];
            for(int j = 0;j < i;j++){
                // 注意这里我们需要保证后两个维度都满足要求
                if(info.get(j)[2] <= info.get(i)[2] && info.get(j)[1] <= info.get(i)[1]) dp[i] = Math.max(dp[i], dp[j] + info.get(i)[2]);
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }
}
