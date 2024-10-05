# z-func

首先介绍一下z函数的作用: z函数是用来快速判断一个字符串 某一个后缀 和 整个字符串 的匹配长度

具体来说: 

假设有一个字符串str, 那么这个字符串对应的z函数的含义如下: 

`z[i]` 表示字符串str中`[i, n - 1]`区间的子串(`n = str.length()`)和整个字符串`str`的匹配长度

**计算方法**

这里通过两个例子来解释z函数的主要思想

### 示例1

`str = "abababzabababab"`

![Alt text](image.png)

1. `z[0]` 这里指的是`str`这个字符串自身和自身匹配, 因此这里没有计算的意义, 如果一定要计算, 那么可以认为`z[0] = str.length()`
2. `z[1]` 这里由于没有任何前置条件, 因此只能暴力匹配, 比较`str[1]`与`str[0]`是否匹配, 发现不匹配, 因此`z[1] = 0`
![Alt text](image-1.png)
3. `z[2]` 在之前的计算中(即计算`z[1]`的过程中), 并没有给当前的计算提供额外的信息(具体可能会提供什么样的信息, 在后面都会提到), 因此这里还是暴力匹配, 比较`str[2]`与`str[0]`是否相等, 发现相等, 因此继续比较`str[3]`与`str[1]`, ... 一直比较到 `str[6] != str[4]`, 因此得出`z[2] = 4`
![Alt text](image-2.png)
4. 引入`z-box`: 通过`z[2]`的计算, 我们可以得到这样的信息: str字符串中, [2, 5]区间的子串, 和str的前缀是匹配的, 因此我们将str [2, 5] 的这个区间, 记作 z-box, z-box的作用在之后的计算中会用到
![Alt text](image-3.png)
5. `z[3]`: 暴力比较 `str[3]`和`str[0]`是否相等, 发现`str[3] != str[0]`, 因此`z[3] = 0`
6. `z[4]`: 4 这个下标落在了 z-box 中, 因此 z-box 可以为`z[4]`的计算提供一些信息: 
   
    首先我们想要知道`str[4]`和`str[0]`是否匹配, 可以转化一下, 由于4这个下标在当前的 z-box 当中, 那么意味着`str[4] == str[2]` (因为当前 z-box 的含义是 str [0, 3] 区间的字符串 和 [2, 5] 区间的子串匹配) 并且 之前我们计算出来了 `z[2] == 4` , 也就意味着 `str[2] == str[0], str[3] == str[1], ... str[5] == str[3]` 那么通过 `str[4] == str[2], str[2] == str[0]` 这两个式子我们可以得到 `str[4] == str[0]` 因此我们知道`z[4]`至少是1
    
    接下来需要判断 `str[5]`和`str[1]`是否匹配, 由于5这个下标依旧在当前的 z-box 当中, 因此仍然有`str[5] == str[3]`, 并且从之前计算出来的`z[2]`的信息中我们可以知道有这一串式子: `str[2] == str[0], str[3] == str[1], ... str[5] == str[3]`, 其中就有 `str[3] == str[1]`, 因此我们得到`str[5] == str[1]`, 因此`z[4]`至少是2

    继续判断`str[6]`和`str[2]`是否相等, 由于6这个下标已经超出了当前 z-box 的范围, 因此这次就不能利用之前 z-box 中的信息了, 只能暴力比较, 通过暴力比较得到 `str[6] != str[2]`, 因此最终 `z[4] == 2`

    如果你觉得上面的这几段解释比较繁琐, 也可以这样理解`z[4]`的计算过程: 
    
    当前的 z-box 的区间是 `[2, 5]`, 也就意味着 `str[1, 3]`区间和`str[2, 5]`区间的子串是相等的, 因此有`str[4] == str[2], str[5] == str[3]`, 而`z[2]`在之前已经算出来了, `z[2] == 2`, 也就意味着`str[2] == str[0], str[3] == str[1]`, 因此联合上面这两个式子可以得到`str[4] == str[0], str[5] == str[1]`, 因此我们可以知道, `z[4]`至少是2, 再往后匹配的话, z-box 就无法提供更多信息了, 因此需要暴力匹配, 比较`str[6]`和`str[2]`是否相等, 发现`str[6] != str[2]`, 因此最终`z[4] == 2`
![Alt text](image-4.png)

    计算完`z[4] == 2`之后, 更新 z-box 为`[4, 5]`这个区间
