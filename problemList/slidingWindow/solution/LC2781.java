package problemList.slidingWindow.solution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
首先我们要能够分析出来一点东西
    既然是求最长合法子串的长度, 那么显然需要枚举子串的两个端点``
    这里不妨先枚举子串的右端点, 对于子串的左端点而言: 
        什么时候左端点left++?
            只有当前子串不符合要求, 此时left才会++
        重点: 
            如果当前子串不符合要求, 并且left++, 那么当枚举到下一个右端点时, 即right++时, 此时left会回退吗?
                不会, 因为之前进行了left++, 意味着前面有一个子串, 在forbidden里面, 那么此时left只要回退, 就一定会包括forbidden字符串
                因此left不会回退
        --> left不回退, 意味着 可以使用滑窗

那么关键问题转化成了: 如何判断一个字符串中是否包含forbidden中的子串?
    由于forbidden中的字符串长度不会超过10, 因此我们可以暴力遍历来计算
    除此之外, 还需要明确的一点是: 对于right++之后, 如果当前[left, right]区间的子串包含forbidden中的字符串, 那么所包含的子串的右端点一定是right
    因此我们只需要从right开始, 倒着向前遍历10个位置, 判断这10个子串是否包含在forbidden中即可
 */
public class LC2781 {
    public int longestValidSubstring(String word, List<String> forbidden) {
        Set<String> set = new HashSet<>();
        for(String s : forbidden) set.add(s);
        int n = word.length(), left = 0, ret = 0;
        for(int i = 0;i < n;i++){
            int len = -1;
            for(int j = i;j >= Math.max(left, i - 9);j--){
                if(set.contains(word.substring(j, i + 1))){
                    len = i - j + 1;
                    break;
                }
            }
            if(len != -1){
                left = i - len + 2;
            }
            ret = Math.max(ret, i - left + 1);
        }
        return ret;
    }
}
