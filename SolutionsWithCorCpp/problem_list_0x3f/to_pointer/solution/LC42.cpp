#include <algorithm>
#include <iostream>
#include <stack>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

/**
前后缀分解:
	考虑每一个i的正上方能接多少水: min(i左边的最大值, i右边的最大值)
		即: min([0, i]区间的最大值pre[i], [i, n - 1]区间的最大值suf[i])
	其中pre[]和suf[]均可以通过一次遍历 (dp) 计算出来
 */

int trap(vector<int> &height) {
	int n = height.size();
	// int pre[n], suf[n];      // C++也不允许
	vector<int> pre(n, 0), suf(n, 0);
	pre[0] = height[0];
	suf[n - 1] = height[n - 1];
	for (int i = 1; i < n; i++) {
		pre[i] = max(pre[i - 1], height[i]);
	}
	for (int i = n - 2; i >= 0; i--) {
		suf[i] = max(suf[i + 1], height[i]);
	}
	int ret = 0;
	for (int i = 0; i < n; i++) {
		ret += min(pre[i], suf[i]) - height[i];
	}
	return ret;
}

/**
考虑这样一种场景: 假设我们知道了[0, l]区间的最大值lmax, 以及[r, n -
1]区间的最大值rmax 如果此时有lmax < rmax, 那么对于 l + 1 位置来说,
其能接的水量就会受限于lmax, 即其能接的水量为 lmax - height[l + 1]
		那么接下来继续移动 l 指针, 这就有双指针的样子了
	如果lmax > rmax, 同理

在代码实现中, 由于0和n - 1位置一定接不到水, 因此让l, r初始化为0, n - 1
	每次循环中, l和r位置都认为是处理过的位置(更新过ret), 只需要对l,
r位置计算lmax, rmax 然后根据情况用 l + 1 或 r - 1 位置累加ret即可
 */
int trap2(vector<int> &height) {
	int n = height.size(), l = 0, r = n - 1, lmax = 0, rmax = 0, ret = 0;
	while (l < r) {
		lmax = max(lmax, height[l]);
		rmax = max(rmax, height[r]);
		if (lmax < rmax) {
			ret += max(0, lmax - height[l + 1]);
			l++;
		} else {
			ret += max(0, rmax - height[r - 1]);
			r--;
		}
	}
	return ret;
}

/**
单调栈: 寻找最近的比自己大的元素

现在想象你从左到右遍历柱子：

	如果柱子的高度一直在下降（或者平齐），水是留不住的，只会顺着流走。这时候我们就把这些柱子记录下来，它们可能成为未来水坑的左边界和坑底。
	突然！ 遇到了一根比前面高的柱子。
	这说明什么？说明地形开始上升了！一个“凹”字型的右边界出现了！
	此时，前面记录的那些矮柱子，加上这根高柱子，刚好形成了一个可以装水的“坑”。

“寻找最近的比自己大的元素” —— 这正是单调栈的拿手好戏！
因此，我们需要维护一个单调递减栈（从栈底到栈顶，柱子高度越来越矮）。

因此, 整体流程为:
	1. 从左到右遍历nums[i], 使用单调栈维护nums[i]左侧第一个比nums[i]大的元素下标
	2. 如果当前的nums[i]大于栈顶, 根据单调栈的规则, 需要弹出栈顶
		对于本题来说, 此时对于弹出的栈顶元素而言, 相当于找到了栈顶元素右边的第一个比栈顶大的元素 
        并且如果弹出栈顶之后栈中还有元素, 那么此时的栈顶元素就是弹出的元素左边第一个比它大的元素 
        根据上面这几个元素, 就可以计算弹出的元素此时能容纳雨水的面积 
        (横着看: 底: 弹出的元素, 高: min(弹出元素左边第一个比它大的元素, 右边第一个比它大的元素))
	3. 对于相等元素的处理: 栈中只保留一次相同的元素, 遇到相同的元素跳过即可, 具体处理见代码
	4. 把上面所有计算过的容积加起来就是答案
	5. 可以理解为, 每次计算一段容积, 就用水泥把这一段 填平 , 然后继续往后计算

每次计算: i是右边界, st.top()是底, 弹出之后的st.top()是左边界
 */
int trap3(vector<int> &nums) {
	int n = nums.size(), ret = 0;
	stack<int> st; // 下标
	for (int i = 0; i < n; i++) {
		// i是右边界, st.top()是底, 弹出之后的st.top()是左边界
		while (!st.empty() && nums[i] >= nums[st.top()]) {
			if (nums[i] == nums[st.top()]) {
				st.pop();   // 相同元素跳过即可, 保留一个就行, 
                            // 由于下面还要st.push(i), 所以这里先pop一下
				break;
			}
			int curIdx = st.top();
			st.pop();
			if (!st.empty()) {
				ret += (i - st.top() - 1) *
					   (min(nums[i], nums[st.top()]) - nums[curIdx]);
				// cout << "i = " << i << " st.top() = " << st.top()
				// 	 << " curIdx = " << curIdx << " volume = "
				// 	 << (i - st.top() - 1) *
				// 			(min(nums[i], nums[st.top()]) - nums[curIdx])
				// 	 << endl;
			}
		}
		st.push(i);
	}
	return ret;
}

int main() {
	vector<int> nums = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
	cout << trap3(nums) << endl;

	return 0;
}