package problemList.slidingWindow.solution;

/**
滑动窗口也叫同向双指针, 其主要的特点是: 当right++时, left不可能减少 (即有单调性)
    如果对于要求的区间, 其具备上面的特性, 那么可以考虑使用滑窗来解决
本题中, 由于总的步数是一定的, 因此对于能走到的区间[left, right], 其当right++时, left显然不可能减少, 符合滑窗的特点
并且这题也像LC2271一样, 是对下标数组进行的滑窗
1. left何时需要++? 
    如果2 * (startPos - fruit[left]) + fruit[right] - startPos > k || 2 * (right - startPos) + startPos - left > k
    那么需要left++
2. left和right的初始值在哪里? 
    left的初始值, 即当前只考虑向左走的情况, 那么此时能够到达的最远距离
        求这个
    right的初始值即startPos
 */
public class LC2106 {
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        
    }
}
