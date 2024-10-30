package problemList.DFS.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
这题其实还是很不好想的
首先我们需要知道题目中是如何进行修改的, 题目中说 "对于节点编号从 1 到 n - 1 的每个节点 x ，我们 同时 执行以下操作 一次"
如何理解这里 "同时" 的含义? 这一点对下面题解的分析很重要
我们可以看示例2这个例子: input: parent = [-1,0,4,0,1], s = "abbba"  output: [5,2,1,1,1]
通过这里例子可以发现, 对于每一个节点的修改, 都是同时进行的, 并不存在先后关系
具体来说, 对于示例2中的下标为4和下标为2的这两个节点来说, 正确的做法应该是 同时 将4的父节点改为0, 并且将2的父节点改为1, 而不需要考虑这两个节点修改的先后关系
换句话说, 原树中的节点的修改是 同时 进行的, 不需要考虑节点修改的先后关系带来的一系列问题, 或者你也可以理解为: 这里的修改操作, 其基础都是在最初始的树上进行的修改, 而不是在某一个修改过后的情况下进行的修改

上面的分析看起来不起眼, 但是十分重要, 如果不搞清楚这一点, 那么去想解法就会很晕

接下来考虑解法: 
    最关键的问题: 如何找到和某一个节点字符相同的最近的祖先节点?

    通过最开始的分析, 我们知道, 这里的修改是同时进行的, 换句话说, 对于每一个节点的修改, 找和当前节点字符相同的祖先节点, 都是在最初的树的基础上进行的
    因此我们要想找和某一个节点字符相同的最近的祖先节点, 也是在最初的树的基础上找的

    由于要找的是 最近的和当前节点相同的祖先节点 , 我们可以对原始的树进行一次dfs, 在dfs的过程中, 维护ancestor[]数组, 其中ancestor[ch]表示在遍历的过程中最后一次出现的字符为ch的节点的下标
    特别的, 如果在遍历过程中没有出现字符为ch的节点, 那么ancestor[ch] = -1
    如果我们在遍历的过程中维护了ancestor[]这个数组, 那么我们就可以在遍历到某一个节点i的时候, 快速知道和这个节点字符相同的最近的祖先节点下标
    具体来首, 如果我们遍历到了下标i这个节点, 那么和这个节点字符相同的最近的祖先节点就是ancestor[s[i] - 'a']

    需要注意的是, 为了能够保证遍历到i节点是, ancestor[]数组的所有元素都是正确的, 我们不仅要在进入某一个节点i之后, 更新ancestor[s[i] - 'a'] = i, 然后继续dfs当前这个i节点的子节点
    还需要在离开当前节点i时, 恢复原来ancestor[s[i] - 'a']的值   (这个操作你可以理解为 恢复现场)

    这里我们还需要考虑另外一个问题: 如何进行修改? 何时修改?

    这里的修改很有讲究, 因为我们只有在第一次dfs遍历到i这个节点时, 才能够知道这个节点修改之后应该挂到哪一个节点下面, 因此修改的时机, 一定是在第一次进行dfs的过程中
    然而, 在一开始的分析中就提到了, 本题的所有修改, 都是在 同一时间 完成的, 即所有的修改操作, 都是基于最初的这棵树的
    那么我们应该如何保证, 在进行dfs遍历的过程中修改这棵树, 同时保证遍历的顺序还是按照最初的树的dfs顺序呢?
    这里我们使用邻接表存这棵树, 并且为了能够修改原本的邻接表, 我们选择每次都是处理当前节点的子节点, 而不是处理当前节点
    假设当前遍历到了x这个节点的y这个子节点, 那么此时如果ancestor[s[y] - 'a'] != -1, 那么意味着和y这个子节点字符相同并且最近的祖先节点存在, 那么根据题目要求, 此时我们应该将y这个节点挂在ancestor[s[y] - 'a']这个节点上
    因此我们应该让 g[ancestor[s[y] - 'a']].add(y), 并且让g[x].set(y, -1)
    那么做了这个修改之后, 我们如何保证后续的遍历顺序依然是按照最初的那棵树的dfs顺序来的呢
    显然对于y这棵子树的遍历顺序, 此时没有影响, 因为当前dfs到了y这个节点, 那么接下来就是要继续dfs递归y这个节点的所有子节点
    问题在于此时我们让g[ancestor[s[y] - 'a']].add(y), 而我们不想让ancestor[s[y] - 'a']这个节点在后续的dfs过程中递归到y这个节点, 这应该怎么处理?
    其实也很简单, 由于在邻接表中, ancestor[s[y] - 'a']这个节点的所有子节点, 原本就都存在邻接表g中, 并且后续如果有其他节点挂在这个节点的下面 (成为这个节点的子节点) , 我们都是调用的g[ancestor[s[y] - 'a']].add()
    也就是说, 后续再添加节点时, 都是在原本这个list的最后插入节点, 那么我们只需要保证, 在dfs到节点x, 并且遍历g[x]这个list时, 不会遍历到后面再添加到g[x]这个list中的这些节点即可
    操作也很简单, 我们可以在第一次遍历g[x]的时候先令size = g[x].size(), 后续的循环条件就是i < size, 这样就能够保证, 在后续的dfs过程中, 即使有节点添加到g[x]中, 那么在遍历g[x]的时候, 也不会遍历到后续添加的这些节点, 始终还是遍历原本没有发生修改之前的二叉树

    但是这里灵神提供了另外一种更巧妙的写法, 即我们不从0循环到size, 而是从g[x].size() - 1循环到0, 这样每次的循环条件只需要判断i >= 0即可, 就不会受到g[x].size()变化的影响

    第一遍dfs结束之后, 我们就得到了修改之后的二叉树, 那么此时我们只需要再dfs一次这个修改之后的二叉树, 计算每一棵子树的大小即可, 这个dfs就很简单了
 */
public class LC3331_1 {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] g = new List[n];
        int[] ancestor = new int[26];
        Arrays.fill(ancestor, -1);
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int i = 0;i < n;i++){
            if(parent[i] != -1){
                g[parent[i]].add(i);
            }
        }
        dfs1(0, g, s, ancestor);
        int[] size = new int[n];
        dfs2(0, g, size);
        return size;
    }

    private int dfs2(int x, List<Integer>[] g, int[] size) {
        int ret = 1;
        for(int nxt : g[x]){
            if(nxt != -1){
                ret += dfs2(nxt, g, size);
            }
        }
        size[x] = ret;
        return ret;
    }

    private void dfs1(int x, List<Integer>[] g, String s, int[] ancestor) {
        int preAncestor = ancestor[s.charAt(x) - 'a'];
        ancestor[s.charAt(x) - 'a'] = x;
        for(int i = g[x].size() - 1;i >= 0;i--){        // 灵神的方法, 倒序循环, 避免g[x].size()变化带来的影响
            int nxt = g[x].get(i);
            if(ancestor[s.charAt(nxt) - 'a'] != -1){
                g[x].set(i, -1);
                g[ancestor[s.charAt(nxt) - 'a']].add(nxt);
            }
            dfs1(nxt, g, s, ancestor);
        }
        ancestor[s.charAt(x) - 'a'] = preAncestor;
    }
}
