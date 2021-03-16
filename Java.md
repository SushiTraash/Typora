# Java

# 基本类操作

## String 

### 分割字符串

~~~java
 String str = "www-runoob-com";
 String[] temp;
 String delimeter = "-";  // 指定分割字符
temp = str.split(delimeter); // 分割字符串

for(int i =0; i < temp.length ; i++){
    System.out.println(temp[i]);
    System.out.println("");
}

System.out.println("------java for each循环输出的方法-----");
String str1 = "www.runoob.com";
String[] temp1;
String delimeter1 = "\\.";  // 指定分割字符， . 号需要转义
temp1 = str1.split(delimeter1); // 分割字符串
for(String x :  temp1){
    System.out.println(x);
    System.out.println("");
}


~~~

### 字符串  StringBuilder 

字符串 操作经常使用 StringBuilder 进行，StringBuilder 速度更快

## String 与 List

string 的操作 繁琐，且花费较多时间，尽量转化为 LinkedList(或其他结构)

# 类之间的关系

依赖、关联、聚合、组合与泛化代表类与类之间的耦合度依次递增(https://blog.csdn.net/Jxianxu/article/details/83505901)

## 依赖 use-a

~~~java
//依赖在代码中主要体现为类A的某个成员函数的返回值、形参、局部变量或静态方法的调用，则表示类A引用了类B
//1. 依赖关系不会增加属性，Student 并没有增加Computer 属性
//2. 依赖关系在对应方法调用时开始，在方法调用完成后结束（以为被依赖的类只存在与方法中）
class Computer
{
public:
	static void start(){
		cout<<"电脑正在启动"<<endl;
	} 
};
class Student
{
public:
	//返回值构成依赖
	Computer& program();
	//形参构成依赖
	void program(Computer&);
	void playGame()
	{
		//局部变量构成依赖
		Computer* computer=new Computer;
		...
		//静态方法调用构成依赖
		Computer::star();
	}
};
~~~

## 关联 has-a

表现为 成员变量  <u>聚合和组合是细化的关联关系</u>

~~~java
class Teacher;
class Student{
public:
	Teacher teacher;  //成员变量
	void study();
}
~~~

## 聚合  个体可以离开总体 存活

<u>聚合和组合是细化的关联关系</u>

~~~java

public  class GooseGroup
{
    public Goose goose;

    public GooseGroup(Goose goose)
    {
        this.goose = goose;
    }
}
~~~

## 组合 个体和总体 同生共死

~~~java

public class Goose
{
    public Wings wings;

    public Goose()
    {
        wings=new Wings();
    }
}
~~~

聚合和组合的区别在于：

1. 构造函数，构造聚合关系需要传入类参数（goose），则个体（goose ）在总体销毁之后仍然可以存在，组合则不用传入类参数，个体和总体同生共死
2. 创建聚合时，客户端可以同时看见个体和总体，而组合则屏蔽了个体（Wings）的存在。

## 继承 is-a



~~~java
public class　Animal {
}

public class Tiger extends Animal {
}
~~~

# Lambda 表达式

lambda: （参数  ） -> { 表达式}；

Lambda 表达式 用于传递代码块,一般用于函数式接口（只有一个抽象方法）

~~~java
//方式 1
( String first, String second) -> {
    return first;
} 
//方式 2 参数类型可以省略
(first,second) ->{
    return first;
}
//没有参数也要 括号
()-{
return 0;
}
//方式 3
() -> 0;


// 1. 不需要参数,返回值为 5  
() -> 5  
  
// 2. 接收一个参数(数字类型),返回其2倍的值  
x -> 2 * x  
  
// 3. 接受2个参数(数字),并返回他们的差值  
(x, y) -> x – y  
  
// 4. 接收2个int型整数,返回他们的和  
(int x, int y) -> x + y  
  
// 5. 接受一个 string 对象,并在控制台打印,不返回任何值(看起来像是返回void)  
(String s) -> System.out.print(s)

~~~



# Exception 与 Error

继承关系

![file](Java.assets/24edf4da83ad889bdff0f2745f045518bd772331.jpg@1320w_710h.webp)

## Throwable

Throwable是Java语言中所有错误或异常的超类。下一层分为Error和Exception

## Error

Error是unchecked 不检查异常

Error类是指Java运行时系统的内部错误和资源耗尽错误。应用程序不会抛出该类对象。如果出现了这样的错误，除了告知用户，剩下的就是尽力使程序安全的终止。

## Exception

“如果出现RuntimeException，一定是程序员的问题” 由于程序有错误导致的异常属于RuntimeException

## RuntimeException -- 不检查异常

主要包含：错误的类型转换，数组访问约界，访问null指针

NullPointerException - 空指针引用异常
ClassCastException - 类型强制转换异常。
IllegalArgumentException - 传递非法参数异常。
ArithmeticException - 算术运算异常
ArrayStoreException - 向数组中存放与声明类型不兼容对象异常
IndexOutOfBoundsException - 下标越界异常
NegativeArraySizeException - 创建一个大小为负数的数组错误异常
NumberFormatException - 数字格式异常
SecurityException - 安全异常
UnsupportedOperationException - 不支持的操作异常



## 其他检查异常

检查异常一定要处理 要么继续Throws 要么try catch

如IOException，SQLException等等，具体看文档

## 抛出异常

~~~java
String methodname () throws EOFException{
	//....
    throw new EOFException();
	return ...;

}

~~~



## 捕获异常

~~~java
try{
    code1;//;
    code2;
    code throw new Exception();//这句抛出异常，那么这句之后剩余的代码不执行 
    //...
}catch(ChildException1 e){
    //捕获这个异常 以及这个异常的子类
}catch(FatherException e2){
    
}
finally{
    +
}
~~~

如果没有子句能捕获对应类型的异常，则在执行完finally 语句后直接退出当前这个方法。

finally语句正常情况下一定会执行（即使在try 或者catch 里有return 语句），但是如果try语句中调用System.exit(0)；直接退出虚拟机，那么finally 语句不会执行。线程为守护线程时，如果主线程死亡了，finally也不会执行的（https://blog.csdn.net/qq_39135287/article/details/78455525）

## 断言

指用于开发和测试阶段

~~~java
assert x>0;
assert x>0:x;
~~~

