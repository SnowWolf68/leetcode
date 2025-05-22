### Dijkstra时间复杂度

#### Dijkstra在邻接表, 邻接矩阵下使用朴素版和堆优化版的时间复杂度

> 以下默认 n = 点数, m = 边数

> 阅读顺序: 先看1和4, 再看2, 3
>
> 1, 4 的时间复杂度分析一定是正确的. 2, 3的时间复杂度分析仅是个人想法, 是否正确还需验证

##### 邻接矩阵 + 朴素版

```java
public int networkDelayTime(int[][] times, int n, int k) {
    int INF = 0x3f3f3f3f;
    k--;
    int[][] g = new int[n][n];
    for(int i = 0;i < n;i++) Arrays.fill(g[i], INF);
    for(int[] t : times){
        g[t[0] - 1][t[1] - 1] = t[2];
    }
    int[] dist = new int[n];
    Arrays.fill(dist, INF);
    boolean[] done = new boolean[n];
    dist[k] = 0;
    for(int i = 0;i < n;i++){
        int t = -1, minDis = INF;
        for(int j = 0;j < n;j++){
            if(!done[j] && dist[j] < minDis){
                minDis = dist[j];
                t = j;
            }
        }
        if(t == -1) break;
        done[t] = true;
        for(int j = 0;j < n;j++){
            if(g[t][j] != INF && dist[t] + g[t][j] < dist[j]){
                dist[j] = dist[t] + g[t][j];
            }
        }
    }
    int max = 0;
    for(int i = 0;i < n;i++) max = Math.max(max, dist[i]);
    return max == INF ? -1 : max;
}
```

时间复杂度: O(n ^ 2)     比较显然

##### 邻接矩阵 + 堆优化

```java
public int networkDelayTime(int[][] times, int n, int k) {
    int INF = 0x3f3f3f3f;
    k--;
    int[][] g = new int[n][n];
    for(int i = 0;i < n;i++) Arrays.fill(g[i], INF);
    for(int[] t : times){
        g[t[0] - 1][t[1] - 1] = t[2];
    }
    int[] dist = new int[n];
    Arrays.fill(dist, INF);
    boolean[] done = new boolean[n];
    dist[k] = 0;
    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    minHeap.offer(new int[]{k, 0});
    while (!minHeap.isEmpty()) {
        int[] poll = minHeap.poll();
        if(done[poll[0]]) continue;
        done[poll[0]] = true;
        for(int j = 0;j < n;j++){
            if(g[poll[0]][j] != INF && dist[poll[0]] + g[poll[0]][j] < dist[j]){
                dist[j] = dist[poll[0]] + g[poll[0]][j];
                minHeap.offer(new int[]{j, dist[j]});
            }
        }
    }
    int max = 0;
    for(int i = 0;i < n;i++) max = Math.max(max, dist[i]);
    return max == INF ? -1 : max;
}
```

时间复杂度: 

​	单独看`poll()`和`offer()`过程: 
​	`poll()`: 堆中有`O(m`)个元素(原因下述), 单次`poll()`时间复杂度`O(logm)`. 总`poll()`时间复杂度: `O(m * logm)`
​	`offer()`: 由于是邻接矩阵, 每次从堆中弹出一个顶点, 都要`O(n)`来遍历邻接点, 但由于`g[poll[0]][j] != INF`这个判断, 因此`offer()`执行次数仍然为`O(m)`. 因此总`offer()`的时间复杂度为`O(m * logm)`

综上: 时间复杂度为`O(m * logm)`, 但由于对于每个从堆中弹出的顶点, 都要花费`O(n)`的时间来遍历邻接点, 因此常数要比 邻接表 + 堆优化 的实现要大. 因此如果是用堆优化, 一般都和邻接表建图相结合

##### 邻接表 + 朴素版

