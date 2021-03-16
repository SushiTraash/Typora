#  TREE

## 基本概念

### 完全二叉树

​	除了最后一层，所有层都满。并且最后一层叶子节点从最左边开始连续排列

​	![图片](https://mmbiz.qpic.cn/mmbiz_png/ciaqDnJprwv4w2BfmNFmwCIdqBMeNmtQNQ1tuYNLqgeqHWmLlNnVMuFFt0LhYzzXHZNJfG31QtI6hAcLq2brLEQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

### 满二叉树

​	有两种定义，不常用到，忽略

## 先序遍历



### 递归框架

​	

```java
class Solution {
    List list = new ArrayList<>();
    public List preorderTraversal(TreeNode root) {
    if(root == null){
        return list;
    }
        list.add(root.val);
        preorderTraversal(root.left);
        preorderTraversal(root.right);
        return list;
    }
}
```

### 迭代框架

​	

```Java
public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> arr = new ArrayList<>();
        if(root==null)
            return arr;
        TreeNode node = root;
        Stack<TreeNode> stack = new Stack<>();
        while(!stack.isEmpty() || node!=null){
            while(node!=null){
                arr.add(node.val);
                if(node.right!=null)
                    stack.push(node.right); 
                // add all the right nodes as they will be visited once all the left and root nodes are added.
                //按照先序遍历的顺序，先遍历完根节点和左子树才遍历右子树，用栈保存所有右子树
                node = node.left;
            }
            
            if(!stack.isEmpty()){
                node = stack.pop();
            }
        }
        return arr;
        
    }
```



~~~java
//迭代遍历二叉树通用模板
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = new TreeNode();
        if(root!=null){ 
            stack.push(root); 
        }
        while(!stack.isEmpty()){
            node = stack.pop();
            if(node.left!=null || node.right!=null) {
                TreeNode tem = new TreeNode();
                tem.val = node.val;//一个假节点，砍掉左右儿子的根节点，分别之后出栈判断是否放进list
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
                stack.push(tem);
            }else list.add(node.val);

        }
        return list;
    }
}
~~~



## 中序遍历

### 递归框架

~~~java
class Solution {
    List list = new ArrayList<>();
    public List inorderTraversal(TreeNode root) {
    if(root == null){
        return list;
    }
        inorderTraversal(root.left);
        list.add(root.val);
        inorderTraversal(root.right);
        return list;
    }
}
~~~



### 迭代框架

~~~java
//迭代遍历二叉树通用模板
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {        
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = new TreeNode();
        if(root!=null){
            stack.push(root);
        }

        while(!stack.isEmpty()  ){
            node = stack.pop();
            if(node.left!=null || node.right!=null) {
                TreeNode tem = new TreeNode();
                tem.val = node.val;
                if (node.right != null) stack.push(node.right);
                stack.push(tem);
                if (node.left != null) stack.push(node.left);
                
            }else list.add(node.val);

        }
        return list;
    }
}
~~~





## 后序遍历

### 递归框架

~~~java
class Solution {
    List list = new ArrayList<>();
    public List postorderTraversal(TreeNode root) {
    if(root == null){
        return list;
    }
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        list.add(root.val);
        return list;
    }
}
~~~

### 迭代框架

~~~java
   
//迭代遍历二叉树通用模板
	public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = new TreeNode();
        if(root!=null){
            stack.push(root);
        }

        while(!stack.isEmpty()  ){
            node = stack.pop();
            if(node.left!=null || node.right!=null) {
                TreeNode tem = new TreeNode();
                tem.val = node.val;
                stack.push(tem);
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
                
            }else list.add(node.val);

        }
        return list;
    }
~~~

## 通用模板改进

​	这个通用模板性能明显比很多题解差，最差情况下几乎每个结点都进入了两次栈，时间复杂度为O(2n)，是其他题解O(n)的两倍。因此考虑改进，减少节点重复进栈的次数。



### 先序遍历模板改进

​	先序遍历中的根节点全部都可以不再次进栈，时间复杂度可以和标准题解一样O(n),可是跑测试的结果还是和标准答案差1ms ，可能是因为算法健壮性不够好，标准答案的算法只有在这棵树所有节点只有右儿子时，取最差情况O(n) ，有n对 进出栈操作，最好情况可以做到没有元素 进出栈（所有节点只有左儿子）。

~~~java
//迭代遍历二叉树通用模板
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = new TreeNode();
        if(root!=null){ 
            stack.push(root); 
        }
        while(!stack.isEmpty()){
            node = stack.pop();
           
            //TreeNode tem = new TreeNode();根节点可以不放回去
            //tem.val = node.val;
            list.add(node.val);
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
            //stack.push(tem); 可以不再放进去了

        }
        return list;
    }
}
~~~



### 中序遍历模板改进

​	当根节点没有左儿子时，根节点不用放回去

