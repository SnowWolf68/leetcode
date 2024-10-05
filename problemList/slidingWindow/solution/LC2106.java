package problemList.slidingWindow.solution;

/**
滑动窗口也叫同向双指针, 其主要的特点是: 当right++时, left不可能减少 (即有单调性)
    如果对于要求的区间, 其具备上面的特性, 那么可以考虑使用滑窗来解决
本题中, 由于总的步数是一定的, 因此对于能走到的区间[left, right], 其当right++时, left显然不可能减少, 符合滑窗的特点
并且这题也像LC2271一样, 是对下标数组进行的滑窗
1. left何时需要++? 
    如果2 * (startPos - fruit[left][0]) + fruit[right][0] - startPos > k || 2 * (fruit[right][0] - startPos) + startPos - fruit[left][0] > k
    那么需要left++
2. left和right的初始值在哪里? 
    left的初始值, 即当前只考虑向左走的情况, 那么此时能够到达的fruit[left][0]
    求这个距离可以使用二分来找
    right的初始值即startPos
 */
public class LC2106 {
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int n = fruits.length, l = 0, r = n - 1;
        while(r >= 0 && fruits[r][0] > startPos) r--; // 初始的r应该是startPos左边最近的那个fruit[r][0]
        // 二分查找右区间的左边界
        while(l < r){
            int mid = (l + r) >> 1;
            if(startPos - fruits[mid][0] <= k) r = mid;
            else l = mid + 1;
        }
        int left = l, curRet = 0, ret = 0;
        for(int i = left;i < n;i++){
            curRet += fruits[i][1];
            while(left <= i && ((fruits[i][0] <= startPos && startPos - fruits[left][0] > k) 
                                || (fruits[i][0] > startPos && fruits[left][0] <= startPos && ((2 * (startPos - fruits[left][0]) + fruits[i][0] - startPos > k) && (2 * (fruits[i][0] - startPos) + startPos - fruits[left][0] > k)))
                                || (fruits[left][0] > startPos && fruits[i][0] - startPos > k))){
                curRet -= fruits[left][1];
                left++;
            }
            ret = Math.max(ret, curRet);
        }
        return ret;
    }
}
