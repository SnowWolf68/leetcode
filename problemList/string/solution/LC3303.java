package problemList.string.solution;

import java.util.Arrays;

/**
题目简化: 在s中找一个起始下标最小的子串, 使得这个子串至多修改一个字符之后, 和pattern相等


令m = s.length(), n = pattern.length()
枚举s中长度为n的子串的起始下标, 假设为i, 那么这个子串的结束下标为i + n - 1
这个子串[i, i + n - 1]需要满足: 至多修改一个字符, 修改后和pattern相等

假设子串的前缀和pattern的匹配长度为len1, 子串的后缀和pattern匹配的长度为len2, 如果len1 + len2 >= n
那么说明[i, i + n - 1]区间的子串, 至多修改一个字符之后, 能够和pattern相等

由于我们是从前往后遍历的子串起始下标, 因此这样求出的子串起始下标一定是字典序最小的那个

如何求子串的前缀/后缀和pattern的前/后缀的最长匹配长度?
    -- hint1: 拼接: pattern + s
    -- hint2: z-func

感觉这个方法真的很难想出来
 */
public class LC3303 {
    public int minStartingIndex(String s, String pattern) {
        int m = s.length(), n = pattern.length();
        String ss = pattern + s, ssr = new StringBuilder(s + pattern).reverse().toString();
        int[] z1 = zfunc(ss), z2 = zfunc(ssr);
        System.out.println(Arrays.toString(z1));
        System.out.println(Arrays.toString(z2));
        for(int i = 0;i < m;i++){
            int len1 = z1[i + n], len2 = z2[m - 1 - (i + n - 1) + n];
            System.out.println(len1 + " " + len2);
            if(len1 + len2 + 1 >= n) return i;
        }
        return -1;
    }

    private int[] zfunc(String s){
        int n = s.length();
        int[] z = new int[n];
        z[0] = n;
        int zLeft = -1, zRight = -1;
        for(int i = 1;i < n;i++){
            if(i < zRight){
                z[i] = Math.min(zRight - i + 1, z[i - zLeft]);
            }
            while(i + z[i] < n && s.charAt(i + z[i]) == s.charAt(z[i])) {
                z[i]++;
                zLeft = i;
                zRight = i + z[i] - 1;
            }
        }
        return z;
    }
    public static void main(String[] args) {
        String s = "abcdefg", pattern = "bcdffg";
        System.out.println(new LC3303().minStartingIndex(s, pattern));
    }
    /**
    pattern = bcdffg
          s = abcdefg
    
     */
}
