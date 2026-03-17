一、单调栈
§1.1 基础
原理讲解：单调栈【基础算法精讲 26】

模板：
```cpp
pair<vector<int>, vector<int>> nearestGreater(vector<int>& nums) {
    int n = nums.size();
    // left[i] 是 nums[i] 左侧最近的严格大于 nums[i] 的数的下标，若不存在则为 -1
    vector<int> left(n);
    vector<int> st{-1}; // 哨兵
    for (int i = 0; i < n; i++) {
        int x = nums[i];
        while (st.size() > 1 && nums[st.back()] <= x) { // 如果求严格小于，改成 >=
            st.pop_back();
        }
        left[i] = st.back();
        st.push_back(i);
    }

    // right[i] 是 nums[i] 右侧最近的严格大于 nums[i] 的数的下标，若不存在则为 n
    vector<int> right(n);
    st = {n}; // 哨兵
    for (int i = n - 1; i >= 0; i--) {
        int x = nums[i];
        while (st.size() > 1 && nums[st.back()] <= x) { // 如果求严格小于，改成 >=
            st.pop_back();
        }
        right[i] = st.back();
        st.push_back(i);
    }

    return {left, right};
}
```
    单调栈: 寻找最近的比自己大的元素
739. 每日温度   (基础题, 从左到右 & 从右到左)   




TODO: 
    1475. 商品折扣后的最终价格 非暴力做法
    496. 下一个更大元素 I
    503. 下一个更大元素 II
    901. 股票价格跨度 1709
    853. 车队

    §1.2 进阶
    1019. 链表中的下一个更大节点 1571
    654. 最大二叉树 做到 O(n)
    768. 最多能完成排序的块 II 1788
    3814. 预算下的最大总容量 1796
    3676. 碗子数组的数目 1848
    2054. 两个最好的不重叠活动 1883
    456. 132 模式 约 2000
    3113. 边界元素是最大值的子数组数目 2046
    2866. 美丽塔 II 2072
    975. 奇偶跳 2079
    1944. 队列中可以看到的人数 2105
    2454. 下一个更大元素 IV 2175
    1130. 叶值的最小代价生成树 O(n) 做法
    2289. 使数组按非递减顺序排列 2482
    1776. 车队 II 2531
    2736. 最大和查询 2533
    3420. 统计 K 次操作以内得到非递减子数组的数目 2855 树形结构
