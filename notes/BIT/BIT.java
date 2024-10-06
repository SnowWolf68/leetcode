package notes.BIT;

public class BIT {
    private int[] tree;
    // len是tree数组的长度, 树状数组的下标范围是[1, len - 1], 即[1, n]
    public int len;

    // n: 下标范围[1, n]
    BIT(int n){
        this.len = n + 1;
        this.tree = new int[this.len];
    }

    private int pre(int i){
        int ret = 0;
        while(i > 0){
            ret += this.tree[i];
            i &= i - 1;
        }
        return ret;
    }

    private void update(int i, int x){
        while(i < this.tree.length){
            this.tree[i] += x;
            i += i & (-i);
        }
    }
}