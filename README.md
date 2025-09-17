# EasyBlog - 在线题库与考试系统

一个基于Vue.js和Spring Boot的现代化在线题库管理和考试系统，支持题目管理、在线练习、模拟考试等功能。

## 📋 项目概述

EasyBlog是一个功能完整的在线学习平台，包含以下核心功能：

- **题库管理**：支持题目的增删改查，分类管理，难度设置
- **在线练习**：用户可以按分类、难度进行题目练习
- **模拟考试**：支持自定义考试，记录考试成绩和答题情况
- **学习进度**：跟踪用户的学习进度和完成情况
- **用户管理**：完整的用户注册、登录、权限管理

## 🛠️ 技术栈

### 前端
- **Vue.js 3** - 渐进式JavaScript框架
- **Vue Router** - 官方路由管理器
- **Element Plus** - Vue 3 UI组件库
- **Axios** - HTTP客户端
- **Vite** - 前端构建工具

### 后端
- **Spring Boot 3.1.0** - Java应用框架
- **Spring Security** - 安全框架
- **Spring Data JPA** - 数据持久层
- **JWT** - 身份验证
- **SQLite** - 轻量级数据库

### 开发工具
- **Maven** - 项目构建和依赖管理
- **Java 17+** - 开发语言
- **VSCode** - 开发环境

## 📁 项目结构

```
vueblog/
├── easyblog-backend/                 # 后端项目
│   ├── src/main/java/
│   │   └── com/example/easyblogbackend/
│   │       ├── controller/          # 控制器层
│   │       ├── service/            # 服务层
│   │       ├── repository/        # 数据访问层
│   │       ├── entity/            # 实体类
│   │       ├── dto/               # 数据传输对象
│   │       ├── config/            # 配置类
│   │       ├── security/          # 安全相关
│   │       ├── utils/             # 工具类
│   │       └── exception/         # 异常处理
│   ├── src/main/resources/
│   │   └── application.yml         # 配置文件
│   └── pom.xml                     # Maven依赖
├── easyblog-front-admin/           # 前端项目
│   ├── src/
│   │   ├── components/            # Vue组件
│   │   ├── views/                 # 页面视图
│   │   ├── router/                # 路由配置
│   │   ├── utils/                 # 工具函数
│   │   └── assets/                # 静态资源
│   ├── public/                    # 公共资源
│   └── package.json               # 前端依赖
├── easyblog.db                    # SQLite数据库文件
├── README.md                      # 项目说明文档
└── apache-maven-3.8.6/           # Maven工具
```

## 🚀 快速开始

### 环境要求

- **Java 17+**
- **Node.js 16+**
- **Maven 3.6+**

### 安装步骤

#### 1. 克隆项目

```bash
git clone https://github.com/LJK-ikun/-vue-.git
cd vueblog
```

#### 2. 后端设置

```bash
cd easyblog-backend

# 编译后端项目
mvn clean compile

# 运行后端服务
mvn spring-boot:run
```

后端服务将在 `http://localhost:8080` 启动

#### 3. 前端设置

```bash
cd easyblog-front-admin

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端应用将在 `http://localhost:3000` 启动

### 数据库初始化

项目使用SQLite数据库，数据库文件会自动创建在项目根目录下的 `easyblog.db`。

## 📚 功能特性

### 用户管理
- 用户注册和登录
- JWT身份验证
- 角色权限管理（USER/ADMIN）

### 题库管理
- 题目CRUD操作
- 题目分类管理
- 难度等级设置
- 题目搜索和筛选
- 题目统计（浏览量、点赞数）

### 学习功能
- 按分类/难度练习
- 学习进度跟踪
- 答题历史记录
- 错题收集

### 考试系统
- 自定义考试
- 实时答题进度
- 考试成绩统计
- 答题详情分析

## 🔧 API文档

### 认证接口

#### 用户注册
```
POST /api/auth/register
Content-Type: application/json

{
  "username": "testuser",
  "email": "test@example.com",
  "password": "password123"
}
```

#### 用户登录
```
POST /api/auth/login
Content-Type: application/json

{
  "username": "testuser",
  "password": "password123"
}
```

### 题库接口

#### 获取题目列表
```
GET /api/questions?page=0&size=10&sortBy=createdAt&sortDir=desc
```

#### 按分类获取题目
```
GET /api/questions/category/{categoryName}
```

#### 按难度获取题目
```
GET /api/questions/difficulty/{difficulty}
```

#### 搜索题目
```
GET /api/questions/search?keyword=关键词
```

