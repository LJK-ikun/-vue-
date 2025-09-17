# Easy Blog Backend 部署文档

## 项目概述

Easy Blog Backend 是一个基于 Spring Boot 3.1.0 的后端服务，提供用户管理、题目管理、考试记录和学习进度等功能。

## 系统要求

- Java 17 或更高版本
- Maven 3.6.0 或更高版本
- MySQL 8.0 或更高版本
- Git

## 环境配置

### 1. 数据库配置

#### 创建数据库
```sql
CREATE DATABASE easyblog CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 创建用户（可选）
```sql
CREATE USER 'easyblog'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON easyblog.* TO 'easyblog'@'localhost';
FLUSH PRIVILEGES;
```

### 2. 配置文件修改

修改 `src/main/resources/application.yml` 文件中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/easyblog?useSSL=false&serverTimezone=UTC
    username: root  # 修改为你的数据库用户名
    password: password  # 修改为你的数据库密码
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 3. JWT配置

修改 `src/main/resources/application.yml` 文件中的JWT配置：

```yaml
jwt:
  secret: easyblogSecretKey2023  # 建议修改为更安全的密钥
  expiration: 86400  # Token有效期（秒），24小时
```

## 部署步骤

### 1. 克隆项目

```bash
git clone https://github.com/LJK-ikun/-vue-.git
cd vueblog/easyblog-backend
```

### 2. 编译项目

```bash
mvn clean compile
```

### 3. 运行测试（可选）

```bash
mvn test
```

### 4. 打包项目

```bash
mvn clean package -DskipTests
```

### 5. 运行应用

#### 方式一：使用 Maven 运行
```bash
mvn spring-boot:run
```

#### 方式二：运行 JAR 文件
```bash
java -jar target/easyblog-backend-0.0.1-SNAPSHOT.jar
```

#### 方式三：作为系统服务运行（Linux）

创建服务文件 `/etc/systemd/system/easyblog-backend.service`：

```ini
[Unit]
Description=Easy Blog Backend Service
After=syslog.target network.target

[Service]
Type=simple
User=your_username
WorkingDirectory=/path/to/easyblog-backend
ExecStart=/usr/bin/java -jar target/easyblog-backend-0.0.1-SNAPSHOT.jar
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

启动服务：
```bash
sudo systemctl daemon-reload
sudo systemctl enable easyblog-backend
sudo systemctl start easyblog-backend
```

### 6. 验证部署

应用启动后，访问以下地址验证服务是否正常运行：

```bash
curl http://localhost:8080/api/questions
```

如果返回JSON数据，说明部署成功。

## API 文档

### 基础信息

- **服务地址**: `http://localhost:8080`
- **API前缀**: `/api`
- **端口**: 8080

### 认证方式

使用 JWT Bearer Token 认证：

```http
Authorization: Bearer <your-jwt-token>
```

### 主要接口

#### 用户管理
- `POST /api/users/register` - 用户注册
- `POST /api/users/login` - 用户登录
- `GET /api/users/{id}` - 获取用户信息
- `PUT /api/users/{id}` - 更新用户信息
- `DELETE /api/users/{id}` - 删除用户

#### 题目管理
- `GET /api/questions` - 获取题目列表
- `POST /api/questions` - 创建题目
- `GET /api/questions/{id}` - 获取题目详情
- `PUT /api/questions/{id}` - 更新题目
- `DELETE /api/questions/{id}` - 删除题目

#### 考试记录
- `POST /api/exam-records` - 创建考试记录
- `GET /api/exam-records/{id}` - 获取考试记录
- `GET /api/exam-records/user/{userId}` - 获取用户考试记录
- `POST /api/exam-records/{id}/complete` - 完成考试

#### 学习进度
- `GET /api/progress/user/{userId}` - 获取用户学习进度
- `POST /api/progress` - 创建学习进度
- `PUT /api/progress/{id}` - 更新学习进度

## 监控和日志

### 日志配置

日志文件位置：`logs/easyblog-backend.log`

### 健康检查

Spring Boot Actuator 健康检查端点：
```bash
curl http://localhost:8080/actuator/health
```

## 故障排除

### 常见问题

1. **数据库连接失败**
   - 检查数据库服务是否启动
   - 验证数据库连接配置
   - 确认数据库用户权限

2. **端口占用**
   - 检查8080端口是否被占用
   - 修改 `application.yml` 中的端口号

3. **JWT Token 无效**
   - 检查 JWT 密钥配置
   - 验证 Token 是否过期

4. **内存不足**
   - 增加 JVM 堆内存大小：
     ```bash
     java -Xmx2g -jar target/easyblog-backend-0.0.1-SNAPSHOT.jar
     ```

### 日志分析

查看错误日志：
```bash
tail -f logs/easyblog-backend.log
```

## 性能优化

### 数据库优化

1. 添加适当的索引
2. 优化查询语句
3. 使用连接池

### 应用优化

1. 启用缓存
2. 优化JVM参数
3. 使用CDN加速静态资源

## 安全配置

### 生产环境安全建议

1. 修改默认的JWT密钥
2. 配置HTTPS
3. 启用CSRF保护
4. 限制API访问频率
5. 定期备份数据库

### 防火墙配置

```bash
# 开放8080端口
sudo ufw allow 8080
```

## 备份和恢复

### 数据库备份

```bash
mysqldump -u root -p easyblog > easyblog_backup.sql
```

### 数据库恢复

```bash
mysql -u root -p easyblog < easyblog_backup.sql
```

## 更新部署

### 版本更新

1. 备份当前版本
2. 拉取最新代码
3. 重新编译打包
4. 停止旧版本服务
5. 部署新版本
6. 启动服务

### 回滚操作

如果新版本出现问题，可以快速回滚到上一个版本：

```bash
# 恢复备份
git checkout <previous-version-tag>
mvn clean package
sudo systemctl stop easyblog-backend
sudo systemctl start easyblog-backend
```

## 联系方式

如有问题，请联系开发团队或提交Issue。

---

**注意**: 本文档基于项目当前版本编写，如有更新请参考最新文档。
