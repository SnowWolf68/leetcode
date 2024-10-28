package notes.Martrix.solution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
矩阵快速幂测试链接: https://www.luogu.com.cn/problem/P1226
 */
public class QuickPower {
    public static int pow(int base, int exp, int mod){
        long ans = 1, x = base;
        while(exp != 0){
            if((exp & 1) == 1){
                ans = (ans * x) % mod;
            }
            exp >>= 1;
            x = (x * x) % mod;
        }
        return (int)ans;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        
        in.nextToken(); int a = (int)in.nval;
        in.nextToken(); int b = (int)in.nval;
        in.nextToken(); int p = (int)in.nval;
        System.out.printf("%d^%d mod %d=", a, b, p);
        System.out.println(pow(a, b, p));

        out.flush();
		out.close();
		br.close();
    }
}