![Alt text](image-5.png)

7.  `z[5]`: 5 这个下标也落在了 z-box 当中, 因此可以利用 z-box 中的信息, 通过z-box 中提供的信息可以知道`str[5] == str[1]`, 而`z[1]`之前已经计算出来`z[1] == 0`, 意味着`str[1] != str[0]`, 因此我们得到`str[5] != str[0]`, 因此`z[5] == 0`
![Alt text](image-6.png)
8.  `z[6]`: 6这个下标落在了当前 z-box 的外面, 因此需要暴力匹配, 暴力比较`str[6]`和`str[0]`, 发现`str[6] != str[0]`, 因此`z[6] = 0`
![Alt text](image-7.png)
9. `z[7]`: 7这个下标落在当前 z-box 外面, 暴力匹配得到`z[7] == 6`
![Alt text](image-8.png)
更新 z-box 为`[7, 12]`这个区间
![Alt text](image-9.png)
10.  `z[8]`: 下标8在当前 z-box 当中, 可以利用z-box中的信息, 通过z-box可以知道, `str[8] == str[1]`, 通过`z[1] == 0`可以知道`str[1] != str[0]`, 因此有`str[8] != str[0]`, 因此`z[8] = 0`
11.  `z[9]`: 依旧是落在z-box区间中, 通过z-box得到`str[9] == str[2], str[10] == str[3], str[11] == str[4], str[12] == str[5]`, 通过`z[2] == 4`可以知道`str[2] == str[0], str[3] == str[1], str[4] == str[2], str[5] == str[3]`, 联合上面两个式子可以得到`str[9] == str[0], str[10] == str[1], str[11] == str[2], str[12] == str[3]`, 再往后面匹配`str[13]`与`str[4]`时, 当前z-box就无法提供更多信息了, 就需要暴力匹配, 暴力匹配`str[13] == str[4]`, 继续暴力匹配`str[14] == str[5]`, 继续往后就没有字符了, 因此得到`z[9] == 6`
![Alt text](image-10.png)
更新当前z-box为`[9, 14]`这个区间
![Alt text](image-11.png)
12.  `z[10]`: 落在z-box里面, 因此有`str[10] == str[1]`, 通过`z[1] == 0`得到`str[1] != str[0]`, 因此`str[10] != str[0]`, 故`z[10] = 0`
13.  `z[11]`: 落在z-box里面, 因此有`str[11] == str[2], str[12] == str[3], str[13] == str[4], str[14] == str[5]`, 通过`z[2] == 4`得到`str[2] == str[0], str[3] == str[1], str[4] == str[2], str[5] == str[3]`, 联合上面两个式子得到`str[11] == str[0], str[12] == str[1], str[13] == str[2], str[14] == str[3]`, 因此得到`z[11] = 4`
![Alt text](image-12.png)
更新z-box为`[11, 14]`这个范围
14.  `z[12]`: 落在z-box里面, 因此有`str[12] == str[1]`, 通过`z[1] == 0`得到`str[1] != str[0]`, 因此`str[12] != str[0]`, 因此`z[12] = 0`
![Alt text](image-12.png)
15.  `z[13]`: 落在z-box当中, 因此有`str[13] == str[2], str[14] == str[3]`, 通过`z[2] == 4`得到`str[2] == str[0], str[3] == str[1], ... str[5] == str[3]`, 联立上面两个式子得到`str[13] == str[0], str[14] == str[1]`, 因此`z[13] = 2`
![Alt text](image-13.png)
同理更新 z-box 为 `[13, 14]`这个区间
![Alt text](image-14.png)
16.  `z[14]`: 14这个下标落在 z-box 当中, 因此有`str[14] == str[1]`, 而`z[1] == 0`说明`str[1] != str[0]`, 因此得到`str[14] != str[0]`, 因此`z[14] = 0`
![Alt text](image-15.png)

这样我们就得到了`str`对应的`z`数组

>注: 在上面`z[3]`的计算中, 我是用的直接比较`str[3]`和`str[0]`是否相等, 而没有利用到当前的z-box, 这是因为此时对于读者来说是第一次接触z-box, 如果直接使用z-box来进行比较可能会看不懂, 因此我就用的暴力比较来得到的`z[3]`的值, 但是相信你看到这里, 其实也就大概明白了z-box的原理, 因此这里可以更正一下`z[3]`的计算方法: 
>
>`z[3]`: 3这个下标落在了当前的 z-box 当中, 因此有`str[3] == str[1]`, 而`z[1] == 0`, 说明`str[1] != str[0]`, 通过上面两个式子得到`str[3] != str[0]`, 因此得到`z[3] = 0`


