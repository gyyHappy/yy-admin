# 项目说明

- yy-admin是一个前后端分离的Java后台权限管理系统项目
- 前端地址： https://github.com/gyyHappy/yy-admin-vue 

# 项目源码

|        |               后端源码               |                 前端源码                 |
| ------ | :----------------------------------: | :--------------------------------------: |
| github | https://github.com/gyyHappy/yy-admin | https://github.com/gyyHappy/yy-admin-vue |

# 项目特点

- 基于SpringBoot开发
- 前后端分离，使用JWT进行交互
- Shiro安全框架
- Restful接口
- Swagger接口文档
- Redis
- 统一异常处理，统一数据封装返回
- xss脚本过滤
- Mybatis-Plus
- MySQL8+
- IDEA.2019

# 项目结构

```
yy-admin
├─sql  项目SQL语句
│
├─common 公共模块
│  ├─annotation 自定义注解
│  ├─aspect 自定义注解切面
│  ├─constants 常量定义
│  ├─exception 异常处理
│  ├─serializer 自定义Redis序列化
│  ├─shiro shiro相关配置
│  ├─utils 工具类
│  └─XSS XSS过滤
│ 
├─config 配置信息
│ 
├─modules 功能模块
│  ├─sys 权限模块
│ 
├─YyAdminApplication 项目启动类
├─CodeGenerator Mybatis-Plus代码生成
│  
├──resources 
│  ├─mapper SQL对应的XML文件
│  └─static 静态资源
```
# 项目预览
账号/密码 ： admin/123456
![](https://github.com/gyyHappy/yy-admin/blob/master/src/main/resources/images/1.png?raw=true)
![](https://github.com/gyyHappy/yy-admin/blob/master/src/main/resources/images/2.png?raw=true)
