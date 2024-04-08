# 高并发商品限时秒杀系统

后端技术栈：

- 😄SpringBoot + MyBatis：基本框架
- :books:MySQL：用户，商品和订单信息的持久化存储
- ❤Redis：减少数据库的访问量，提高运行效率
- :walking:RabbitMQ：实现用户的异步秒杀下单

## 数据库设计

![image](https://github.com/shendefeng/Picture/main/img/A5B94DA2096F6F7125447963E76073AD.png)

符合数据库三大范式：字段原子性、非主键列完全依赖主键列

## 用户登录模块

### 密码功能

#### 两次MD5加盐加密

用户输入到后端，以及从后端到数据库实现两次的加密

好处：

1. 第一次作用：防止用户明文密码在网络进行传输
2. 第二次作用：防止数据库被盗，避免通过MD5反推出密码，双重保险

### 登录验证

使用JSR303自定义校验器，实现对用户账号、密码的验证，使得验证逻辑从业务代码中脱离出来。

### 用户状态存储

技术栈：Redis❤ + Session + ThreadLocal + 拦截器

解决HTTP无状态问题和实现拦截器功能。

## 秒杀模块

👾👾👾特点：多表操作+高并发+多事务+大流量⭐⭐⭐、

### 实现步骤

系统初始化时，将秒杀商品库存加载到Redis中

收到请求，在Redis中预减库存，库存不足时，直接返回秒杀失败。

> - 使用Redis是为了减少数据库访问量，但是为了进一步减少Redis的访问量使用内存标记(缓存实现)。

秒杀成功，将订单压入消息队列，返回前端消息"排队中"，实现异步处理。

> - 通过消息队列进行流量削峰，平缓地处理一个个请求。

判断是否满足秒杀功能，消息出队，生成订单，减少库存

客户端在以上过程执行过程中，将一直轮询是否秒杀成功

### 关键问题

⭐超卖：数据库的悲观锁 + 消息队列

⭐一人一单：数据库唯一索引 + Redis预减 + mysql 和 redis 数据一致性

## 安全模块

`seckill/doSeckill`接口是高流量的，需要对其流量限制。

### 验证码

通过`EasyCaptcha`生成图形验证码存入Redis中，必须通过验证码验证才能动态返回地址。

### 动态秒杀地址

实现`seckill/+{path}+/doSeckill`，将`path`存入Redis中。

### 接口防刷

使用计数器实现防刷功能。后面使用RateLimiter令牌桶算法优化。

## 接口文档

https://apifox.com/apidoc/shared-7d0cf69f-3a14-4b82-8832-6fba6181a964

## TODO

- [ ] 支付服务

- [ ] 分布式锁

- [ ] 分布式服务

## 参考资料
https://github.com/qiurunze123/miaosha/blob/master/old.md

https://github.com/qiurunze123/miaosha/blob/master/docs/code-solve.md

https://github.com/zaiyunduan123/springboot-seckill

https://github.com/Goinggoinggoing/seckill-study

https://github.com/Grootzz/seckill/tree/master

https://osjobs.net/system/posts/spike-system/

https://github.com/hfbin/Seckill


