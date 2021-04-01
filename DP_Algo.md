# 动态规划

## 主要模型

https://mp.weixin.qq.com/s?__biz=Mzg2NjU1MzIxNg==&mid=2247483846&idx=1&sn=7691121f35968b55b2474681d16ebb24&chksm=ce485d29f93fd43fe8b8645c5b6a77e2fbab86be395e16d87ba6e1470c3afa065df7712e7e3e&token=1626063786&lang=zh_CN#rd

## 1、线性DP

最小上升子序列
dp[i] = max{dp[j] } + 1   (0<=  j < i  , num[j] < num[i])

## 2、区间DP

## 3、树形DP

## 4、坐标DP



0 - 1 背包 问题 里的表的启发：

​	用手算模拟动态规划的算法计算背包问题时，可以感觉到有了表进行记录，求解过程加快了不少，是 有选择的穷举  “careful brute force”

## 例题

### 1.矩阵连乘

<img src="C:\Users\sushi\Desktop\Typora\DP_Algo.assets\image-20210305143904980.png" alt="image-20210305143904980" style="zoom: 67%;" />

![image-20210305144056519](C:\Users\sushi\Desktop\Typora\DP_Algo.assets\image-20210305144056519.png)

### 2.0-1 背包

#### 问题描述：

![image-20210305153245439](C:\Users\sushi\Desktop\Typora\DP_Algo.assets\image-20210305153245439.png)

#### 状态转移方程：

![image-20210305153326404](C:\Users\sushi\Desktop\Typora\DP_Algo.assets\image-20210305153326404.png)

其中m(i,j) 代表 第i 种到 第n 种 物品可选时的最优值。

#### DP表

![image-20210305161618856](C:\Users\sushi\Desktop\Typora\DP_Algo.assets\image-20210305161618856.png)

#### 动态规划适合求最值。

动态规划相比于回溯法 不能直接得到最优解，动态规划更关注 最优值，在求解过程中隐藏了”最优解“, 隐藏最优解的同时也减少了 决策树的树枝（这里不一定对，我个人感觉而已）。动态规划适合求最值。题目要求某个最值的话，可能在暗示使用动态规划。



#### 代码

求最优值代码

~~~c++
void knapsack(Type v, int *w, int c, int n, Type **m) 
{  int jMax=min(w[n]-1,c);
   for (int j=0;j<=jMax;j++) m[n][j]=0; //可选物品只有n但装不下
   for (int j=w[n];j<=c;j++) m[n][j]=v[n]; //可选物品只有n且可装
   for (int i=n-1; i>1; i--)
   {  jMax=min(w[i]-1, c);
       for (int j=0; j<=jMax; j++) 
              m[i][j]=m[i+1][j]; //物品i不能被选，容量不够
       for (int j=w[i]; j<=c; j++) 
              m[i][j]=max(m[i+1][j], m[i+1][j-w[i]]+v[i]);
   }   //物品i可能选入，容量够看价值：不装i和装入i比价值
   m[1][c]=m[2][c];         
   if (c>=w[1]) m[1][c]=max(m[2][c], m[2][c-w[1]]+v[1]);
}   //物品1可能装入，则不装物品1和装1比价值


~~~

求最优解：根据得到的dp表（二维矩阵，各个维度分别代表容量c  与 可选物品 n）可以回溯最优解

~~~c++
void Trackback(Type **m, int *w, int c, int n, int *x)
{  
   for (int i=1; i<n; i++)
         if (m[i][c]==m[i+1][c]) x[i]=0;   //物品i未装入
         else  { x[i]=1; c-=w[i]; }         //物品i被装入背包
    x[n] = (m[n][c]>0)? 1 : 0;
}

~~~

#### 背包问题分支

![416.分割等和子集1](C:\Users\sushi\Desktop\Typora\DP_Algo.assets\1611624003-KWJYuV-file_1611624003321)

### 剪绳子 i  与 剪绳子  ii(剑指 14)

### 动态规划做法

​	 只适合 剪绳子i 

