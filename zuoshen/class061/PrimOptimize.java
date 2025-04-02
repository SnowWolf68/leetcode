package zuoshen.class061;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * 优化: 小根堆中至多存放 n 个元素, 堆需要反向索引来寻找某个未加入节点对应的权值最小的边
 */
public class PrimOptimize {
    class MinHeap {
        public List<int[]> list; // [oldNode, newNode, weight]
        public int[] revIndex; // revIndex[newNode] = index in list
        public int usedSize;
        public int n;

        private MinHeap(int _n) {
            n = _n;
            list = new ArrayList<>();
            for(int i = 0;i < n;i++) list.add(new int[]{-1, -1, 0x3f3f3f3f});
            // System.out.println("init size = " + list.size());
            revIndex = new int[n];
            Arrays.fill(revIndex, -1);
            usedSize = 0;
        }

        private void shiftUp(int child) {
            int pa = (child - 1) / 2;
            while (child > 0) {
                if (list.get(child)[2] < list.get(pa)[2]) {
                    swap(child, pa);
                    child = pa;
                    pa = (child - 1) / 2;
                } else break;
            }
        }

        private void shiftDown(int start, int end) {
            int lchild = start * 2 + 1, rchild = start * 2 + 2, pa = start;
            while (lchild <= end || rchild <= end) {
                if (lchild <= end && rchild <= end) {
                    if (list.get(lchild)[2] <= list.get(pa)[2] && list.get(lchild)[2] <= list.get(rchild)[2]) {
                        swap(lchild, pa);
                        pa = lchild;
                        lchild = 2 * pa + 1;
                        rchild = 2 * pa + 2;
                    } else if (list.get(rchild)[2] <= list.get(pa)[2] && list.get(rchild)[2] <= list.get(lchild)[2]) {
                        swap(rchild, pa);
                        pa = rchild;
                        lchild = 2 * pa + 1;
                        rchild = 2 * pa + 2;
                    }else break;
                } else {
                    // only lchild <= end
                    if (list.get(lchild)[2] < list.get(pa)[2]) {
                        swap(lchild, pa);
                        pa = lchild;
                        lchild = 2 * pa + 1;
                        rchild = 2 * pa + 2;
                    }else break;
                }
            }
        }

        private void swap(int idx1, int idx2) {
            revIndex[list.get(idx1)[1]] = idx2;
            revIndex[list.get(idx2)[1]] = idx1;
            int[] temp = list.get(idx2).clone();
            list.set(idx2, list.get(idx1));
            list.set(idx1, temp);
        }

        public void build(List<int[]> li) {
            list = new ArrayList<>(li);
            for (int i = 0; i < list.size(); i++) {
                revIndex[list.get(i)[1]] = i;
            }
            usedSize = li.size();
            for (int i = (usedSize - 1) / 2; i >= 0; i--) {
                shiftDown(i, usedSize - 1);
            }
        }

        public void updateOrInsert(int[] edge) {
            if (revIndex[edge[1]] == -1) {
                list.set(usedSize++, edge);
                // System.out.println("insert: " + Arrays.toString(edge) + " usedSize = " + usedSize);
                revIndex[edge[1]] = usedSize - 1;
                shiftUp(usedSize - 1);
            } else {
                // System.out.println("aaa" + Arrays.toString(edge));
                int idx = revIndex[edge[1]];
                if (edge[2] < list.get(idx)[2]) {
                    list.set(idx, edge);
                    int pa = (idx - 1) / 2, lchild = 2 * idx + 1, rchild = 2 * idx + 2;
                    if(idx > 0 && list.get(idx)[2] < list.get(pa)[2]) shiftUp(idx);
                    else if(lchild < usedSize || rchild < usedSize){
                        if(rchild < usedSize){
                            if(list.get(idx)[2] > list.get(lchild)[2] || list.get(idx)[2] > list.get(rchild)[2]) shiftDown(idx, usedSize - 1);
                        }else{
                            // only has lchild
                            if(list.get(idx)[2] > list.get(lchild)[2]) shiftDown(idx, usedSize - 1);
                        }
                    }
                }
            }
        }

