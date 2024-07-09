package problemList.trie.solution;

/**
涉及到 '.' 的匹配规则, 因此需要对原来的Trie稍作修改
 */
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
                        if(i == word.length()) return true;
                        // 匹配任意一个子树均可
                        boolean searchResult = false;
                        for(int j = 0;j < 26;j++){
                            if(p.tns[j] != null){
                                if(search(p.tns[j], word.substring(i + 1))) {
                                    searchResult = true;
                                    break;
                                }
                            }
                        }
                        if(searchResult){
                            return true;
                        }else{
                            return false;
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
