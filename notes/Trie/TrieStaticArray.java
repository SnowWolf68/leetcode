package notes.Trie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
静态数组实现的 Trie
    tree数组的结构
    对于tree[i][]来说: 
        如果tree[i][j] == 0, 意味着tree[i]这个节点中没有j这个孩子
        反之, tree[i][j]就是tree[i]的下标为j的孩子在tree中的下标, 即tree[i]的下标为j的孩子为tree[tree][i][j]
    
牛客测试链接: https://www.nowcoder.com/practice/7f8a8553ddbf4eaab749ec988726702b
 */
public class TrieStaticArray {
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
    TrieStaticArray(int n, int m){
        tree = new int[n][m];
        cnt = 1;    // 这里我们规定: tree中的下标从1开始用
        pass = new int[n];
        end = new int[n];
    }

    /**
     * 计算字符c对应的子节点位于tree中第二个维度中的下标
     * @param c: 要计算的字符
     * @return 字符c对应tree中第二个维度中的下标
     */
    private int getIdx(char c){
        return c - 'a';
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
            if(--pass[tree[node][idx]] == 0){
                return;
            }
            node = tree[node][idx];
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        
        TrieStaticArray Trie = new TrieStaticArray(20 * 100000, 0)

        in.nextToken(); int m = (int)in.nval;
        for(int i = 0;i < m;i++){
            String[] split = br.readLine().split(" ");
            int op = Integer.valueOf(split[0]);
            String s = split[1];
            if(op == 1){

            }
        }

        out.flush();
		out.close();
		br.close();
    }
}
