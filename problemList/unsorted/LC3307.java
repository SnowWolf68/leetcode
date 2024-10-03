package problemList.unsorted;

/**
对于示例2: k = 10, operations = [0,1,0,1]
最终的字符串为: aabbaabbbbccbbcc

由于每一次不管是操作1还是操作2, 都会将原来的字符串长度增加一倍, 因此最终的字符串长度为2 ^ n     其中n = operations.length
我们可以将最终的字符串划分一下, 划分成前后两部分, 其下标范围分别是: [0, 2 ^ (n - 1) - 1], [2 ^ (n - 1), 2 ^ n]
其中, 后半部分是前半部分的子串经过operation得到的
要想求第k个字符, 即下标为(k - 1)的字符, 我们可以首先判断下标为k - 1的元素是在前半部分还是后半部分
如果是在前半部分, 即 (k - 1) < 2 ^ (n - 1), 那么此时下标(k - 1)的字符就转化为初始状态经过 operations[:n - 2]这些操作之后的字符
如果是在后半部分, 即(k - 1) >= 2 ^ (n - 1), 那么此时下标(k - 1)的字符就转化为初始状态经过 operations[:n - 2]之后再经过operations[n - 1]的字符

因此我们就将 初始状态经过operations[:n - 1]的字符 转化成 初始状态经过operations[:n - 2]的字符
出现了重复子问题, 可以使用递归解决
 */
public class LC3307 {
    public char kthCharacter(long k, int[] operations) {
        return recuise(k - 1, operations, operations.length - 1);
    }
    // return: 初始状态经过 operations[:i]之后的第k个字符
    private char recuise(long k, int[] operations, int i){
        if(i == 0){
            if(operations[0] == 0) return 'a';
            else return (char)('a' + k);
        }
        /**
        这里有一个细节: 由于i的范围可以到100, 即2 ^ i可以到2 ^ 100, 而k是long, 最大就是2 ^ 63 - 1
        因此这里直接让k和(1L << i)进行比较, 1L << i有可能发生溢出
        因此这里可以特殊判断一下, 如果i >= 63, 那么2 ^ i >= 2 ^ 63, 即2 ^ i超出了long的表示范围, 而又由于k是long类型
        因此在这种情况下, k 一定小于 2 ^ i
         */
        if(i >= 63 || k < (1L << i)) return recuise(k, operations, i - 1);
        else return (char)((((recuise(k - (1L << i), operations, i - 1) - 'a') + operations[i]) % 26) + 'a');
    }
}
