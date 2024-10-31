package problemList.DFS.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
只需要一次遍历的写法

为什么一次dfs就可以? 
因为我们做的修改都是同时进行的, 也就是所有的修改都是基于最初的树来进行的
并且在第一次dfs的过程中, 假设当前遍历到的是下标为i的节点, 那么从这个节点中返回时, 以这个节点为根的子树其实就已经修改完了
因此在第一次dfs中, 当我们从某一个节点返回时, 其实我们已经知道了这个以这个节点为根的子树修改之后的子树大小

但是由于这里子树的修改会影响到子树的大小, 因此我们不能单纯依靠dfs的返回值来计算子树大小, 需要依赖size[]数组
具体来说, 假设当前遍历到下标为i的节点, 并且当前遍历到i节点的nxt这个子节点, 那么此时分为两种情况
    1. nxt这个节点的父结点就是i: 此时可以直接将dfs(nxt)的结果加到size[i]上, 即size[i] += dfs(nxt)
    2. nxt这个节点的父节点不是i: 此时需要将dfs(nxt)的结果加到size[ancestor[s[nxt] - 'a']]上, 即size[ancestor[s[nxt] - 'a']] += dfs(nxt)
需要注意的是, 这里我们都是把子节点的子树大小累加到父节点上, 而父节点本身的大小没有被包括, 因此这里我们需要初始化size[]数组全为1

除此之外, 由于这里只涉及一次dfs, 因此实际上并不需要对g进行修改, 因此可以把修改g的部分都去掉, dfs中的i也可以正序循环, 而不是倒序循环

在具体实现中, 有一点小细节, 我写在了coding中
 */
public class LC3331_2 {
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
        int[] size = new int[n];
        Arrays.fill(size, 1);
        dfs(0, g, s, ancestor, size);
        return size;
    }

    private int dfs(int x, List<Integer>[] g, String s, int[] ancestor, int[] size) {
        int preAncestor = ancestor[s.charAt(x) - 'a'];
        ancestor[s.charAt(x) - 'a'] = x;
        for(int i = g[x].size() - 1;i >= 0;i--){        // 灵神的方法, 倒序循环, 避免g[x].size()变化带来的影响
            int nxt = g[x].get(i);
            if(ancestor[s.charAt(nxt) - 'a'] != -1){
                int ret = dfs(nxt, g, s, ancestor, size);
                size[ancestor[s.charAt(nxt) - 'a']] += ret;
                // size[ancestor[s.charAt(nxt) - 'a']] += dfs(nxt, g, s, ancestor, size);
            }else{
                /**
                 * 有关这里为什么要分开写, 而不是按照下面注释的那样写成一行的分析:
                 *  因为这里在后面的dfs(nxt, g, s, ancestor, size)的过程中, 还有可能会对size[x]进行修改, 因此如果直接 size[x] += dfs(nxt, g, s, ancestor, size)的话
                 *  有可能会导致编译器优化, 使得在dfs之前获取size[x]的值, 保存在寄存器中, 然后进行dfs(nxt), 最终将 dfs(nxt) + 寄存器的值 写入size[x]中
                 *  这样会导致最终size[x]计算错误
                 *  因此这里采用分开写的方式, 即首先计算dfs(nxt), 将返回值保存在ret中, 然后size[x] += ret, 这样就能够保证进行 += 的时候, size[x]的值一定是
                 *  经过dfs(nxt)更新过后的值
                 * 
                 *  至于灵神的写法为什么不会出问题, 可能是因为灵神在下标这里采用了三目运算符来计算下标, 从而导致编译器不会进行上述的编译优化
                 */
                int ret = dfs(nxt, g, s, ancestor, size);
                size[x] += ret;
                // size[x] += dfs(nxt, g, s, ancestor, size);
            }
        }
        ancestor[s.charAt(x) - 'a'] = preAncestor;
        return size[x];
    }
}
