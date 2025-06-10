package problemList.unsorted;

/**
二进制枚举/枚举子集
 */
public class LC3566_2 {
    public boolean checkEqualPartitions(int[] nums, long target) {
        int n = nums.length, state = 1 << n - 1;
        boolean find = false;
        while (state != 0) {
            long leftRet = 1, rightRet = 1, leftCnt = 0;
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 1){
                    leftRet *= nums[i];
                    leftCnt++;
                }else{
                    rightRet *= nums[i];
                }
            }
            if(leftCnt != 0 && leftCnt != n && leftRet == rightRet && leftRet == target){
                find = true;
                break;
            }
            state--;
        }
        return find;
    }
}
