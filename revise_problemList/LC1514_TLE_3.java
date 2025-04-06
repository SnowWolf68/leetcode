package revise_problemList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LC1514_TLE_3 {
    class RevIndexHeap{
        public List<double[]> list;   // [vertex, weight]
        public int[] revIndex;     // revIndex[vertex] = index in list
        public int usedSize;
        RevIndexHeap(int n){
            list = new ArrayList<>(n);
            for(int i = 0;i < n;i++) list.add(new double[2]);
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
            revIndex[(int)list.get(idx1)[0]] = idx2;
            revIndex[(int)list.get(idx2)[0]] = idx1;
            double[] temp = list.get(idx1);
            list.set(idx1, list.get(idx2));
            list.set(idx2, temp);
        }

        public boolean isEmpty(){
            return usedSize == 0;
        }

        public void offerOrUpdate(double[] arr){
            if(revIndex[(int)arr[0]] == -1){
                revIndex[(int)arr[0]] = usedSize++;
                list.set(usedSize - 1, arr);
                shiftUp(usedSize - 1);
            }else{
                int idx = revIndex[(int)arr[0]];
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

        public double[] poll(){
            double[] poll = list.get(0);
            swap(0, --usedSize);
            revIndex[(int)poll[0]] = -1;
            shiftDown(0, usedSize - 1);
            return poll;
        }
    }

    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        List<double[]>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int i = 0;i < succProb.length;i++){
            g[edges[i][0]].add(new double[]{edges[i][1], succProb[i]});
            g[edges[i][1]].add(new double[]{edges[i][0], succProb[i]});
        }
        double[] dist = new double[n];
        dist[start_node] = 1;
        RevIndexHeap minHeap = new RevIndexHeap(n);
        minHeap.offerOrUpdate(new double[]{start_node, 1});
        while (!minHeap.isEmpty()) {
            double[] poll = minHeap.poll();
            int cur = (int)poll[0];
            for(double[] nxtE : g[cur]){
                int nxt = (int)nxtE[0];
                double prob = nxtE[1];
                if(dist[cur] * prob > dist[nxt]){
                    dist[nxt] = dist[cur] * prob;
                    minHeap.offerOrUpdate(new double[]{nxt, dist[nxt]});
                }
            }
        }
        return dist[end_node];
    }
}