        public int[] poll(){
            int[] poll = list.get(0);
            swap(0, --usedSize);
            revIndex[poll[1]] = -1;
            shiftDown(0, usedSize - 1);
            return poll;
        }

        public boolean isEmpty(){
            return usedSize == 0;
        }
        

    }

    public List<int[]> primOptimize(int n, List<int[]> edges) {
        int[][] g = new int[n + 1][n + 1];
        int INF = 0x3f3f3f3f;
        for (int i = 0; i <= n; i++)
            Arrays.fill(g[i], INF);
        for (int[] e : edges) {
            if (g[e[0]][e[1]] > e[2]) {
                g[e[0]][e[1]] = e[2];
                g[e[1]][e[0]] = e[2];
            }
        }
        MinHeap minHeap = new MinHeap(n);
        boolean[] vis = new boolean[n];
        vis[0] = true;
        for(int i = 0;i < n;i++){
            if(g[0][i] != INF){
                // System.out.println("aaa");
                minHeap.updateOrInsert(new int[]{0, i, g[0][i]});
            }
        }
        int cnt = 1;
        List<int[]> ret = new ArrayList<>();
        // System.out.println(minHeap.isEmpty());

        // for(int i = 0;i < minHeap.usedSize;i++){
        //     System.out.println("minHeap: " + Arrays.toString(minHeap.list.get(i)));
        // }

        while(!minHeap.isEmpty()){
            // try {
            //     Thread.sleep(1000);
            // } catch (InterruptedException e1) {
            //     // TODO Auto-generated catch block
            //     e1.printStackTrace();
            // }
            int[] poll = minHeap.poll();

            // System.out.println(Arrays.toString(poll));

            ret.add(poll);
            cnt++;
            vis[poll[1]] = true;
            for(int i = 0;i < n;i++){
                if(g[poll[1]][i] != INF && !vis[i]){
                    minHeap.updateOrInsert(new int[]{poll[1], i, g[poll[1]][i]});
                }
            }
        }
        // System.out.println(cnt);
        if(cnt != n) return null;
        return ret;
    }

    public void test(){
        MinHeap minHeap = new MinHeap(10);
        minHeap.updateOrInsert(new int[]{1, 2, 4});
        minHeap.updateOrInsert(new int[]{2, 3, 3});
        minHeap.updateOrInsert(new int[]{1, 5, 2});
        minHeap.updateOrInsert(new int[]{2, 5, 1});
        System.out.println(Arrays.toString(minHeap.list.get(0)));
        System.out.println(Arrays.toString(minHeap.list.get(1)));
        System.out.println(Arrays.toString(minHeap.list.get(2)));
        System.out.println(Arrays.toString(minHeap.list.get(3)));
        System.out.println(Arrays.toString(minHeap.revIndex));
        System.out.println("size = " + minHeap.usedSize);
        // int[] poll = minHeap.poll();
        // System.out.println(Arrays.toString(poll));
        // poll = minHeap.poll();
        // System.out.println(Arrays.toString(poll));
    }

    public static void main1(String[] args) {
        new PrimOptimize().test();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();
        List<int[]> edges = new ArrayList<>();
        for(int i = 0;i < m;i++){
            int x = sc.nextInt() - 1, y = sc.nextInt() - 1, weight = sc.nextInt();
            edges.add(new int[]{x, y, weight});
        }
        List<int[]> ret = new PrimOptimize().primOptimize(n, edges);
        if(ret == null) System.out.println("orz");
        else{
            int sum = 0;
            for(int[] e : ret) sum += e[2];
            System.out.println(sum);   
        }
    }
}
/**
4 5
0 1 2
0 2 2
0 3 3
1 2 4
2 3 3
 */