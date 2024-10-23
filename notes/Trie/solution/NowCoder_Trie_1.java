package notes.Trie.solution;

/**
牛客链接: https://www.nowcoder.com/practice/c552d3b4dfda49ccb883a6371d9a6932
 */
public class NowCoder_Trie_1 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * 
     * @param b int整型二维数组 
     * @param a int整型二维数组 
     * @return int整型一维数组
     */
    public int[] countConsistentKeys (int[][] b, int[][] a) {
        // write code here
        /**
        对于-5的情况, 将 '-' 单独作为一条路, 这样就不需要考虑负数的情况, 父节点的子节点最多有 10 + 1 + 1 = 12种 (10个数字 + 一个 '-' 符号 + 一个 '#' 符号)
         */
        Trie trie = new Trie(100000 * 12, 12);
        for(int[] arr : a){
            StringBuilder sb = new StringBuilder();
            for(int i = 1;i < arr.length;i++){
                int diff = arr[i] - arr[i - 1];
                sb.append(diff + '#');
            }
            trie.insert(sb.toString());
        }
        int[] ret = new int[b.length];
        for(int j = 0;j < b.length;j++){
            StringBuilder sb = new StringBuilder();
            int[] arr = b[j];
            for(int i = 1;i < arr.length;i++){
                int diff = arr[i] - arr[i - 1];
                sb.append(diff + '#');
            }
            ret[j] = trie.prefixCnt(sb.toString());
        }
        return ret;
    }
}

class Trie {
    private int[][] tree;
    private int cnt;    // 下一个要用的tree中的节点下标
    private int[] pass;
    private int[] end;

    /**
     * 如何估算一棵Trie的n的大小?
     *  一种朴素的估算方式: n = 字符串数量 * 最大字符串长度
     *      原因也很简单, 假设所有字符串在同一下标i位置的字符均不相等, 那么显然最多就需要开 字符串数量 * 最大字符串长度 这么多节点
     * @param n: 静态数组tree的大小
     * @param m: 每一个节点的孩子数量
     */
    Trie(int n, int m){
        tree = new int[n][m];
        cnt = 1;    // 这里我们规定: tree中的下标从1开始用
        pass = new int[n];
        end = new int[n];
    }

    /**
     * 计算字符c对应的子节点位于tree中第二个维度中的下标
     * 
     *  在这题的情况中, 由于可能出现负数, 因此对于-6这种的数, 在Trie中存的时候, 会存成 '-' 和 '6' 两个字符
     *  因此需要处理当c == '-'时, 应该映射到哪一个下标
     * 
     * @param c: 要计算的字符
     * @return 字符c对应tree中第二个维度中的下标
     */
    private int getIdx(char c){
        if(c == '-') return 10;
        else if(c == '#') return 11;
        else return c - '0';
    }

    public void insert(String s){
        int node = 1;
        pass[node]++;
        for(char c : s.toCharArray()){
            int idx = getIdx(c);
            if(tree[node][idx] == 0){
                tree[node][idx] = ++cnt;
            }
            node = tree[node][idx];
            pass[node]++;
        }
        end[node]++;
    }

    // return cnt of s in Trie
    public int search(String s){
        int node = 1;
        for(char c : s.toCharArray()){
            int idx = getIdx(c);
            if(tree[node][idx] == 0) return 0;
            node = tree[node][idx];
        }
        return end[node];
    }

    // return cnt of preffix in Trie
    public int prefixCnt(String preffix){
        int node = 1;
        for(char c : preffix.toCharArray()){
            int idx = getIdx(c);
            if(tree[node][idx] == 0) return 0;
            node = tree[node][idx];
        }
        return pass[node];
    }

    /**
     * delete s in Trie only once
     * 需要注意的是, 当找到一个pass[tree[node][idx]] == 1(--之前)的节点node时, 可以直接将tree[node][idx]这个节点以及后续节点全都删掉
     * 而因为后续再开新的节点时, 我们都是让cnt继续++, 也就是在tree最后继续开新的节点, 因此这里的删除操作只需要tree[node][idx] == 0即可
     * 后续的其余节点无需进行清零操作
     * @param s
     */
    public void delete(String s){
        int node = 1;
        pass[node]--;
        for(char c : s.toCharArray()){
            int idx = getIdx(c);
            if(tree[node][idx] == 0 || --pass[tree[node][idx]] == 0){
                tree[node][idx] = 0;
                return;
            }
            node = tree[node][idx];
        }
        end[node]--;
    }
}
