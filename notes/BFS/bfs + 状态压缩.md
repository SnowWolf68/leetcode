状态压缩 + BFS

> 相关题目: 
>
> 1. [LC847](https://leetcode.cn/problems/shortest-path-visiting-all-nodes/description/)
> 2. [LC864](https://leetcode.cn/problems/shortest-path-to-get-all-keys/description/)
> 3. [LCP 13](https://leetcode.cn/problems/xun-bao/description/)
> 4. [LC3568](https://leetcode.cn/problems/minimum-moves-to-clean-the-classroom/description/)

为什么附加状态后的BFS仍然是正确的?

拿[LC847](https://leetcode.cn/problems/shortest-path-visiting-all-nodes/description/)来举例子: 

> [LC847](https://leetcode.cn/problems/shortest-path-visiting-all-nodes/description/)
>
> 存在一个由 `n` 个节点组成的无向连通图，图中的节点按从 `0` 到 `n - 1` 编号。
>
> 给你一个数组 `graph` 表示这个图。其中，`graph[i]` 是一个列表，由所有与节点 `i` 直接相连的节点组成。
>
> 返回能够访问所有节点的最短路径的长度。你可以在任一节点开始和停止，也可以多次重访节点，并且可以重用边。

我们可以用**分层图最短路**的思想来试着理解一下添加了额外状态后的BFS过程: 

在本题中, 我们在基本BFS的状态`i`(当前节点编号)的基础上, 添加了`state`这样的状态, 即将`(i, state)`作为一个状态放到队列中进行BFS

不妨用分层图的思想, 将所有`(i, state)`的状态空间看作是有 **`mask`层, 每层`n`个节点** 这样的一个分层图, 那么我们要求的就是从第一层到第`mask`层的最短路径.

特别的, 从第`j`层走到第`j + 1`层, 当且仅当最后一次走的路径 `cur -> nxt` 中的`nxt`未被访问过. 此时我们会从`(cur, j)`这个状态, 走到`(nxt, j + 1)`这个状态, 并且此时的代价为`1`. 因此不难看出, **相邻两层之间的权值** 和 **每层中相邻两个节点之间的权值** 是**相等**的

那么如果将这一整个立体的图看作是一张无向等权图的话, 那么要求的最短路就可以使用BFS来解决

这也是为什么虽然我们添加了额外的状态, 但是我们仍然可以使用BFS来计算最短路
