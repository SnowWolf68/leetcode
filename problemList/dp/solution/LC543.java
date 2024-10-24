package problemList.dp.solution;

/**
树形DP中的简单题

首先需要明白一点: 树的直径的两个端点, 一定是位于叶子节点上
    这一点可以使用反证法证明: 如果直径的两个端点不在叶子节点上, 那么将这条路径的端点拓展到叶子节点位置, 那么此时得到的新的路径一定比原来的更长

既然直径对应的路径的两个端点一定都是叶子节点, 那么这条路径一定存在一个 "拐点" , 这条路径的长度就可以拆解成 从 "拐点" 分别到直径的两个端点(叶子节点)的路径长度之和
因此我们可以枚举所有可能的拐点, 对于每一个可能的拐点, 都要求出左子树中到叶子结点最长的路径, 然后求出右子树中到叶子结点最长的路径, 那么这两个路径长度之和, 在加上2 (因为这里的长度指的是边数, 因此需要 +2)
就是以当前节点为拐点时的最长路径的长度
 */
public class LC543 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private int ret = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return ret;
    }

    private int dfs(TreeNode root){
        if(root == null) return -1;     // 巧妙设计返回值, 避免下面左右节点是否为空的判断
        int leftRet = dfs(root.left), rightRet = dfs(root.right);
        ret = Math.max(ret, leftRet + rightRet + 2);
        return Math.max(leftRet, rightRet) + 1;
    }
}
