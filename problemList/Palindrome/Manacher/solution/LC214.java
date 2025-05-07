package problemList.Palindrome.Manacher.solution;

/**
计算每个下标i的回文半径, 找到满足以下条件的最长回文半径l:
    从0到i的范围, 都在i的回文半径范围内, 那么只需要在字符串前面拼接i回文半径之外的字符即可
    需要满足的条件即: 
    1. 如果i是奇回文中心, 那么满足: l == i + 1
    2. 如果i是偶回文中心靠左的字符, 那么满足: l == i + 1
最终答案为:  n = s.length()
    1. 如果i是奇回文中心, 那么 s - (2 * l - 1)
    2. 如果i是偶回文中心, 那么 s - 2 * l
 */
public class LC214 {
    public String shortestPalindrome(String s) {
        int n = s.length();
        StringBuilder sb = new StringBuilder();
        sb.append('#');
        for(char c : s.toCharArray()){
            sb.append(c);
            sb.append('#');
        }
        String t = sb.toString();
        int[] hl = new int[2 * n + 1];
        int mIdx = -1, mRight = -1;
        for(int i = 0;i < 2 * n + 1;i++){
            int curHL = 0;
            if(i < mRight) curHL = Math.min(hl[2 * mIdx - i], mRight - i + 1);
            while(i - curHL >= 0 && i + curHL < 2 * n + 1 && t.charAt(i - curHL) == t.charAt(i + curHL)) curHL++;
            hl[i] = curHL;
            if(i + curHL - 1 > mRight){
                mRight = i + curHL - 1;
                mIdx = i;
            }
        }
        int maxHL1 = 0, maxHL2 = 0, idx1 = -1, idx2 = -1;     // 奇回文, 偶回文
        for(int i = 1;i < 2 * n + 1;i++){   // 最左边的#没有对应的偶回文中心偏左的字符, 因此可以从i = 1开始
            if(i % 2 == 0){
                // 偶回文
                int idx = i / 2 - 1, sHL = hl[i] / 2;
                if(sHL == idx + 1) {
                    if(sHL > maxHL2){
                        maxHL2 = sHL;
                        idx2 = idx;
                    }
                }
            }else{
                // 奇回文
                int idx = i / 2, sHL = hl[i] / 2;
                if(sHL == idx + 1){
                    if(sHL > maxHL1){
                        maxHL1 = sHL;
                        idx1 = idx;
                    }
                }
            }
        }
        if(n - (2 * maxHL1 - 1) < n - 2 * maxHL2){
            // 奇回文
            return reverse(s.substring(idx1 + maxHL1, n)) + s;
        }else{
            // 偶回文
            return reverse(s.substring(idx2 + maxHL2 + 1, n)) + s;
        }
    }

    private String reverse(String s){
        int n = s.length();
        StringBuilder sb = new StringBuilder();
        for(int i = n - 1;i >= 0;i--){
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }
}
