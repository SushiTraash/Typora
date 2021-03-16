# Android 

# 1.安卓四大组件

![img](C:\Users\sushi\Desktop\Typora\Android.assets\1187237-1b4c0cd31fd0193f.png)

四大组件分为activity、service、content provider、broadcast receiver 

## Context 理解

https://www.jianshu.com/p/94e0f9ab3f1d

Context是上下文抽象类，ContextImpl是具体功能实现类，ContextWrapper是代理类。

只有Activity，Service持有Context。Broadcast Receiver，Content Provider并不是Context的子类，他们所持有的Context都是其他地方传过去的，所以并不计入Context总数。

## Activity

应用程序中，一个Activity通常就是一个单独的屏幕，它上面可以显示一些控件也可以监听并处理用户的事件做出响应。Activity之间通过Intent进行通信。(关注Intent)

# 2.Intent

Intent 分为 显式 Intent 和 隐式Intent。Intent 作用：启动Activity、启动Service（为了确保应用的安全性，启动 `Service` 时，请始终使用显式 Intent，且不要为服务声明 Intent 过滤器。使用隐式 Intent 启动服务存在安全隐患，因为您无法确定哪些服务将响应 Intent，且用户无法看到哪些服务已启动。从 Android 5.0（API 级别 21）开始，如果使用隐式 Intent 调用 `bindService()`，系统会抛出异常。） 、传递Broadcast

## 显式Intent



~~~JAVA
Intent intent = new Intent(FirstActivity.this, SecondActivity.class) // (context 语境，目标活动)
~~~



## 隐式Intent

使用隐式Intent 需要用到 action ，category  和 data 等信息。在Manifest.xml 对应的活动下增加Intent-filter  ，filter 里面添加这个活动希望响应的 Action+Category+Data  注意Category 一定要有DEFAULT项

## 隐式Intent 匹配规则

Action:

要通过此过滤器，您在 Intent 中指定的操作必须与过滤器中列出的某一操作匹配

Category:

若要使 Intent 通过类别测试，则 Intent 中的每个类别均必须与过滤器中的类别匹配。反之则未必然，**Intent 过滤器声明的类别可以超出 Intent 中指定的数量**，且 Intent 仍会通过测试。

Data:

1. 仅当过滤器未指定任何 URI 或 MIME 类型时，不含 URI 和 MIME 类型的 Intent 才会通过测试。
2. 对于包含 URI 但不含 MIME 类型（既未显式声明，也无法通过 URI 推断得出）的 Intent，仅当其 URI 与过滤器的 URI 格式匹配、且过滤器同样未指定 MIME 类型时，才会通过测试。
3. 仅当过滤器列出相同的 MIME 类型且未指定 URI 格式时，包含 MIME 类型但不含 URI 的 Intent 才会通过测试。
4. 仅当 MIME 类型与过滤器中列出的类型匹配时，同时包含 URI 类型和 MIME 类型（通过显式声明，或可以通过 URI 推断得出）的 Intent 才会通过测试的 MIME 类型部分。如果 Intent 的 URI 与过滤器中的 URI 匹配，或者如果 Intent 具有 `content:` 或 `file:` URI 且过滤器未指定 URI，则 Intent 会通过测试的 URI 部分。换言之，如果过滤器*只是*列出 MIME 类型，则假定组件支持 `content:` 和 `file:` 数据。

如果 Intent 指定 URI 或 MIME 类型，则数据测试会在 `<intent-filter>` 中没有 `<data>` 元素时失败。

Data test 总结： 

对MIME"严格匹配"（Intent 中不能缺少，也不能类型不匹配）。对URI匹配的会宽松一点（Intent 可以有多余URI ，如果 Filter 里面没有指定URI，Intent 装入URI 也能通过测试。但是如果Filter 指定了 URI 则要严格匹配）



~~~XML
<intent-filter>
    <!-- Intent 过滤器既可以不声明任何 <action> 元素，也可以声明多个此类元素-->
    <!-- 要通过此过滤器，您在 Intent 中指定的操作必须与过滤器中列出的某一操作匹配。-->
    <action android:name="android.intent.action.EDIT" />
    <action android:name="android.intent.action.VIEW" />
    <!-- Intent 过滤器既可以不声明任何  <category> 元素，也可以声明多个此类元素-->
    <!--若要使 Intent 通过类别测试，则 Intent 中的每个类别均必须与过滤器中的类别匹配。反之则未必然，Intent 过滤器声明的类别可以超出 Intent 中指定的数量，且 Intent 仍会通过测试。-->
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    ...
    <!--Intent 过滤器既可以不声明任何 <data> 元素，也可以声明多个此类元素-->
	<data android:mimeType="video/mpeg" android:scheme="http" ... />
    <data android:mimeType="audio/mpeg" android:scheme="http" ... />

</intent-filter>
~~~



~~~JAVA
Intent intent = new Intent(Intent.ACTION_VIEW);//动作 Action
intent.addCategory("...");//类别 Category 
intent.addData("")
~~~

# 3.Activity 的生命周期

![img](C:\Users\sushi\Desktop\Typora\Android.assets\activity_lifecycle.png)

## 1. 完整生存期

The entire lifetime between onCreate() to  onDestroy  

## 2.可见生存期

between onStart() to onStop()

## 3.前台生存期

between onResume()  to onPause()

# 4.Activity 启动模式

有四种 Standard,singleTop,singleTask,singleInstance，在Manifest 中修改启动方式

A task is a collection of activities 任务是一组放在返回栈中的活动的集合

## 1.standard

活动的默认启动方式。活动直接放到栈顶，不检查返回栈中是否有相同活动，每次启动都创建该活动的一个新实例。

## 2.singleTop

只检查栈顶。

检查将要启动的活动是否与栈顶的活动相同（即用户当前看到的活动），若相同，直接使用这个实例。否则新建活动实例。

## 3.singleTask

检测整个任务（即整个返回栈）。

检测整个返回栈中是否有相同的活动，若有，则该活动之上的所有活动出栈，否则创建新实例

## 4.singleInstance

创建一个单独的返回栈来管理要启动的Activity。后台有很多个返回栈（任务）。不同启动这个SingleInstance Activity是会跳转到同一个返回栈。



# 5.布局 Layout

View Viewgroup Layout 继承关系

![img](C:\Users\sushi\Desktop\Typora\Android.assets\1306890-a329e94168459de2.png)

Viewgroup 是一种特殊的View 包含子View 和子ViewGroup 是可以放置控件和布局的容器。

## 1.线性布局LinearLayout(支持weight 属性)

默认保持组件之间的间隔以及组件之间的互相对齐。线性布局显示组件的方式有两种方式：垂直和水平，是通过orientation来设定的。线性布局往往需要嵌套使用

## 2.相对布局RelativeLayout

控件可以通过相对定位，依赖父布局或者其他控件的位置进行布局。

## 3.帧布局FramentLayout

类似LinearLayout 但是不支持weight 属性 按比例布局

## 4.百分比布局PercentLayout(weight)

有PercentFrameLayout以及PercentRelativeLayout 。相当于增加了weight 属性的Frame 和Reative 



# 6.View （自定义过程）

