package notes;

public class ZFunc {
    int[] zFunc(String str){
        int n = str.length();
        int[] z = new int[n];
        z[0] = n;   // z[0]其实并没有意义, 如果一定要填的话, 可以认为z[0] = str.length()
        int zLeft = -1, zRight = -1;
        for(int i = 1;i < n;i++){
            if(i < zRight){
                z[i] = Math.min(zRight - i + 1, z[i - zLeft]);
            }
            while(i + z[i] < n && str.charAt(i + z[i]) == str.charAt(z[i])) {
                zLeft = i;
                zRight = i + z[i];
                z[i]++;
            }
        }
        return z;
    }
}
