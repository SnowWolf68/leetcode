### 中心拓展法

中心拓展法用于找到字符串`s`中的所有回文串

原理: 对于字符串`s`, 假设`n = s.length()`. 枚举`s`的所有**回文中心**, 从回文中心开始向两端扩展. 这里的回文中心即包括**奇回文中心**, 又包括**偶回文中心**

+ 一共有多少个回文中心? 
  对于字符串`s`, 且有`n = s.length()`. 那么奇回文中心有`n`个 (`s`中的每一个字符都是奇回文中心), 偶回文中心有`n - 1`个 (s中每两个字符之间的间隔都可以看做是一个偶回文中心)
  需要注意的是, 这里的偶回文中心不像Manacher算法那样考虑`s`最两侧的偶回文中心, 因此这里的偶回文中心只有`n - 1`个, 而不是`n + 1`个
  综上, 对于长度为`n`的字符串`s`, 一共有`2 * n - 1`个回文中心

+ 如何区分奇回文中心和偶回文中心?
  假设我们使用变量`i`来枚举这`2 * n - 1`个回文中心, 那么: 

  + 如果`i % 2 == 0`, 那么此时枚举到的是奇回文中心, 并且此时这个回文中心对应`s`中的下标就是`i / 2`

  + 如果`i % 2 != 0`, 那么此时枚举到的就是偶回文中心, 并且此时和偶回文中心相邻的`s`中的两个下标分别为 `floor(i / 2)`和`ceil(i / 2)`, 即`i / 2` 和`(i + 1) / 2`

    > 上取整的一个小技巧: 
    >
    > 对于正整数`i`而言, `ceil(i / 2) == (i + 1) / 2`, 相比使用`(int)Math.ceil(i / 2)`, 使用`(i + 1) / 2`能加快运算速度

时间复杂度: `O(n ^ 2)`

练习 (最长回文子串) ([LC5](https://leetcode.cn/problems/longest-palindromic-substring/description/))

```java
public String longestPalindrome(String s) {
      int maxLen = 0, n = s.length();
      String ret = "";
      // 2 * n - 1 个回文中心
      for(int i = 0;i < 2 * n - 1;i++){
          int l = -1, r = -1, curLen = -1;
          if(i % 2 == 0){
              // 奇回文
              l = i / 2 - 1;
              r = i / 2 + 1;
              curLen = 1;
          }else{
              // 偶回文
              l = i / 2;
              r = (i + 1) / 2;    // 等价于 i / 2 上取整
              curLen = 0;
          }
          while(l >= 0 && r < n && s.charAt(l) == s.charAt(r)){
              l--;
              r++;
              curLen += 2;
          }
          if(curLen > maxLen){
              maxLen = curLen;
              ret = s.substring(l + 1, r);
          }
      }
      return ret;
  }
```

