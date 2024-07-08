package problemList.trie.solution;

public class LC211 {

    class WordDictionary{

        private Trie trie;

        public WordDictionary() {
            trie = new Trie();
        }
        
        public void addWord(String word) {
            trie.insert(word);
        }
        
        public boolean search(String word) {
            return trie.search(trie.root, word);
        }

        class Trie {

            class TrieNode{
                boolean end;
                TrieNode[] tns = new TrieNode[26];      // TireNodes
            }
        
            private TrieNode root;
        
            public Trie() {
                root = new TrieNode();
            }
            
            public void insert(String word) {
                TrieNode p = root;
                for(int i = 0;i < word.length();i++){
                    int u = word.charAt(i) - 'a';
                    if(p.tns[u] == null) p.tns[u] = new TrieNode();
                    p = p.tns[u];
                }
                p.end = true;
            }
            
            public boolean search(TrieNode cur, String word) {
                TrieNode p = cur;
                for(int i = 0;i < word.length();i++){
                    if(word.charAt(i) == '.'){
                        // 匹配任意一个子树均可
                        boolean searchResult = false;
                        for(int j = 0;j < 26;j++){
                            if(p.tns[j] != null){
                                
                            }
                        }
                    }
                    int u = word.charAt(i) - 'a';
                    if(p.tns[u] == null) return false;
                    p = p.tns[u];
                }
                return p.end;
            }
        }
    }
}