### 示例2

通过示例1, 应该可以大概理解z-func的主要思想, 下面我会再用一个示例再加深一下印象

`str = aabcaabxaaaz`

![Alt text](image-17.png)

1. `z[0]`: 没有意义
2. `z[1]`: 暴力匹配, `str[1] == str[0], str[2] != str[1]`, 因此`z[1] = 1`, 并且更新当前z-box区间为`[1]`
![Alt text](image-18.png)
3. `z[2]`: 2这个下标不在当前z-box当中, 继续暴力匹配, `str[2] != str[0]`, 因此`z[2] = 0`
![Alt text](image-19.png)
4. `z[3]`: 依旧不在z-box当中, 因此继续暴力匹配, `str[3] != str[0]`, 因此`z[3] = 0`
![Alt text](image-20.png)
5. `z[4]`: 4不在z-box当中, 因此暴力匹配, `str[4] == str[0], str[5] == str[1], str[6] == str[2], str[7] != str[3]`, 因此`z[4] == 3`, 并且更新当前z-box区间为`[4, 6]`
![Alt text](image-21.png)
6. `z[5]`: 5这个下标在当前z-box当中, 因此有`str[5] == str[1], str[6] == str[2]`, 并且前面算出来了`z[1] == 1`, 因此有`str[1] == str[0], str[2] != str[1]`, 联合上面两个式子有`str[5] == str[0], str[6] != str[1]`, 因此`z[5] = 1`
![Alt text](image-22.png)
7. `z[6]`: 6这个下标也在当前z-box当中, 因此有`str[6] == str[2]`, 而`z[2] == 0`, 因此有`str[2] != str[0]`, 联合上面两个式子, 有`str[6] != str[0]`, 因此`z[6] = 0`
![Alt text](image-23.png)
8. `z[7]`: 7这个下标在z-box外面, 因此暴力匹配, `str[7] != str[0]`, 因此`z[7] = 0`
![Alt text](image-24.png)
9. `z[8]`: 8这个下标也不在z-box里面, 因此继续暴力匹配, `str[8] == str[0], str[9] == str[1], str[10] != str[2]`, 因此`z[8] = 2`
![Alt text](image-25.png)
并且更新当前z-box区间为`[8, 9]`
10. `z[9]`: 9这个下标在当前z-box当中, 因此有`str[9] == str[1]`, 而`z[1] == 1`, 意味着`str[1] == str[0]`, 联合上面两个式子, 有`str[9] == str[0]`, 因此我们知道`z[9]`至少是1, 因为继续往后匹配时, 就超出了z-box的范围, z-box无法继续提供信息, 因此需要暴力匹配, `str[10] == str[1], str[11] != str[2]`, 因此最终`z[9] = 2`, 并且更新当前z-box区间为`[9, 10]`
![Alt text](image-26.png)
11. `z[10]`: 10这个下标在当前z-box当中, 因此有`str[10] == str[1]`, 而`z[1] == 1`, 因此有`str[1] == str[0]`, 联合上面两个式子有`str[10] == str[0]`, 因此得到`z[10]`至少为1, 继续向后暴力匹配, `str[11] != str[1]`, 因此最终`z[10] = 1`, 更新z-box区间为`[10]`
![Alt text](image-27.png)
12. `z[11]`: 11这个下标不在当前z-box当中, 因此进行暴力匹配, `str[11] != str[0]`, 因此`z[0] = 0`
![Alt text](image-28.png)

因此最终z函数的结果为

![Alt text](image-29.png)


**总结**

通过上面两个例子, 我们可以得到z-func的主要思想

