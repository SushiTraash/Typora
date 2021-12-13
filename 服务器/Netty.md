# Netty基础

## 核心组件

- Channel ：代表连接，对应socket

- 回调和Future：两者都可以在操作完成时通知相关方

  - 回调即回调函数。channelHandler 类似为了响应特定事件而执行的回调

  - ChannelFuture是netty自己实现的异步，可以注册多个ChannelFutureListener

  - 可以把 ChannelFutureListener 看作是回调的一个更加精细的版本。 事实上，回调和 Future 是相互补充的机制；它们相互结合，构成了 Netty 本身的关键构件块之一

    <img src="Untitled.assets/image-20211212211008957.png" alt="image-20211212211008957" style="zoom: 80%;" />

    上图的回调只处理新连接建立这一事件，而channelFutureListener可以注册到自定义的Future，因此更灵活精细

## 总结

1. 
2. 

# Netty 组件

## Channel ,EventLoop,ChannelFuture

- Channel ----Socket
- EventLoop ---- 控制流、多线程处理、并发
- ChannelFuture ---- 异步通知

## 组件关系

![image-20211212214044407](Untitled.assets/image-20211212214044407.png)

- 一个 EventLoopGroup 包含一个或者多个 EventLoop；
-  一个 EventLoop 在它的生命周期内只和一个 Thread 绑定； 
-  所有由 EventLoop 处理的 I/O 事件都将在它专有的 Thread 上被处理； 
- 一个 Channel 在它的生命周期内只注册于一个 EventLoop； 
- 一个 EventLoop 可能会被分配给一个或多个 Channel。

注意，在这种设计中，一个给定 Channel 的 I/O 操作都是由相同的 Thread 执行的，实际 上消除了对于同步的需要。

## ChannelFuture

ChannelFuture接口可以用过addListener注册channelFutureListener。

