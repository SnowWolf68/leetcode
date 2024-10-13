package notes.FastRead;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;


public class FastReadAndWrite {

    /**
        左神的快读快写模版
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        // 读入m, n
        in.nextToken(); int m = (int) in.nval;
		in.nextToken(); int n = (int) in.nval;

        // 输出m, n
        out.println(m + " " + n);
        out.flush();
		out.close();
		br.close();

        /* 重定位到文件
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("in.txt")));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("out.txt")));
        
        

        out.flush();
		out.close();
		br.close();
        */
    }
 
    /**
     * Java快读模版 (网上找的)
     */
    class FaseRead{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
 
        StreamTokenizer st = new StreamTokenizer(bf);
    
        String next() {
            return st.sval;
        }
    
        String nextLine() throws IOException {
            return bf.readLine();
        }
    
        int nextInt() throws IOException {
            st.nextToken();
            return (int) st.nval;
        }
    
        long nextLong() throws IOException {
            st.nextToken();
            return (long) st.nval;
        }
    
        double nextDouble() throws IOException {
            st.nextToken();
            return st.nval;
        }
    }
}