1. 如果当前要计算的这个下标不在当前的z-box当中, 或者此时压根就没有z-box, 那么此时需要暴力匹配
2. 如果从某一个下标`i`开始的后缀, 能够匹配`str`的某一个前缀, 假设从i开始的后缀的范围是`[i, i + len]`, 匹配的前缀的区间为`[0, len]`, 那么更新当前的z-box区间为`[i, i + len]`
3. 如果当前的下标`i`落在z-box当中, 那么此时就可以利用z-box中的信息来加速匹配过程(就不再是暴力匹配了)
   
   具体来说, 假设当前z-box的范围是`[zLeft, zRight]`(并且满足`zLeft <= i <= zRight`, 因为当前下标`i`落在z-box当中), 那么首先我们知道当前z-box能够提供的信息的最长的长度就是`zRight - i + 1`
   
   通过当前的z-box的范围我们可以知道`[i, zRight]`这个区间 和 `[i - zLeft, zRight - zLeft]`这个区间的子串是相等的(即下图中两个绿色阴影区域), 而`[i - zLeft, zRight - zLeft]`这个区间(即下图中左边的这个阴影区间)中`z[i - zLeft]`这个位置的z函数值我们是知道的, 也就是`i - zLeft`这个下标开始的后缀, 和整个字符串`str`能够匹配`z[i - zLeft]`这些长度, 因此我们可以知道`i`这个下标开始的后缀, 和整个字符串`str`能够匹配的长度应该至少为`z[i - zLeft]`这些长度, 但是需要注意, 这里`z[i]`的长度还会受到前一段分析出来的`zRight - i + 1`的限制, 因为如果`z[i - left] > zRight - i + 1`, 那么多出来的这一部分实际上就不在当前的z-box当中了, 换句话说, 多出来的这部分就超出了下图中的绿色阴影部分的范围了, 因此应该取`min(z[i - left], zRight - i + 1)`

   因此综合上面这两段分析出来的信息, 我们可以知道, 当`i`在z-box当中时, 当前`z[i]`应该至少为`min(zRight - i + 1, z[i - zLeft])`, 所谓"至少"的意思就是: 

    1. 如果 `z[i - zLeft] <= zRight - i + 1` , 那么不需要继续暴力匹配, 因为z-box中的信息足以证明`z[i] = z[i - Left]`
    2. 如果`z[i - zLeft] > zRight - i + 1`, 那么此时`z[i]`至少是`zRight - i + 1`, 至于是否还可以继续向后匹配, 此时z-box就不能再提供信息了, 只能继续向后暴力匹配

    对于这一点, 可以总结一下: 如果`i`落在当前z-box当中, 那么只需要从`i + min(z[i - zLeft], zRight - i + 1)`这个下标开始继续向后暴力匹配即可
   
![Alt text](image-16.png)

4. 每次计算完`z[i]`之后, 如果`z[i] > 0`, 那么更新当前的z-box, 更新为`[i, i + z[i] - 1]`

### z-func 代码实现

通过上面总结中这四点的分析, 其实不难写出求一个字符串的z数组的代码

```
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
```

### 时间复杂度

在计算z数组的过程中, z-box 始终都是向右移动的, 类似滑动窗口, 因此z-func的时间复杂度是O(n)

# kmp

### 主要思想

kmp解决的是 "文本串 和 模式串 的匹配问题" , 具体来说, 假设文本串为`text`, 模式串为`pattern`, 那么kmp就是用来求 模式串`pattern` 在 文本串`text` 中出现了多少次?

在介绍kmp之前, 我们首先来看一下暴力的匹配是如何实现的

考虑下面这个例子: `text = abbaabbaaba, pattern = abbaaba`

暴力匹配的过程: 

1. 尝试`text`从下标0开始的, 长度为`pattern.length()`的子串是否可以和`pattern`匹配: 首先比较`text[0]`和`pattern[0]`, 发现`text[0] == pattern[0]`, 然后继续比较1下标, ... 一直比较到`text[6] != pattern[6]`, 说明`text`以0下标开始的, 长度为`pattern.length()`的子串无法和`pattern`匹配
2. 尝试`text`从下标1开始的, 长度为`pattern.length()`的子串是否可以和`pattern`匹配: 首先比较`text[1]`和`pattern[0]`, 发现`text[1] != pattern[0]`, 因此无法匹配
3. 尝试`text`从下标2开始的, 长度为`pattern.length()`的子串是否可以和`pattern`匹配, ...
4. ...

通过上面的暴力匹配过程可以看到, 假设当前匹配的是`text`以下标`i`开始的子串和`pattern`是否匹配, 并且当前比较到了`text[j]`和`pattern[k]`这个位置(`j > i, k > 0`), 如果当前这两个字符不匹配, 即`text[j] != pattern[k]`, 那么接下来需要让`j`**回退到**`i + 1`, `k`**回退到**`0`, 重新开始匹配

