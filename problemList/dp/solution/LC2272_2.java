package problemList.dp.solution;

public class LC2272_2 {
    public int largestVariance(String _s) {
        char[] s = _s.toCharArray();
        int n = s.length, ret = 0, INF = 0x3f3f3f3f;
        for(int i = 0;i < 26;i++){
            for(int j = 0;j < 26;j++){
                // 最少, 最多
                char a = (char)(i + 'a'), b = (char)(j + 'a');
                // 不管包不包括, 包括
                int f = -INF, g = -INF, max = -INF;
                for(int k = 0;k < n;k++){
                    if(s[k] == a){
                        f = Math.max(-1, f - 1);
                        g = f;
                    }else if(s[k] == b){
                        f = Math.max(1, f + 1);
                        g = g + 1;
                    }
                    max = Math.max(max, g);
                }
                ret = Math.max(ret, max);
            }
        }
        return ret;
    }
}
