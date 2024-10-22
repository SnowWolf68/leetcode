package notes.Trie;

import java.util.HashMap;
import java.util.Map;

/**
线段树动态实现, 子节点使用哈希表保存, 适合字符种类较多的情况
 */
public class TrieDynamicHashMap {
    class TrieNode{
        int pass;
        int end;
        Map<Character, TrieNode> nxt;

        TrieNode(){
            pass = 0;
            end = 0;
            nxt = new HashMap<>();
        }
    }

    private TrieNode root;

    TrieDynamicHashMap(){
        root = new TrieNode();
    }

    // insert word into Trie
    private void insert(String word){
        TrieNode node = root;
        node.pass++;
        for(char c : word.toCharArray()){
            if(!node.nxt.containsKey(c)){
                node.nxt.put(c, new TrieNode());
            }
            node.nxt.get(c).pass++;
            node = node.nxt.get(c);
        }
        node.end++;
    }    

    // return: 字符串word在前缀树中出现的次数
    private int search(String word){
        TrieNode node = root;
        for(char c : word.toCharArray()){
            if(!node.nxt.containsKey(c)) return 0;
            node = node.nxt.get(c);
        }
        return node.end;
    }

    // return: 前缀prefix在前缀树中出现的次数
    private int prefixNumber(String prefix){
        TrieNode node = root;
        for(char c : prefix.toCharArray()){
            if(!node.nxt.containsKey(c)) return 0;
            node = node.nxt.get(c);
        }
        return node.pass;
    }

    // delete word in Trie (delete only once)
    // 需要注意的是, 如果删除到某个分支, 将当前节点的pass--之后, pass == 0, 那么这个节点以及子节点都可以直接删掉
    private void delete(String word){
        TrieNode node = root;
        node.pass--;
        for(char c : word.toCharArray()){
            if(node.nxt.get(c).pass == 1){
                node.nxt.remove(c);
                return;
            }else{
                TrieNode nxtNode = node.nxt.get(c);
                nxtNode.pass--;
                node.nxt.put(c, nxtNode);
            }
            node = node.nxt.get(c);
        }
        node.end--;
    }

    // return: total string numbers in Trie
    private int getTotStrCnt(){
        return root.pass;
    }
}
