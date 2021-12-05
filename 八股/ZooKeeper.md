## ZooKeeper 可以实现哪些功能？

- 数据发布/订阅
- 负载均衡
- 命名服务
- 分布式协调/通知
- 集群管理
- Master 选举
- 分布式锁
- 分布式队列

## Nacos  Zookeeper  Eureka 特点？ 为什么选Nacos ?

 https://www.liaochuntao.cn/2019/06/01/java-web-41/

- Zookeeper 实现 cp.ZAB.难以水平扩展因为只有leader 结点可以写数据
- Eureka 实现 ap raft算法
- Nacos 实现ap（distro 协议） 和cp （raft 协议）可以灵活切换

## ZAB协议

https://dbaplus.cn/news-141-1875-1.html