通过上面的分析可以看到, 暴力匹配的效率低的原因在于: **每次发生不匹配时, `text`文本串的下标`j`都需要回退到`i + 1`这个位置开始重新匹配**

我们可以这样想: 既然当前都匹配到了`j`这个下标, 那么说明文本串`text`的`[i, j - 1]`区间的子串和模式串`pattern`的`[0, j - i - 1]`区间的子串是相等的, 换句话说, **`text`串中`[i, j - 1]`区间的字符信息是已知的**, 既然是已知的, 那么就不需要从`i + 1`下标开始继续和`pattern`串匹配, 而是可以**直接得到`text`的`[i + 1, j - 1]`区间和`pattern`串匹配的长度**(具体怎么得到后续会说), 需要注意的是, 这里的匹配指的是`text`串的`[i + 1, j - 1]`区间的**后缀**和`pattern`串的**前缀**的最大匹配

因此`text`串的**下标`j`就无需回退**, 假设`text[i + 1, j - 1]`区间的**后缀**和`pattern`串的**前缀**最大匹配长度为`len`, 那么我们可以直接尝试`text`中以下标`j - len`开始的子串和`pattern`串是否匹配, 即比较字符`text[j]`和`pattern[len]`是否相等即可, 此时也可以看到, `pattern`串的下标`k`也**不需要回退到`0`**, 而是只需要回退到`len`这个下标即可

上面这段中体现出来的思想就是kmp的主要思想

在上面的分析中, 还有一个问题没有解决, 就是如何事先计算 `text`的`[i + 1, j - 1]`区间的**后缀**和`pattern`串的**前缀**最大匹配的长度

这里我们可以把表达方式转化一下, 将 `text`的`[i + 1, j - 1]`区间的**后缀** 转化成 `text`的`[i, j - 1]`区间的**真后缀**, 所谓 **真后缀**, 即**不包括`0`这个下标的这些后缀**

在上面的分析中我们知道, 这里的前提是`text`串的`[i, j - 1]`区间和`pattern`串的`[0, j - i - 1]`区间的子串是匹配的, 因此求 `text`的`[i, j - 1]`区间的的**真后缀**和`pattern`串的**前缀**的最大匹配的长度, 实际上就是求 `pattern`的`[0, j - i - 1]`区间的**真后缀**和`pattern`串的**前缀**的最大匹配长度, 显然这个长度只和`pattern`串有关, 因此我们可以事先通过计算`pattern`得到

如何计算 `pattern`的`[0, j - i - 1]`区间的**真后缀**和`pattern`串的**前缀**的最大匹配长度 ?

这里下标可以简化一下, 实际上就是求`pattern`串的`[0, i]`区间的**真后缀**和`pattern`串的**前缀**的最大匹配长度

> 这里由于`pattern`的`[0, i]`区间取的是**真后缀**, 即后缀长度肯定小于`pattern.length()`, 因此这里的前缀也可以理解为**真前缀**, 即**不包括当前子串的最后一个字符**的前缀

求`pattern`串`[0, i]`区间的**真后缀**和`pattern`串的**真前缀**的最大匹配长度

假设`pi[i]`表示`pattern`串`[0, i]`区间的**真后缀**和`pattern`串的**真前缀**的最大匹配长度, 这里我们就是需要通过`pattern`串, 计算出`pi`数组

> 这里的`pi`数组, 也叫做**π数组**

我们可以通过下面一个例子来理解`pi`数组的计算方式: 

`pattern = abababzabababa`

|pattern| a | b | a | b | a | b | z | a | b | a | b | a | b | a | 
| ----- | - | - | - | - | - | - | - | - | - | - | - | - | - | - |
| p[i]  | 0 | 0 | 1 | 2 | 3 | 4 | 0 | 1 | 2 | 3 | 4 | 5 | 6 | ? |

这里假设`pattern`串的`[0, 13]`区间对应的`pi`数组已经被计算出来(你可以认为这些`pi`都是基于暴力匹配计算出来的), 接下来我将会通过`pi[14]`的计算, 来介绍`pi`数组的计算方式

