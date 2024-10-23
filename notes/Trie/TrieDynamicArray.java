package notes.Trie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
线段树动态实现, 子节点使用固定长度的数组保存

牛客测试链接: https://www.nowcoder.com/practice/7f8a8553ddbf4eaab749ec988726702b
 */
public class TrieDynamicArray {

    class TrieNode{
        int pass;
        int end;
        TrieNode[] nxt;

        TrieNode(){
            pass = 0;
            end = 0;
            nxt = new TrieNode[26];
        }
    }

    private TrieNode root;

    TrieDynamicArray(){
        root = new TrieNode();
    }

    // insert word into Trie
    private void insert(String word){
        TrieNode node = root;
        node.pass++;
        for(char c : word.toCharArray()){
            if(node.nxt[c - 'a'] == null){
                node.nxt[c - 'a'] = new TrieNode();
            }
            node.nxt[c - 'a'].pass++;
            node = node.nxt[c - 'a'];
        }
        node.end++;
    }    

    // return: 字符串word在前缀树中出现的次数
    private int search(String word){
        TrieNode node = root;
        for(char c : word.toCharArray()){
            if(node.nxt[c - 'a'] == null) return 0;
            node = node.nxt[c - 'a'];
        }
        return node.end;
    }

    // return: 前缀prefix在前缀树中出现的次数
    private int prefixNumber(String prefix){
        TrieNode node = root;
        for(char c : prefix.toCharArray()){
            if(node.nxt[c - 'a'] == null) return 0;
            node = node.nxt[c - 'a'];
        }
        return node.pass;
    }

    // delete word in Trie (delete only once)
    // 需要注意的是, 如果删除到某个分支, 将当前节点的pass--之后, pass == 0, 那么这个节点以及子节点都可以直接删掉
    private void delete(String word){
        TrieNode node = root;
        root.pass--;
        for(char c : word.toCharArray()){
            if(--node.nxt[c - 'a'].pass == 0){
                node.nxt[c - 'a'] = null;
                return;
            }
            node = node.nxt[c - 'a'];
        }
        node.end--;
    }

    // return: total string numbers in Trie
    private int getTotStrCnt(){
        return root.pass;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        
        TrieDynamicArray trie = new TrieDynamicArray();

        in.nextToken(); int m = (int)in.nval;
        for(int i = 0;i < m;i++){
            String[] split = br.readLine().split(" ");
            int op = Integer.valueOf(split[0]);
            String s = split[1];
            if(op == 1){
                trie.insert(s);
            }else if(op == 2){
                trie.delete(s);
            }else if(op == 3){
                int cnt = trie.search(s);
                if(cnt > 0) out.println("YES");
                else out.println("NO");
            }else{
                out.println(trie.prefixNumber(s));
            }
        }

        out.flush();
		out.close();
		br.close();
    }
}
