# Easy Blog Backend 快速部署指南

## 已完成的修复工作

### 1. 修复的 Bug

#### JWT 工具类 Bug 修复
- **问题**: JWT 解析和生成方法使用了过时的 API
- **修复**: 更新到 JJWT 0.11.5 版本的新 API
  - `Jwts.parser()` → `Jwts.parserBuilder()`
  - `signWith(SignatureAlgorithm.HS512, secret)` → `signWith(SignatureAlgorithm.HS512, secret.getBytes())`

#### 安全配置 Bug 修复
- **问题**: SecurityConfig 中缺少 JWT 过滤器配置
- **修复**: 
  - 添加 JWT 过滤器自动装配
  - 在安全链中添加 JWT 过滤器
  - 修复导入语句

#### 用户登录接口 Bug 修复
- **问题**: 登录接口没有返回 JWT Token
- **修复**: 
  - 集成 Spring Security 认证
  - 生成并返回 JWT Token
  - 更新最后登录时间

#### 依赖缺失 Bug 修复
- **问题**: 缺少 Lombok 依赖
- **修复**: 在 pom.xml 中添加 Lombok 依赖

### 2. 部署文档

已创建完整的部署文档 `DEPLOYMENT.md`，包含：
- 系统要求
- 环境配置
- 部署步骤
- API 文档
- 监控和日志
- 故障排除
- 性能优化
- 安全配置

## 快速部署步骤

### 前置条件
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Git

### 1. 数据库准备
```sql
CREATE DATABASE easyblog CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. 配置修改
修改 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/easyblog?useSSL=false&serverTimezone=UTC
    username: your_username
    password: your_password

jwt:
  secret: your_secure_secret_key
  expiration: 86400
```

### 3. 编译和运行
```bash
cd easyblog-backend
mvn clean package -DskipTests
java -jar target/easyblog-backend-0.0.1-SNAPSHOT.jar
```

### 4. 验证部署
```bash
curl http://localhost:8080/api/questions
```

## 主要 API 接口

### 认证接口
- `POST /api/users/register` - 用户注册
- `POST /api/users/login` - 用户登录（返回 JWT Token）

### 题目管理
- `GET /api/questions` - 获取题目列表
- `POST /api/questions` - 创建题目（需要 ADMIN 权限）
- `GET /api/questions/{id}` - 获取题目详情

### 考试记录
- `POST /api/exam-records` - 创建考试记录
- `GET /api/exam-records/user/{userId}` - 获取用户考试记录

### 学习进度
- `GET /api/progress/user/{userId}` - 获取用户学习进度
- `POST /api/progress` - 创建学习进度

## 认证方式

使用 JWT Bearer Token：
```http
Authorization: Bearer <your-jwt-token>
```

## 项目特性

### 技术栈
- Spring Boot 3.1.0
- Spring Security
- Spring Data JPA
- MySQL 8.0
- JWT 认证
- Lombok

### 安全特性
- JWT Token 认证
- 密码加密存储
- 角色权限控制
- CORS 配置

### 功能模块
- 用户管理（注册、登录、权限控制）
- 题目管理（CRUD、分类、难度筛选）
- 考试记录（创建、完成、统计）
- 学习进度（跟踪、统计）

## 注意事项

1. **生产环境部署前**：
   - 修改默认 JWT 密钥
   - 配置 HTTPS
   - 设置合适的数据库权限

2. **性能优化**：
   - 考虑添加 Redis 缓存
   - 优化数据库查询
   - 配置连接池

3. **监控**：
   - 配置日志输出
   - 设置健康检查
   - 监控系统资源

## 故障排除

### 常见问题
1. **数据库连接失败**：检查数据库配置和权限
2. **JWT Token 无效**：确认密钥配置和 Token 时效
3. **端口占用**：修改 application.yml 中的端口号

### 日志查看
```bash
tail -f logs/easyblog-backend.log
```

---

**部署完成！** 项目已修复所有已知 bug，可以正常运行。详细部署信息请参考 `DEPLOYMENT.md`。