首先有`pi[13] == 6`, 也就是意味着`pattern`的`[0, 13]`区间的**真后缀**和`pattern`的**真前缀**的最大匹配长度为6, 当计算`pi[14]`时, 因为在这之前`pi[13]`已经被计算出来了, 因此首先我们会**尝试看看`pattern`的`[0, 14]`区间能够匹配的最大真后缀能不能由`[0, 13]`区间匹配的最大真后缀 拼接上`pattern[14]`这个字符得到**(是不是有点dp的味道?), 因此比较`pattern[14]`和`pattern[6]`是否相等, 因为如果相等, 那么意味着`pi[14] = pi[13] + 1`, 即`pi[14] = 7`, 但是发现`pattern[14] != pattern[6]`, 也就意味着`pi[14]`此时真后缀的最长匹配长度不能由`pi[13]`的真后缀的最长匹配长度 +1 得来

重点来了

首先我们需要关注`[0, 13]`区间的真后缀最长匹配是什么, 不难发现这个真后缀应该是`ababab`, 然后我们再关注`[0, 13]`区间真后缀次长匹配是什么, 这个真后缀应该是`abab`

可以发现, 次大匹配`abab`一定在最大匹配`ababab`中, 换句话说, **最大匹配`ababab`中真后缀和真前缀的最大匹配, 就是这里的`abab`, 即`[0, 13]`区间的次大匹配**

因此回到上面的分析, 当我们发现`pattern[14] != pattern[6]`时, 即`pi[14]`不能由`pi[13] + 1`拓展而来, 即不能由`[0, 13]`中的最大匹配拓展而来, 那么此时我们就需要考虑`pi[14]`能不能由`[0, 13]`区间的次大匹配拓展而来

`[0, 13]`区间的次大匹配如何表示? 通过之前的分析, 次大匹配可以看为 最大匹配串中的的最大匹配, 因此`[0, 13]`区间的次大匹配长度就为`pi[pi[13] - 1] = 4`, 对应的串就是`abab`, 因此这里尝试使用`[0, 13]`区间的次大匹配拓展时, 需要比较`pattern[14]`和`pattern[pi[pi[13] - 1]]`是否相等(这里`pi[pi[13] - 1]`是次大匹配串的结束下标的后一个字符的下标), 其中通过前面的表可以知道, `pi[pi[13] - 1] == 4`, 因此就是比较`pattern[4]`和`pattern[14]`是否相等, 通过比较发现`pattern[4] == pattern[14]`, 因此`pi[14]`可以由`[0, 13]`区间真后缀和`pattern`前缀的次大匹配串(`abab`)通过拼接上`pattern[14]`这个字母拓展而来, 即`pi[14] = pi[pi[13] - 1]] + 1`

拓展一下, 在这个例子中, `[0, 14]`区间真后缀和`pattern`的真前缀的最长匹配长度可以由`[0, 13]`区间的次大匹配拓展而来, 进一步, 如果`[0, 14]`区间的真后缀和`pattern`的真前缀不能由`[0, 13]`区间的次大匹配拓展而来, 那么又应该如何处理? 

类似的, 如果`pi]14`不能由`[0, 13]`区间的次大匹配`abab`通过 +1 拓展而来, 那么我们可以继续找`abab`中的最大匹配匹配, 即`[0, 13]`区间的第三大匹配串, 在这个例子中, 就是`ab`这个子串, 然后尝试`pi[14]`能不能由`[0, 13]`区间中真后缀和`pattern`串的真前缀的第三大匹配长度, 即`2`通过 +1 拓展而来, 通过代码表示就是, 判断`text[14]`和`pattern[pi[pi[pi[13] - 1] - 1]]`是否相等, 如果相等, 那么`pi[14] = pi[pi[pi[13] - 1] - 1]] + 1`

> 这里嵌套层数比较多, 我再解释一下: 
> 
> 对于`pattern[pi[pi[pi[13] - 1] - 1]]`这个式子, 我们一层一层来看, 首先`pi[13]`指的是`[0, 13]`区间的真后缀和`pattern`真前缀的最大匹配长度, 而`pi[13] - 1`就是这个最大匹配串的结束下标, `pi[pi[13] - 1]` 就是找最大匹配的最大匹配, 即找次大匹配, 即`[0, 13]`区间的次大匹配的长度, 进一步的, `pi[pi[13] - 1] - 1`就是次大匹配串的结束下标, 然后`pi[pi[pi[13] - 1] - 1]`就是找次大匹配中的最大匹配, 即`[0, 13]`区间的第三大匹配, 然后`pi[pi[pi[13] - 1] - 1] - 1`就是找第三大匹配串的结束下标
>
> 让`pi[pi[pi[13] - 1] - 1] - 1 + 1`, 即`pi[pi[pi[13] - 1] - 1]`, 得到的就是第三大匹配串的结束下标的下一个字符
> 
> 而我们需要比较的正是 第三大匹配串结束下标的**后一个字符** 和 `pattern[14]` 是否相等, 即比较`pattern[pi[pi[pi[13] - 1] - 1]]`和`pattern[14]`, 如果相等, 说明`pi[14]`可以由第三大匹配串的长度 +1 得来, 即`pi[14] = pi[pi[pi[13] - 1] - 1] + 1`

