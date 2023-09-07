# MyWebStack-backend
# WebStack-backend
一个开源的网址导航网站项目的后台项目。
## 配置环境
Mysql8 + Redis3.2.1

## 技术版本
SpringBoot 2.7.13 + MyBatisPlus 3.5.3.1 + SpringSecurity2.7.14 + java-jwt4.3.0

## 运行
克隆代码:  

导入IDE，建议用IDEA打开项目目录，待maven下载完jar包


编辑配置：

```
application.yml
```

```
Mysql连接，用户名密码：
url
username
password
```

```
Redis连接，配置host与port：
```
```

新建数据库mywebstack(utf8mb4)，导入script目录下的sql文件：
mywebstack.sql
```

IDE启动服务

## 更新日志
### 2023-09-07
#### 新增

* 完成后台项目当中的目录管理模块，包括添加和删除目录。
* 完成后台项目当中的管理Web模块，完善了修改和添加Web时针对目录的操作。
* 完成了logo图片的上传功能。