~~~java
class Solution {
    public int cuttingRope(int n) {
        int[] dp = new int[n];
        int p =  1000000007;
        if(n == 2) return 1;
        if(n == 3) return 2;
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 3;
        int max=0;
        for(int i = 4; i < n-1;i++){
            int tem = 0;
            for(int j = 1; j <= i/2 + 1;j++){
                if(tem <  (dp[j] % p) * (dp[i - j] % p)  ) tem = (dp[j] % p) * (dp[i - j] % p );
            }
            dp[i] = tem;

        }
        for(int i = 1;i <= n/2 +1; i++){
            if(max < (dp[i]%p) * (dp[n - i]%p) ) max = (dp[i]%p) * (dp[n - i]%p) ;
        }
        return max;
    }
}
~~~



### 贪心法

​	RES 要用 long  因为计算 两数之积时 （1000000007）* （1 000000007） 超过 int 范围，

​	最优：剪出一段长度为 3 的

​	次优：剪一段 长度为 2 的

​	最差 ：剩下一段 长度 为 1 的， 与之前剪的 一段 3  拼接起来  变为  2 + 2 的

​				因为 1 * 3 < 2 * 2 

~~~JAVA
class Solution {
    
    //自己写得版本 注意 return 处的注释！！！！
    public int cuttingRope(int n) {
        long res =1;
        int p = 1000000007;
        if(n == 2) return 1;
        if(n == 3) return 2;
        if(n == 4) return 4;
        while(n > 4){
            res = res * 3 % p;
            n-=3;
        }
        if(n == 4) return (int)(res*4%p) ; //() 强制类型转换 运算符 优先级很高 ，因此要整体加括号 ，不能写成(int) res *4 %p！！

        if(n == 3) return (int)(res*3%p) ;

        return (int)(res*2%p) ;
        
    }
    
    //别人的版本，比较整洁
    public int cuttingRope(int n) {
        if(n <= 3) return n - 1;
        long res=1L;
        int p=(int)1e9+7;
        //贪心算法，优先切三，其次切二
        while(n>4){
            res=res*3%p;
            n-=3;
        }
        //出来循环只有三种情况，分别是n=2、3、4
        return (int)(res*n%p);
    }
}
~~~

编辑距离(lc 72)

首先，对x[1..i]的操作有一种标记法是这样的，把x[1..i]看作一排装在盒子里的字符：

1. 插入：[*] -> [a] 插入一个新的装有字符a的盒子
2. 删除：[a] -> [*] 将一个盒子里的字符a取出
3. 替换：[a ]-> [b] 盒子里的字符a换成b

horse -> ros 可以表示为

```racket
[h]  [o]  [r]  [s]  [e]
[r]  [o]  [*]  [s]  [*]
```

intention -> execution 可以表示为

```racket
[i]  [n]  [t]  [e]  [n]  [*]  [t]  [i]  [o]  [n]
[e]  [x]  [*]  [e]  [c]  [u]  [t]  [i]  [o]  [n]
```

使用这种标记方式可以直观地看出 **操作过程和顺序无关**，不论先修改哪个盒子，都不影响所需的步骤。

回到问题，x[1..i] -> y[1..j] （x[i] != y[j]）的操作只能是3种情况，才能使字符串末尾由x[i]变为y[j]：

1. 最后一个有字符的盒子在 x[i] 之前，那么 x[i] 肯定被删除，操作顺序不重要，可以等价为第一步就删除，那么剩下的操作就是x[1..i-1]->y[1..j]
2. 最后一个有字符的盒子就是 x[i]，那么肯定要将 x[i] 替换为 y[j]，操作顺序不重要，可以等价为第一步就替换，那么剩下的操作就是x[1..i-1]->y[1..j-1]
3. 最后一个有字符的盒子在 x[i] 之后，那么肯定在后面插入了新的装了 y[j] 的盒子，操作顺序不重要，可以等价为第一步就先在末尾插入y[j]，那么剩下的操作就是x[1..i]->y[1..j-1]

因为只有这3种情况，所以取其中操作步骤最小的。