#### 创建题目（管理员）
```
POST /api/questions
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "题目标题",
  "content": "题目内容",
  "answer": "答案",
  "categoryName": "分类名称",
  "difficulty": "难度",
  "tags": ["标签1", "标签2"]
}
```

### 学习进度接口

#### 获取用户学习进度
```
GET /api/progress/user/{userId}
Authorization: Bearer <token>
```

#### 更新答题进度
```
POST /api/progress/user/{userId}/question/{questionId}/answer
Authorization: Bearer <token>
Content-Type: application/json

{
  "answer": "用户答案"
}
```

#### 标记题目完成
```
POST /api/progress/user/{userId}/question/{questionId}/done
Authorization: Bearer <token>
```

### 考试接口

#### 创建考试
```
POST /api/exam-records
Authorization: Bearer <token>
Content-Type: application/json

{
  "examName": "考试名称",
  "questionIds": [1, 2, 3, 4, 5]
}
```

#### 提交考试答案
```
POST /api/exam-records/{id}/complete
Authorization: Bearer <token>
Content-Type: application/x-www-form-urlencoded

correctCount=3&score=60&timeSpent=1800&userAnswers=...&correctAnswers=...
```

## 📊 数据库设计

### 用户表 (users)
| 字段 | 类型 | 描述 |
|------|------|------|
| id | Long | 主键 |
| username | String | 用户名 |
| email | String | 邮箱 |
| password | String | 密码 |
| role | String | 角色 |
| created_at | LocalDateTime | 创建时间 |

### 题目表 (questions)
| 字段 | 类型 | 描述 |
|------|------|------|
| id | Long | 主键 |
| title | String | 题目标题 |
| content | String | 题目内容 |
| answer | String | 答案 |
| category_name | String | 分类名称 |
| difficulty | String | 难度 |
| tags | String | 标签 |
| view_count | Long | 浏览量 |
| like_count | Long | 点赞数 |
| is_active | Boolean | 是否激活 |
| created_at | LocalDateTime | 创建时间 |

### 学习进度表 (progress)
| 字段 | 类型 | 描述 |
|------|------|------|
| id | Long | 主键 |
| user_id | Long | 用户ID |
| question_id | Long | 题目ID |
| answer | String | 用户答案 |
| is_done | Boolean | 是否完成 |
| attempts | Integer | 尝试次数 |
| time_spent | Long | 花费时间 |
| last_accessed_at | LocalDateTime | 最后访问时间 |

### 考试记录表 (exam_records)
| 字段 | 类型 | 描述 |
|------|------|------|
| id | Long | 主键 |
| user_id | Long | 用户ID |
| exam_name | String | 考试名称 |
| question_ids | String | 题目ID列表 |
| correct_count | Integer | 正确数量 |
| score | Double | 得分 |
| time_spent | Long | 花费时间 |
| user_answers | String | 用户答案 |
| correct_answers | String | 正确答案 |
| status | String | 状态 |

## 🔐 安全配置

- JWT令牌认证
- 密码加密存储
- 角色权限控制
- CORS跨域配置
- SQL注入防护

## 📝 开发指南

### 代码规范
- 遵循Java编码规范
- 使用Spring Boot最佳实践
- 前端遵循Vue.js风格指南

### 提交规范
```
feat: 新功能
fix: 修复bug
docs: 文档更新
style: 代码格式化
refactor: 重构
test: 测试相关
chore: 构建或辅助工具变动
```

### 环境变量
项目支持环境变量配置，主要配置项：

```yaml
# application.yml
spring:
  datasource:
    url: ${DB_URL:jdbc:sqlite:../easyblog.db}
    driver-class-name: org.sqlite.JDBC
  
  jpa:
    hibernate:
      ddl-auto: ${DB_SCHEMA:update}
    show-sql: ${SHOW_SQL:false}

jwt:
  secret: ${JWT_SECRET:easyblogSecretKey2023}
  expiration: ${JWT_EXPIRATION:86400}
```

## 🤝 贡献指南

1. Fork本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建Pull Request

## 📄 许可证

本项目采用MIT许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 🙏 致谢

感谢所有为这个项目做出贡献的开发者

## 📞 联系方式

如有问题或建议，请通过以下方式联系：

- 项目地址：[https://github.com/LJK-ikun/-vue-.git](https://github.com/LJK-ikun/-vue-.git)
- 提交Issue：[GitHub Issues](https://github.com/LJK-ikun/-vue-.git/issues)

---

**注意**：这是一个教育项目，仅供学习和研究使用。
