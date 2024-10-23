package problemList.dp.solution;

/**
树形DP
    既然是DP, 那么就需要用到DP中比较常见的技巧, 比如: 选或不选 以及 枚举选哪个
    树形DP中的状态转移比较简单 (从左右子节点转移到父节点) , 但是这并不意味着树形DP很简单

    对于其中的某一个节点, 考虑当前节点选或不选, 那么此时有两种可能: 
        1. 当前节点选: 
            要想能够选当前节点, 首先需要保证当前节点所在的树, 应该是一棵二叉搜索树, 即: 
                1) 左/右子树是二叉搜索树 2) 当前节点的值 < 右子树中的最小值 && 当前节点的值 > 左子树中的最大值
            如果能够满足上面两个条件, 那么此时以当前节点为根的子树的元素和 = 左子树的元素和 + 右子树的元素和 + 当前节点的值
        2. 当前节点不选: 那么问题转化成 求 左子树中的二叉搜索树的最大元素和, 以及求 右子树中的二叉搜索树的最大元素和 , 并在这两者之间取一个max
    
    因此递归函数的返回值需要携带以下几个信息: 
        1. 以当前节点为根的子树是否是二叉搜索树
        2. 以当前节点为根的子树中的最大/最小值
        3. 以当前节点为根的子树的元素和
    这里需要注意, 你可能会感觉1和3可以合并成一个信息, 使用一个int来表示, 
        即: 1) 如果当前子树是BST, 那么这个值 = 当前节点为根的子树的元素和
            2) 如果当前子树不是BST, 那么这个值 = -1
    但是其实这种表示方式并不合适, 因为如果当前子树不是BST, 那么只能说明当前节点为根的子树不是BST, 并不意味着当前节点为根的子树中的子树都不是BST
    并且, 如果当前子树是BST, 此时也并不意味着此时当前子树中元素和最大的BST一定是以当前节点为根的子树, 因为本题的数据范围中, 节点的值可以是负数
        因此可能出现: 以当前节点为根的子树是BST, 但是在这个子树中, 元素和最大的BST并不是以当前节点为根的子树, 而是以当前节点为根的子树中的一个子树
    除此之外, 由于节点的值有可能是负数, 因此这里使用-1表示以当前节点为根的子树不是二叉搜索树也不合适 (因为这个值同时也可以表示当前子树的元素和为-1) (但是如果将当前子树的元素和 与 当前子树是不是BST 分开来, 那么使用-1表示当前子树不是BST就是可以的)
    
    因此, 我们还是需要严格按照上面分析的3点来安排递归函数的返回值

需要注意的是, 本题中, 如果最大的BST是一棵空树, 那么此时也是认为这是一棵合法的BST, 因此最后的结果需要和0取一个max
 */
public class LC1373 {
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

    public int maxSumBST(TreeNode root) {
        return Math.max(0, dfs(root)[4]);
    }

    /**
        return [ret0, ret1, ret2, ret3, ret4]
        ret0: 以当前节点为根的子树是否是BST, -1 表示不是, 0表示是
        ret1: 以当前节点为根的子树中的最大值
        ret2: 以当前节点为根的子树中的最小值
        ret3: 以当前节点为根的子树的元素和
        ret4: 以当前节点为根的子树中 元素和最大的BST
     */
    private int[] dfs(TreeNode root){
        int[] leftRet = root.left == null ? null : dfs(root.left);
        int[] rightRet = root.right == null ? null : dfs(root.right);
        boolean isLeftBST = (leftRet == null || (leftRet != null && leftRet[0] == 0));
        boolean isRightBST = (rightRet == null || (rightRet != null && rightRet[0] == 0));
        int leftMax = leftRet == null ? Integer.MIN_VALUE : leftRet[1];
        int leftMin = leftRet == null ? Integer.MAX_VALUE : leftRet[2];
        int rightMax = rightRet == null ? Integer.MIN_VALUE : rightRet[1];
        int rightMin = rightRet == null ? Integer.MAX_VALUE : rightRet[2];
        int[] ret = new int[5];
        int leftBSTSum = leftRet == null ? Integer.MIN_VALUE : leftRet[4];
        int rightBSTSum = rightRet == null ? Integer.MIN_VALUE : rightRet[4];
        ret[0] = (isLeftBST && isRightBST && (rightRet == null || (rightRet != null && root.val < rightMin)) && (leftRet == null || (leftRet != null && root.val > leftMax))) ? 0 : -1;
        ret[1] = Math.max(leftMax, Math.max(rightMax, root.val));
        ret[2] = Math.min(leftMin, Math.min(rightMin, root.val));
        ret[3] = (leftRet == null ? 0 : leftRet[3]) + (rightRet == null ? 0 : rightRet[3]) + root.val;
        ret[4] = Math.max(ret[0] == 0 ? ret[3] : Integer.MIN_VALUE, Math.max(leftBSTSum, rightBSTSum));
        return ret;
    }
}
