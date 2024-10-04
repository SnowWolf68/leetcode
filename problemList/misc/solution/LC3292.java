package problemList.misc.solution;

import java.util.HashSet;
import java.util.Set;

/**
将题目转化成 刷表法 类型的 划分型DP 之后
由于划分型DP的时间复杂度是O(n ^ 2), 在本题的数据范围下还是会T

因此需要找一个更优的解法

我们可以将划分型DP的过程, 看作是 "跳跃游戏" 的变种, 因此可以使用类似 跳跃游戏 的方法解决
因此问题转化为了: 对于某一个下标, 怎么确定在这个位置最多能够向右跳多远, 或者最多能够跳到右边的哪一个下标?
    ps: 在本题的数据范围下, 这个过程的时间复杂度必须是要低于O(n)的
如果使用Trie树, 这个过程是O(n)的, 如果想要找到一个比O(n)更低的解法, 可以使用 二分长度 + 字符串哈希
我们可以二分所有可能的长度, 每次二分到某一个长度之后, 需要在O(1)的时间内判断这个长度是否是可以跳到的长度 如何实现?
可以使用字符串哈希, 将前缀按照长度分组, 分组之后分别计算出每一组中所有前缀各自的哈希值
每次判断的时候, 可以直接当前子串的哈希值是否存在于对应长度的前缀哈希值集合中, 以此判断当前划分出来的子串是否是一个前缀

时间复杂度: 跳跃游戏: O(n), 每次都要进行二分: O(logn), 总的时间复杂度: O(n * logn)
 */
public class LC3292 {
    public int minValidStrings(String[] words, String target) {
        int maxLen = (int)(5 * 1e4), n = target.length();
        // System.out.println("target.length = " + target.length());
        Set<Long>[] hash = new HashSet[maxLen + 1];
        for(int i = 0;i <= maxLen;i++) hash[i] = new HashSet<>();
        int p = 131313;     // p取13或17, 在测试数据中都会发生哈希冲突
        long mod = (long)1e9 + 7;
        for(String word : words){
            StringHash stringHash = new StringHash(word, p, mod);
            for(int i = 0;i < word.length();i++){
                long hashValue = stringHash.getHashValue(0, i);
                hash[i + 1].add(hashValue);
            }
        }
        int[] rightStep = new int[n];
        StringHash stringHash = new StringHash(target, p, mod);
        for(int i = 0;i < n;i++){
            // 二分查找左区间的右边界 二分长度
            int l = 0, r = n - i;
            while(l < r){
                int mid = (l + r + 1) >> 1;
                // System.out.println(mid + " " + (i + mid - 1));
                if(hash[mid].contains(stringHash.getHashValue(i, i + mid - 1))) l = mid;
                else r = mid - 1;
            }
            // 二分得到的l就是最长长度
            rightStep[i] = l;
        }
        // 跳跃游戏
        int curR = 0, nxtR = 0, cnt = 0;
        for(int i = 0;i <= n;i++){
            if(i != n) nxtR = Math.max(nxtR, rightStep[i] + i);
            if(curR == i && i != n){
                if(curR == nxtR) return -1;
                curR = nxtR;
                cnt++;
            }
        }
        return cnt;
    }

    public class StringHash {
        private long p;
        private long MOD;

        private int n;          // 字符串长度
        private long[] pre;      // 哈希值前缀
        private long[] pow;      // pow[i] = p^i

        StringHash (String str, long p, long mod) {
            this.p = p;
            this.MOD = mod;
            this.n = str.length();

            pre = new long[n + 1];
            pow = new long[n + 1];

            pre[0] = 0;
            pow[0] = 1;

            for(int i = 1;i <= n;i++){
                pow[i] = (pow[i - 1] * p) % MOD;
            }

            for(int i = 1;i <= n;i++){
                pre[i] = ((pre[i - 1] * p) % MOD + str.charAt(i - 1)) % MOD;
            }
        }

        long getHashValue(int l, int r){
            return ((pre[r + 1] - (pre[l] * pow[r - l + 1]) % MOD) + MOD) % MOD;
        }

        long rotate(int l) {
            if (l < 0 || l >= n - 1) {
                return getHashValue(0, n - 1);
            } else {
                long hash1 = getHashValue(0, l);
                long hash2 = getHashValue(l + 1, n - 1);
                return (hash2 * pow[l + 1] % MOD + hash1) % MOD;
            }
        }

        static long evaluate(String s, int p, long MOD){
            long hash = 0;
            for(char c : s.toCharArray()){
                hash = ((hash * p) % MOD + c) % MOD;
            }
            return hash;
        }
    }
}