```java
public int networkDelayTime(int[][] times, int n, int k) {
    int INF = 0x3f3f3f3f;
    k--;
    List<int[]>[] g = new List[n];
    for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
    for(int[] t : times){
        g[t[0] - 1].add(new int[]{t[1] - 1, t[2]});
    }
    int[] dist = new int[n];
    Arrays.fill(dist, INF);
    boolean[] done = new boolean[n];
    dist[k] = 0;
    for(int i = 0;i < n;i++){
        int t = -1, minDis = INF;
        for(int j = 0;j < n;j++){
            if(!done[j] && dist[j] < minDis){
                minDis = dist[j];
                t = j;
            }
        }
        if(t == -1) break;
        done[t] = true;
        for(int[] nxt : g[t]){
            if(dist[t] + nxt[1] < dist[nxt[0]]){
                dist[nxt[0]] = dist[t] + nxt[1];
            }
        }
    }
    int max = 0;
    for(int i = 0;i < n;i++) max = Math.max(max, dist[i]);
    return max == INF ? -1 : max;
}
```

时间复杂度: 

​	单独看内层的两次`for`循环. 
​	内层的第一次`for`循环: 内层循环`O(n)`, 外层`O(n)`, 总时间复杂度为`O(n ^ 2)`
​	内层的第二次`for`循环: 将内外层合起来看, 至多执行`O(m)`次

综上: 时间复杂度为`O(n ^ 2 + m)`



##### 邻接表 + 堆优化

```java
public int networkDelayTime(int[][] times, int n, int k) {
    int INF = 0x3f3f3f3f;
    k--;
    List<int[]>[] g = new List[n];
    for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
    for(int[] t : times){
        g[t[0] - 1].add(new int[]{t[1] - 1, t[2]});
    }
    int[] dist = new int[n];
    Arrays.fill(dist, INF);
    boolean[] done = new boolean[n];
    dist[k] = 0;
    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    minHeap.offer(new int[]{k, 0});
    while (!minHeap.isEmpty()) {
        int[] poll = minHeap.poll();
        if(done[poll[0]]) continue;
        done[poll[0]] = true;
        for(int[] nxt : g[poll[0]]){
            if(dist[poll[0]] + nxt[1] < dist[nxt[0]]){
                dist[nxt[0]] = dist[poll[0]] + nxt[1];
                minHeap.offer(new int[]{nxt[0], dist[nxt[0]]});
            }
        }
    }
    int max = 0;
    for(int i = 0;i < n;i++) max = Math.max(max, dist[i]);
    return max == INF ? -1 : max;
}
```

时间复杂度: 

​	根据代码流程可知, 每个顶点可以入堆很多次, 但至多出堆一次(`done`数组保证). 每次出堆都要将其所有邻接点加入到堆中
​	因此堆中的元素的数量级为`O(m)`. 		(切记不是`O(n)`!!!  一个元素可以多次入堆!!!)
​	单独看`poll()`和`offer()`过程
​	`poll()`: 堆大小`O(m)`, `while (!minHeap.isEmpty())`执行`O(m)`次, 每次`poll()`的时间复杂度为`O(logm)`. 因此这部分的时间复杂度为`O(m * logm)`
​	`offer()`: 定义"松弛"操作: 一次松弛操作指的是, 使用`u`更新其邻接点`v`的最短距离. 显然一次松弛操作对应`v`的一次入堆操作, 而堆中元素数量级为`O(m)`, 因此松弛操作的次数为`O(m)`. 每次松弛都要将邻接点`v`入堆, 且又由于单次入堆操作的时间复杂度为`O(logm)`, 因此`offer()`操作的总时间复杂度为`O(m * logm)`

综上: 时间复杂度为`O(m * logm + m * logm) = O(m * logm)`

由于对于任意的图, 有`m < n * (n - 1) / 2`, 即`m < n ^ 2`, 因此有`logm < 2 * logn`, 即`O(logm) < O(2 * logn)`, 即`O(logm) < O(logn)`. 因此上面的时间复杂度也可以写作`O(m * logn)`

特别的, 如果是稠密图, 有`m ~ n ^ 2`, 此时时间复杂度变成`O(n ^ 2 * logn)`, 显然比邻接矩阵 + 朴素版的`O(n ^ 2)`要差. 因此这种写法常用于稀疏图