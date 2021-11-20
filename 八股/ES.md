## ES全文检索原理

https://www.cxyxiaowu.com/17356.html

https://www.elastic.co/cn/blog/found-elasticsearch-from-the-bottom-up

![img](https://gitee.com/Cai_Programmer/pic-go/raw/master/8c515a78-7253-4ca8-a2b5-d9750d407cdd.jpg)

- 内容爬取，停顿词过滤，比如一些无用的像"的"，“了”之类的语气词/连接词
- 内容分词，提取关键词
- 根据关键词建立**倒排索引**
- 用户输入关键词进行搜索

## 倒排索引

![img](https://gitee.com/Cai_Programmer/pic-go/raw/master/64936e49-cb83-491a-91e2-8180738a6f5b.jpg)

## ES数据迁移

https://www.infoq.cn/article/1afyz3b6hnhprrg12833



## 能不能取代Mysql？

- - ES没有事务。采用乐观锁，做不到acid事务
  - Join 连表复杂

- 

- - 