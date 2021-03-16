# DFS_BFS_ALGO

## DFS框架

### [剑指 Offer 13. 机器人的运动范围](https://leetcode-cn.com/problems/ji-qi-ren-de-yun-dong-fan-wei-lcof/)

~~~java
class Solution {
    int search[][];
    int ans = 0;
    int gk;
    int gm;
    int gn;
    public int movingCount(int m, int n, int k) {
        gk = k;
        gm =m;
        gn = n;
        search = new int[m][n];
        dfs(0,0);
        return ans;
    }
    public void dfs(int i,int j){
        if( getDigitSum(i) + getDigitSum(j) > gk) return;
        search[i][j] = 1;
        ans ++;
        //System.out.print("i j:"+i+"  "+j+" ");
        if( i+1 < gm && search[i+1][j] !=1)dfs(i+1,j);
        if(i-1 >= 0 && search[i-1][j] !=1  )dfs(i-1,j);
        if(j+1 < gn && search[i][j+1] !=1  )dfs(i, j+1);
        if(j-1 >= 0 && search[i][j-1] !=1  )dfs(i, j-1);
    }
    public int getDigitSum(int digit){
        int sum = 0;
        
        while(digit > 0){
            sum += digit%10;
            digit = digit/10;
        }
        return sum;
    }
}
~~~

### 提取框架

~~~JAVA
    public void dfs(int i,int j){
        if( getDigitSum(i) + getDigitSum(j) > gk) return;
        search[i][j] = 1;
        ans ++;
        //System.out.print("i j:"+i+"  "+j+" ");
        if( i+1 < gm && search[i+1][j] !=1)dfs(i+1,j);
        if(i-1 >= 0 && search[i-1][j] !=1  )dfs(i-1,j);
        if(j+1 < gn && search[i][j+1] !=1  )dfs(i, j+1);
        if(j-1 >= 0 && search[i][j-1] !=1  )dfs(i, j-1);
    }
~~~

