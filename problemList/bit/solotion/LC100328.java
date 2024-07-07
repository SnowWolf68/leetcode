package problemList.bit.solotion;

import java.util.List;

/**
如何判断一个二进制数x中是否包含相邻的0, 这里假设x的二进制长度为n, 那么定义mask = (1 << n) - 1
((~(x)) & mask) & ((~(x >> 1)) & mask)
 */
public class LC100328 {
    public List<String> validStrings(int n) {
        int mask = (1 << n) - 1;
        
    }
}
