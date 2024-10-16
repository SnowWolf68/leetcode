package problemList.slidingWindow.solution;

/**
这题的关键在于, 如何判断删除一个子数组之后, 剩下的元素是否是非递减的

我的想法是, 通过维护所有的逆序对数量, 以及单独判断子数组两侧的元素, 来判断剩下的元素是否是非递减的
具体来说, 首先计算出原数组中的所有逆序对数量, 假设逆序对总数为 revCnt
当枚举到其中某一个子数组[left, right]时, 首先我们可以在枚举子数组的过程中, 维护子数组中的逆序对数量 curCnt
那么数组中剩下的这些元素中的逆序对数量就可以计算出来 计算过程需要考虑的情况比较多, 具体的计算方式可以看代码
如果这个逆序对数量为0, 说明剩下的这些元素是非递减的, 否则就不是
 */
public class LC1574 {
    public int findLengthOfShortestSubarray(int[] arr) {
        int n = arr.length, left = 0, ret = Integer.MAX_VALUE, revCnt = 0, curCnt = 0;
        for(int i = 0;i < n;i++) {
            if(i != 0 && arr[i] < arr[i - 1]) revCnt++;
        }
        if(revCnt == 0) return 0;
        for(int i = 0;i < n;i++){
            if(i != 0 && i - left + 1 >= 2 && arr[i] < arr[i - 1]) curCnt++;
            while(left <= i && revCnt - curCnt - (left - 1 >= 0 && arr[left] < arr[left - 1] ? 1 : 0) - (i + 1 < n && arr[i + 1] < arr[i] ? 1 : 0) + (i + 1 < n && left - 1 >= 0 ? (arr[i + 1] >= arr[left - 1] ? 0 : 1) : 0) == 0){
                ret = Math.min(ret, i - left + 1);
                if(left + 1 < n && arr[left + 1] < arr[left]) curCnt--;
                left++;
            }
        }
        return ret;
    }
}
