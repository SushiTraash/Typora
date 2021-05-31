# Linux

### 云服务器使用：

1. 修改root密码 

   ~~~shell
   su
   passwd
   ~~~

   

2. 添加用户和密码

   ~~~shell
   useradd -m sushi
   passwd sushi
   ~~~

   

3. 允许远程连接

# Vim

有三种模式

<img src="Linux.assets/centos7_vi-mode.gif" alt="vi三種模式的相互關係" style="zoom:150%;" />

# 常用网络指令

## ifconfig

- ### 查看修改网络配置的参数

## route

- ### 路由表结构 可参考https://blog.csdn.net/u011857683/article/details/83795279

![image-20210528180041296](Linux.assets/image-20210528180041296.png)

- ### Destination 对应目的IP ；如果在同网段：对应目的MAC

  目的IP地址

- ### Gateway  对应目的Mac；如果在不同网段：对应目的MAC

  网关，指明下一跳路由器的IP地址。这个IP地址通过ARP协议获取对应路由器的MAC地址用于填进IP数据报的**目的MAC地址**。

  如果Destination 在本网段内则为0.0.0.0。

- ### Genmask 目的IP地址和掩码与运算 之后进行匹配，匹配Destination字段

  Destination 字段的网络掩码，Destination 是主机时需要设为 255.255.255.255，是默认路由时会设置为 0.0.0.0

- ### Flags

  标记

  ● U 该路由可以使用。

  ● H 该路由是到一个主机，也就是说，目的地址是一个完整的主机地址。如果没有设置该标志，说明该路由是到一个网络，而目的地址是一个网络地址：一个网络号，或者网络号与子网号的组合。

  ● G 该路由是到一个网关（路由器）。如果没有设置该标志，说明目的地 是直接相连的

- ### Metric  跳数

  路由距离，到达指定网络所需的中转数，是大型局域网和广域网设置所必需的。

- ### Ref 和 use 不重要

- ### Iface 真正转发出去的网线口

  网卡名字，例如 eth0。
