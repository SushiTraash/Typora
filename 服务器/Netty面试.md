# 为什么Netty ByteBuf要手动释放资源?

https://blog.csdn.net/agonie201218/article/details/113657795

ByteBuf使用池化技术优化了。当ByteBuf  不再使用，会被GC。但是如果不释放byteBuf资源，对象池不知道对应的ByteBuf被释放，而保留了对应的引用计数，最终会导致内存泄漏。
