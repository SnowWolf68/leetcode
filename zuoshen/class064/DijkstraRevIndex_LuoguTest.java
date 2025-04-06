package zuoshen.class064;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
反向索引堆优化dijkstra

测试链接用的是 https://www.luogu.com.cn/problem/P4779
luogu的测试有一个点WA了, 还有几个点MLE了

反向索引堆优化dijkstra 时间复杂度: O((m + n) * logn) ???
 */
public class DijkstraRevIndex_LuoguTest {
    class RevIndexHeap{
        public List<int[]> list;   // [vertex, weight]
        public int[] revIndex;     // revIndex[vertex] = index in list
        public int usedSize;
        RevIndexHeap(int n){
            list = new ArrayList<>(n);
            for(int i = 0;i < n;i++) list.add(new int[2]);
            revIndex = new int[n];
            Arrays.fill(revIndex, -1);
            usedSize = 0;
        }

        private void shiftUp(int child){
            int pa = (child - 1) / 2;
            while(child > 0){
                if(list.get(child)[1] < list.get(pa)[1]){
                    swap(child, pa);
                }else break;
                child = pa;
                pa = (child - 1) / 2;
            }
        }

        private void shiftDown(int pa, int end){
            int lchild = pa * 2 + 1, rchild = pa * 2 + 2;
            while(lchild <= end || rchild <= end){
                if(rchild <= end){
                    // both
                    if(list.get(lchild)[1] < list.get(rchild)[1]){
                        if(list.get(lchild)[1] < list.get(pa)[1]){
                            swap(lchild, pa);
                            pa = lchild;
                        }else break;
                    }else{
                        if(list.get(rchild)[1] < list.get(pa)[1]){
                            swap(rchild, pa);
                            pa = rchild;
                        }else break;
                    }
                }else{
                    // only lchild
                    if(list.get(lchild)[1] < list.get(pa)[1]){
                        swap(lchild, pa);
                        pa = lchild;
                    }else break;
                }
                lchild = 2 * pa + 1;
                rchild = 2 * pa + 2;
            }
        }

        private void swap(int idx1, int idx2){
            revIndex[list.get(idx1)[0]] = idx2;
            revIndex[list.get(idx2)[0]] = idx1;
            int[] temp = list.get(idx1);
            list.set(idx1, list.get(idx2));
            list.set(idx2, temp);
        }

        public boolean isEmpty(){
            return usedSize == 0;
        }

        public void offerOrUpdate(int[] arr){
            if(revIndex[arr[0]] == -1){
                revIndex[arr[0]] = usedSize++;
                list.set(usedSize - 1, arr);
                shiftUp(usedSize - 1);
            }else{
                int idx = revIndex[arr[0]];
                if(arr[1] < list.get(idx)[1]){
                    list.set(idx, arr);
                    int pa = (idx - 1) / 2;
                    if(idx > 0 && list.get(idx)[1] < list.get(pa)[1]){
                        shiftUp(idx);
                    }else{
                        shiftDown(idx, usedSize - 1);
                    }
                }
            }
        }

        public int[] poll(){
            int[] poll = list.get(0);
            swap(0, --usedSize);
            revIndex[poll[0]] = -1;
            shiftDown(0, usedSize - 1);
            return poll;
        }
    }

    public int[] dijkstra(int[][] edges, int n, int k) {
        List<int[]>[] g = new ArrayList[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] time : edges){
            g[time[0] - 1].add(new int[]{time[1] - 1, time[2]});
        }
        k--;
        RevIndexHeap minHeap = new RevIndexHeap(n);
        int[] dist = new int[n];
        int INF = 0x3f3f3f3f;
        Arrays.fill(dist, INF);
        dist[k] = 0;
        minHeap.offerOrUpdate(new int[]{k, 0});
        while (!minHeap.isEmpty()) {
            int[] poll = minHeap.poll();
            for(int[] nxt : g[poll[0]]){
                if(dist[poll[0]] + nxt[1] < dist[nxt[0]]){
                    dist[nxt[0]] = dist[poll[0]] + nxt[1];
                    minHeap.offerOrUpdate(new int[]{nxt[0], dist[nxt[0]]});
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt(), s = sc.nextInt();
        int[][] edges = new int[m][3];
        for(int i = 0;i < m;i++){
            edges[i][0] = sc.nextInt();
            edges[i][1] = sc.nextInt();
            edges[i][2] = sc.nextInt();
        }
        int[] dist = new DijkstraRevIndex_LuoguTest().dijkstra(edges, n, s);
        for(int i = 0;i < n;i++){
            System.out.print(dist[i] + " ");
        }
    }
}