~~~java
//迭代遍历二叉树通用模板
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {        
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = new TreeNode();
        if(root!=null){
            stack.push(root);
        }

        while(!stack.isEmpty()  ){
            node = stack.pop();
            if(node.left==null){//没有左儿子时直接，根节点不再进入栈
                list.add(node.val);
                if (node.right != null) stack.push(node.right);
            }else{
                TreeNode tem = new TreeNode();
                tem.val = node.val;
                if (node.right != null) stack.push(node.right);
                stack.push(tem);
                if (node.left != null) stack.push(node.left);
            }

        }
        return list;
    }
}
~~~



### 后序遍历模板改进

​	可以参考乐扣题解思路 https://leetcode-cn.com/problems/binary-tree-inorder-traversal/solution/die-dai-fa-by-jason-2/

​	思路： 每到一个节点 `A`，就应该立即访问它。 然后将左子树压入栈，再次遍历右子树。

​	遍历完整棵树后，结果序列逆序即可。

​	基于改进后的先序遍历模板可以得到根-右-左的序列，进行reverse 即可得到

~~~java
//迭代遍历二叉树通用模板
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
           List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = new TreeNode();
        if(root!=null){
            stack.push(root);
        }
        while(!stack.isEmpty()){
            node = stack.pop();

            //TreeNode tem = new TreeNode();根节点可以不放回去
            //tem.val = node.val;
            list.add(node.val);

            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);// 出栈顺序为 根-右-左 
            //stack.push(tem); 可以不再放进去了

        }
        Collections.reverse(list);//反转list
        return list;
    }
}
~~~

## 层次遍历

​	使用队列实现（基于DFS思想）

~~~java
    
public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if(root!=null) queue.offer(root);
        while(!queue.isEmpty()){
            List<Integer> list1=new ArrayList<>();
            int size = queue.size();
            for(int i = 0 ; i < size;i++){
                TreeNode node = new TreeNode();
                node=queue.poll();
                list1.add(node.val);

                if(node.left!=null) queue.offer(node.left);
                if(node.right!=null) queue.offer(node.right);
            }

            list.add(list1);

        }
        return list;
    }
~~~



## 题目

## 重点在于学会提取框架（模板），追求性能可以对思路某些步骤进行改进：

1. 基于框架改进（容易）
2. 根据题目具体的特征进行改进，如：抓特定规律，规律其实就是另外一种框架,只不过适用的范围比我们学习的框架小（难，而且敲出的代码细节记不住）

### 二叉树最大深度



~~~java
class Solution {
    public int maxDepth(TreeNode root) {
        if(root==null) return 0;
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }
}
~~~



### 对称二叉树

~~~java
class Solution {
    public boolean cross (TreeNode node1,TreeNode node2){
        if(node1 ==null && node2 == null) return true;
        else if(node1 !=null && node2 != null){
            if(node1.val==node2.val){
                return cross(node1.left,node2.right)&&cross(node1.right,node2.left);
            }else return false;
            
            
        }else return false;
             
    }
    public boolean isSymmetric(TreeNode root) {
        if(root==null) return true;
        return cross(root.left,root.right);
    }
}
~~~



### 路径总和

~~~java
class Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null ){
             return false;
        
        }
        //if(root!=null && targetSum < root.val ) return false;
        if(root.left==null && root.right==null ){
            if( targetSum - root.val == 0)
            return true;
            else return false;
        }else{
            return hasPathSum(root.left,targetSum-root.val)||hasPathSum(root.right,targetSum-root.val);
        }
        
        
    }
}
~~~

### [106. 从中序与后序遍历序列构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/)

~~~java
    public TreeNode getRoot(List<Integer> in,List<Integer> post){
        if(in.size()==1){
            return  new TreeNode (in.get(0));
        }else {
            int rootval = post.get(post.size()-1);
            int index = in.lastIndexOf(rootval);
            TreeNode root = new TreeNode(rootval);
            
          
          if(index==0){
            root.left = null;
          }else root.left = getRoot(in.subList(0,index),post.subList(0,index));
          if(index==in.size()-1){
            root.right = null;
          }else root.right = getRoot(in.subList(index+1,in.size()),post.subList(index,post.size()-1));
         
            return root;
        }
    }
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        List<Integer> in = Arrays.stream(inorder).boxed().collect(Collectors.toList());
        List<Integer> post = Arrays.stream(postorder).boxed().collect(Collectors.toList());
        return getRoot(in,post);
    }
~~~

可以使用HashMap 优化

### 填充每个节点的下一个右侧节点指针 ( i 和ii)

使用层次遍历框架即可



~~~java
    public Node connect(Node root) {

        Queue<Node> queue = new LinkedList<>();
        Node preNode = new Node();
        Node nextNode = new Node();
        if (root == null){
            return  null;
        }
        queue.offer(root);
        while(!queue.isEmpty()){
            int sz = queue.size();
            for(int i = 0; i<sz; i++){
                //set next
                
                preNode = queue.poll();
                if(i==sz-1){
                    preNode.next=null;
                }else{
                    nextNode = queue.peek();
                    preNode.next = nextNode;
                }
                //new node in queue
                if(preNode.left != null) queue.offer(preNode.left);
                if(preNode.right != null) queue.offer(preNode.right);
            }
            
        }
        return root;
    }
~~~



