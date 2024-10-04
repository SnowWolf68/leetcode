package problemList.slidingWIndows.solution;

public class LC904 {
    public int totalFruit(int[] fruits) {
        int n = fruits.length, left = 0, ret = 0, cnt = 0;
        int[] category = new int[n];
        for(int i = 0;i < n;i++){
            if(category[fruits[i]] == 0){
                cnt++;
            }
            category[fruits[i]]++;
            while(cnt > 2){
                if(--category[fruits[left]] == 0) cnt--;
                left++;
            }
            ret = Math.max(ret, i - left + 1);
        }
        return ret;
    }
}