这里确实会很绕, 如果看不明白可以画一个图结合之前给出的`pattern`示例多看几遍

**总结**

总结一下`pi[]`数组的计算方式: 当计算`pi[i]`的时候, 我们需要依次找`[0, i - 1]`区间的最大匹配, 比较能不能通过最大匹配拓展, 比较规则为: 假设最大匹配长度为`maxLen`, 那么比较`pattern[maxLen]`是否和`pattern[i]`相等(即判断最大匹配串的结束下标的下一个字符是否与`pattern[i]`相等), 如果不能就找次大匹配, 还不能就找第三大匹配, ... , 假设这里我们找到的`[0, i - 1]`中第一个能够匹配的长度为`len`, 并且有`pattern[len] = pattern[i]`, 即某一大匹配串的结束下标的后一个字符和`pattern[i]`相等, 那么此时`pi[i] = len + 1`, 特别的, 如果一直往前找始终没有找到符合要求的某一大匹配, 那么此时匹配长度就是`0`, 那么此时就意味着需要比较`pattern[0]`和`pattern[i]`是否相等, 即此时真后缀和真前缀的最大匹配长度 至多为1 , 如果`pattern[0] == pattern[i]`, 那匹配长度为`1`, 否则匹配长度为`0`

通过上面的分析, 我们不难写出计算`pattern`串的`pi[]`数组的代码

```
int[] getPi(String pattern){
    int n = pattern.length();
    int[] pi = new int[n];
    pi[0] = 0;  // 0位置无意义
    // matchLen表示上一个位置的最大匹配长度, 即matchLen = pi[i - 1]
    int matchLen = 0;
    for(int i = 1;i < n;i++){
        while(matchLen > 0 && pattern.charAt(matchLen) != pattern.charAt(i)){
            // 找[0, matchLen - 1]区间的最大匹配
            matchLen = pi[matchLen - 1];
        }
        if(pattern.charAt(matchLen) == pattern.charAt(i)){
            matchLen++;
        }
        pi[i] = matchLen;
    }
    return pi;
}
```

然后是kmp部分, 现在我们结合`pi[]`数组的定义, 重新总结一下kmp的思想: 

假设当前进行kmp的两个串分别为`text`和`pattern`: 

首先尝试`text`串`0`下标开始的子串能不能和`pattern`串匹配

进行暴力匹配, 当匹配到第一个不相等的字符, 即`text[i] != pattern[j]`时, 说明`text`串以`0`下标开始的子串不能和`pattern`串匹配, 那么此时`i`不需要回退到`1`重新开始匹配, 而是找`text`中`[0, i - 1]`区间的真后缀和`pattern`中`[0, j - 1]`区间的真前缀的最大匹配长度, 即`pattern[0, j - 1]`区间的真后缀和`pattern`的真前缀的最大匹配长度, 并且尝试将`text[0, i - 1]`区间的符合上述要求的真后缀和`pattern`进行匹配

而`pattern[0, j - 1]`区间真后缀和真前缀的最大匹配长度保存在`pi[]`数组当中, 即`pi[j - 1]`, 因此我们只需要比较`text[i]`是否和`pattern[pi[j - 1]]`相等即可, 即`i`不回退, 同时`j`回退到`pi[j - 1]`

如果`text[i] == pattern[pi[j - 1]]`, 那么说明找到了一个`text[0, i]`区间的真后缀和`pattern`的真前缀匹配, 因此可以继续让`i++, j++`, 继续匹配即可

