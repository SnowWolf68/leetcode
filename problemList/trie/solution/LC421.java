package problemList.trie.solution;

/**
为什么这题能够用字典树来实现?

其实这里还是利用了 从高位到低位的构造思想, 这个思想我在 bit文件夹 中对应题目的解析中提到过

只不过这里对于这个思想的实现方式和位运算有些不同
具体来说: 
    首先将nums中的所有元素都插入到Trie中, 这里为了将前缀对齐, 对于每个nums[i], 这里都取32位
    然后枚举nums中的每个元素nums[i], 拿着遍历到的nums[i]到Trie树中匹配, 
    因为Trie树我们是按照从高位到低位构建的, 因此首先走到的一定是nums中某个元素的高位, 
    然后就用到了 从高位到低位 的构造思想
    对于Trie树中当前节点的两个子节点来说, 每次我们都选取和nums[i]中对应位上 不同 的那个 (前提是如果可以选择的话, 如果只有一个子节点, 那么无法选择)

    由于Trie树保证了每次都是从高位开始匹配, 而 每次选择的都是和nums[i]对应位置 最不同的 那个, 因此使用Trie能够满足贪心的构造需求

特别的, 由于这里的Trie树只有2个节点, 分别是0, 1, 因此这里需要对原来的True树模版进行稍微的改动
 */
public class LC421 {
    public int findMaximumXOR(int[] nums) {
        Trie trie = new Trie();
        for(int x : nums) trie.insert(x);
        int ans = 0;
        for(int num : nums){
            int curAns = 0;
            Trie.TrieNode p = trie.root;
            for(int i = 31;i >= 0;i--){
                int cur = (num >> i) & 1;
                if(p.tns[cur ^ 1] != null){
                    curAns |= 1 << i;
                    p = p.tns[cur ^ 1];
                }else{
                    p = p.tns[cur];
                }
            }
            ans = Math.max(ans, curAns);
        }
        return ans;
    }

    class Trie{
        class TrieNode{
            boolean end;
            TrieNode[] tns = new TrieNode[2];      // TireNodes
        }
    
        private TrieNode root;
    
        public Trie() {
            root = new TrieNode();
        }
        
        public void insert(int num) {
            TrieNode p = root;
            for(int i = 31;i >= 0;i--){
                int u = (num >> i) & 1;
                if(p.tns[u] == null) p.tns[u] = new TrieNode();
                p = p.tns[u];
            }
            p.end = true;
        }
    }
}
