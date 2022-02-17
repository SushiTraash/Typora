https://yeasy.gitbook.io/docker_practice/image/build

# Docker基础

## 基本概念

- 镜像
- 容器
- 仓库

## 镜像

**Docker 镜像** 是一个特殊的文件系统，除了提供容器运行时所需的程序、库、资源、配置等文件外，还包含了一些为运行时准备的一些配置参数（如匿名卷、环境变量、用户等）。镜像 **不包含** 任何动态数据，其内容在构建之后也不会被改变。

镜像设计为分层存储的架构。镜像构建时，会一层层构建，前一层是后一层的基础。每一层构建完就不会再发生改变，后一层上的任何改变只发生在自己这一层。比如，删除前一层文件的操作，实际不是真的删除前一层的文件，而是仅在当前层标记为该文件已删除。在最终容器运行的时候，虽然不会看到这个文件，但是实际上该文件会一直跟随镜像。

## 容器

镜像和容器 的关系就像 类和实例。镜像是静态的定义，容器是镜像运行时的实体。容器可以被创建、启动、停止、删除、暂停等。

前面讲过镜像使用的是分层存储，容器也是如此。每一个容器运行时，是以镜像为基础层，在其上创建一个当前容器的存储层，我们可以称这个为容器运行时读写而准备的存储层为 **容器存储层**。

## 仓库

下载镜像的地方

# Docker进阶

## 保存当前容器为镜像：commit

docker commit 可以将当前容器对存储层的操作保存下来，基于原有镜像保存为新的镜像（分层构建）

Dockerfile 是一个文本文件，其内包含了一条条的 **指令(Instruction)**，每一条指令构建一层，因此每一条指令的内容，就是描述该层应当如何构建。

## Dockerfile

 Dockerfile是用于分层构建，定制镜像的脚本。

### Dockerfile指令

#### FROM 指定基础镜像

```dockerfile
FROM scratch ##空白镜像
```

#### RUN 执行命令

```dockerfile
RUN echo 'python3' >> ~/start_up.sh &&\  
## \ ：换行符
## && ：连接两条指令，前一条成功才可以进行下一条
 chmod +x ~/agent_start.sh  

```

示例

```dockerfile
FROM ubuntu:18.04

WORKDIR  /data/app/python
ENV PYTHON_VERSION 3.6.9
ENV AGENT_VERSION 1.0.15
ENV LANG C.UTF-8
RUN  apt-get update && \
     apt-get install -y wget && \
     apt update && \
     ##apt install -y git &&\
     apt install -y curl &&\
     apt install -y zip &&\
	wget https://pan.huya.com/s/yQBfW3YkjSAtiSZ/download -O ./agent.py &&\
	apt install -y python3-pip &&\
     pip3 install requests &&\ 
     pip3 install pluginbase &&\
     echo 'python3 /data/app/python/agent.py ${1} ${2} ${3} ${4} ${5}' >> ~/agent_start.sh && \
     chmod +x ~/agent_start.sh  

##python: zipfile 和 urllib 和time 和hashlib 和logging 自带

```

### 制作镜像

#### build 根据Dockerfile 构建镜像

```shell
docker build -t testpython:v2 .
##在Dockerfile 的同级目录运行。  ”.“ 代表当前目录
##-t  tag 镜像的标签
```

## 镜像仓库和tag的关系

![image-20211216180859003](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20211216180859003.png)

- REPOSITORY仓库就是远程地址，用于存放镜像
- TAG就是有点类似ID用于寻找镜像

## push本地镜像

```shell
##指定远程仓库
docker login --username=名字 -p= 密码 远程仓库ip:端口
##根据tag指定本地镜像 推送
docker push NAME:TAG
```

## 修改本地镜像tag

```shell
docker tag IMAGEID(镜像id) REPOSITORY:TAG（仓库：标签）

```

# Docker

## Docker安装

- 官网找到一下目录，按教程安装

![image-20210612124654630](C:\Users\sushi\AppData\Roaming\Typora\typora-user-images\image-20210612124654630.png)

## Docker配置

- 配置mysql端口映射和文件挂载

```shell
docker run -p 3306:3306 --name mysql  -v /mydata/mysql/log:/var/log/mysql  -v /mydata/mysql/data:/var/lib/mysql  -v /mydata/mysql/conf:/etc/mysql  -e MYSQL_ROOT_PASSWORD=root  -d mysql:5.7
```

- 配置redis时，如果Linux本身就有redis要记得停止redis服务

  ```shell
  #下载redis
  docker pull redis
  #配置文件
  mkdir -p /mydata/redis/conf
  touch /mydata/redis/conf/redis.conf
  #启动
  docker run -p 6379:6379 --name redis \
  -v /mydata/redis/data:/data \
  -v /mydata/redis/conf/redis.conf:/etc/redis/redis.conf \
  -d redis redis-server /etc/redis/redis.conf
  #查看容器
  docker ps
  #查看所有容器（包括停止了的）
  docker ps -a
  #使用redis-cli模式 进入redis
  docker exec -it redis redis-cli#最后输入的redis-cli表明进入是使用的命令，redis-cli命令是用于进入cli模式的

  ```

## Docker 使用 Mysql 、redis

![image-20210712202747576](谷粒商城.assets/image-20210712202747576.png)

```
# docker 中下载 mysql
docker pull mysql

#启动
docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=Lzslov123! -d mysql

#进入容器
docker exec -it mysql bash

#登录mysql
mysql -u root -p
ALTER USER 'root'@'localhost' IDENTIFIED BY 'Lzslov123!';

#添加远程登录用户
CREATE USER 'liaozesong'@'%' IDENTIFIED WITH mysql_native_password BY 'Lzslov123!';
GRANT ALL PRIVILEGES ON *.* TO 'liaozesong'@'%';
```


# Docker 坑

## 中文乱码问题

dockerfile 设置ENV LANG C.UTF-8 即可