如果`text[i] != pattern[pi[j - 1]]`, 那么说明还没有找到一个匹配的真前缀和真后缀, 因此还需要继续向前找, 因此继续找`text`中`[i - pi[j - 1], i - 1]`区间中的真后缀和`pattern`真前缀的最大匹配长度, 即`pattern`中`[0, pi[j - 1] - 1]`区间的真后缀和`pattern`真前缀的最大匹配长度, 即`pi[pi[j - 1] - 1]`, 然后比较`text[i]`是否和`pi[pi[j - 1] - 1]`相等, 如果相等, 那么`i++, j++`继续匹配, 否则还需要继续重复上述过程

有一点需要单独强调一下, 如果找到了`text`串中以`i`开头的子串, 和`pattern`能够匹配, 换句话说, 如果`j == pattern.length()`, 那么此时说明找到了一个`pattern`能够和`text`中的某一个子串匹配, 那么此时应该如何继续匹配? 

和之前 不匹配 的过程类似, `i`还是不回退, 同时`j`回退到`pi[j - 1]`继续匹配

> 注: 这里的`j`即是`pattern`串中匹配的下标, 也可以理解为 当前匹配的长度 

因此kmp的代码如下

```
/**
* kmp
* @param text
* @param pattern
* @return : text中所有和pattern匹配的子串的起始下标
*/
List<Integer> kmp(String text, String pattern){
    List<Integer> ret = new ArrayList<>();
    int[] pi = Pi.getPi(pattern);
    // matchLen指的是当前匹配的长度
    int matchLen = 0;
    for(int i = 0;i < text.length();i++){
        // 如果不匹配, i不回退, 同时matchLen循环回退
        while(matchLen > 0 && text.charAt(i) != pattern.charAt(matchLen){
            matchLen = pi[matchLen - 1];
        }
        if(text.charAt(i) == pattern.charAt(matchLen)){
            matchLen++;
        }
        if(matchLen == pattern.length()){
            ret.add(i);
            matchLen = pi[matchLen - 1];
        }
    }
    return ret;
}
```




# Problems

#### [LC3031](https://leetcode.cn/problems/minimum-time-to-revert-word-to-initial-state-ii/description/)

z-func

```
public int minimumTimeToInitialState(String word, int k) {
    int n = word.length();
    int[] z = zFunc(word);
    for(int i = 0;i < n - 1;i++){
        if((i + 1) % k == 0 && z[i + 1] == n - i - 1){
            return (i + 1) / k;
        }
    }
    return n % k == 0 ? n / k : n / k + 1;
}

int[] zFunc(String str){
    int n = str.length();
    int[] z = new int[n];
    z[0] = n;
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
```

#### [LC3036](https://leetcode.cn/problems/number-of-subarrays-that-match-a-pattern-ii/description/)

kmp

```
public int countMatchingSubarrays(int[] nums, int[] pattern) {
    // kmp
    int n = nums.length, m = pattern.length;
    int[] info = new int[n - 1];
    for(int i = 0;i < n - 1;i++){
        if(nums[i] < nums[i + 1]) info[i] = 1;
        else if(nums[i] > nums[i + 1]) info[i] = -1;
        else info[i] = 0;
    }
    System.out.println(Arrays.toString(info));
    List<Integer> ret = kmp(info, pattern);
    return ret.size();
}
int[] getPi(int[] pattern){
    int n = pattern.length;
    int[] pi = new int[n];
    pi[0] = 0;  // 0位置无意义
    // matchLen表示上一个位置的最大匹配长度, 即matchLen = pi[i - 1]
    int matchLen = 0;
    for(int i = 1;i < n;i++){
        while(matchLen > 0 && pattern[matchLen] != pattern[i]){
            // 找[0, matchLen - 1]区间的最大匹配
            matchLen = pi[matchLen - 1];
        }
        if(pattern[matchLen] == pattern[i]){
            matchLen++;
        }
        pi[i] = matchLen;
    }
    return pi;
}
List<Integer> kmp(int[] text, int[] pattern){
    List<Integer> ret = new ArrayList<>();
    int[] pi = getPi(pattern);
    // matchLen指的是当前匹配的长度
    int matchLen = 0;
    for(int i = 0;i < text.length;i++){
        // 如果不匹配, i不回退, 同时matchLen循环回退
        while(matchLen > 0 && text[i] != pattern[matchLen]){
            matchLen = pi[matchLen - 1];
        }
        if(text[i] == pattern[matchLen]){
            matchLen++;
        }
        if(matchLen == pattern.length){
            ret.add(i);
            matchLen = pi[matchLen - 1];
        }
    }
    return ret;
}
```