package problemList.dp.solution;

/**
和LC543非常类似, 只不过LC543中路径的两个端点一定是叶子, 但是这题节点值可以是负数, 因此路径的端点不一定是叶子节点
但是其实思路还是不变的, 依旧是枚举拐点, 然后分别求 从左/右子节点开始 的最大路径和

需要注意的是, 这里由于路径和有可能是负数, 因此左/右子节点并不是必须要选, 也可以不选左/右节点开始的路径
 */
public class LC124 {
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

    private int ret = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        dfs(root);
        return ret;    
    }

    private int dfs(TreeNode root){
        if(root == null) return 0;
        int leftRet = dfs(root.left), rightRet = dfs(root.right);
        ret = Math.max(ret, leftRet + rightRet + root.val);
        return Math.max(Math.max(leftRet, rightRet) + root.val, 0);
    }
}
