package problemList.DS.solution;

/**
需要读清题的一点是, 题目中对于每一个 queries[i] 对应的 [l, r] , 都是选择一个 下标子集 , 并且将这个下标子集中的每一个下标对应的元素 - 1
而不是仅仅将 [l, r] 这个区间中的某一个下标对应的元素 - 1 

因此本题的做法就比较显然, 我们只需要遍历所有的 queries[i] , 并且对于每一个范围 [l, r] , 都将这个范围内所有的下标对应的元素都 - 1
最后只需要遍历一遍修改完的数组, 如果所有元素都 <= 0, 那么说明可以转换为零数组, 反之不行

这里涉及到 区间修改, 因此是一个典型的 差分 模版题

为了写代码方便, 这里将 - 1 转换为 + 1 , 这样最后只需要判断 差分的前缀和数组 sum[i] 和 nums[i] 的关系即可

这里借着这个差分的模版题, 简单说一下差分数组diff[]和原数组nums[]之间的对应关系:
    1. diff[] 数组的长度: 
        diff[]数组的长度, 应该是 nums.length + 1, 因为在更新区间 [l, r] 的时候, 会用到 diff[r + 1] 这个位置, 为了避免越界, 因此diff[]的大小需要开 n + 1
    2. 区间更新时, 下标的对应关系: 
        如果要更新 nums[l, r] += val, 那么应该更新diff数组: diff[l] += val, diff[r + 1] -= val;
    3. diff[] 和 nums[] 的下标对应关系: 
        需要注意的是: diff[0] = nums[0], 之后对 diff[] 求前缀和sum[]后, 有sum[i] = nums[i]       
 */
public class LC3355 {
    public boolean isZeroArray(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] diff = new int[n + 1];
        for(int[] query : queries){
            diff[query[0]]++;
            diff[query[1] + 1]--;
        }
        int sum = 0;
        for(int i = 0;i < n;i++){
            sum += diff[i];
            if(sum < nums[i]) return false;
        }
        return true;
    }
}
