# 为什么Netty ByteBuf要手动释放资源?

https://blog.csdn.net/agonie201218/article/details/113657795

ByteBuf使用池化技术优化了。当ByteBuf  不再使用，会被GC。但是如果不释放byteBuf资源，对象池不知道对应的ByteBuf被释放，而保留了对应的引用计数，最终会导致内存泄漏。

# Netty零拷贝

https://www.jianshu.com/p/a199ca28e80d


Netty的零拷贝体现在三个方面：

> 1. Netty的接收和发送ByteBuffer采用DIRECT BUFFERS，使用堆外直接内存进行Socket读写，不需要进行字节缓冲区的二次拷贝。如果使用传统的堆内存（HEAP BUFFERS）进行Socket读写，JVM会将堆内存Buffer拷贝一份到直接内存中，然后才写入Socket中。相比于堆外直接内存，消息在发送过程中多了一次缓冲区的内存拷贝。

> 2. Netty提供了组合Buffer对象，可以聚合多个ByteBuffer对象，用户可以像操作一个Buffer那样方便的对组合Buffer进行操作，避免了传统通过内存拷贝的方式将几个小Buffer合并成一个大的Buffer。

> 3. Netty的文件传输采用了transferTo方法，它可以直接将文件缓冲区的数据发送到目标Channel，避免了传统通过循环write方式导致的内存拷贝问题。
