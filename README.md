# SQL Generation - 智能 SQL 生成器

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.x-green.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.x-orange.svg)

## 项目简介

SQL Generation 是一个强大的 SQL 和代码生成工具，旨在解决开发过程中反复编写 SQL 建表语句和造测试数据的痛点。本项目提供直观的可视化界面，支持多种数据导入方式，并能够智能生成各类代码和测试数据。

## 🚀 核心特性

### 1. 智能建表

- 可视化表单配置
- 智能字段推荐
- 多种快捷导入方式
  - 智能导入（AI 辅助）
  - SQL 语句导入
  - Excel 导入
  - 配置文件导入

### 2. 数据生成

- 多样化的数据生成规则：
  - 固定值生成
  - 智能随机生成
  - 正则表达式生成
  - 递增序列生成
  - 自定义词库生成

### 3. 代码生成

- SQL（建表、数据操作语句）
- Java 代码（Entity, Service, Controller 等）
- 前端代码（API 调用、表单等）
- JSON 示例数据

### 4. 资源共享

- 词库管理与共享
- 表结构模板共享
- 字段定义复用
- 用户贡献系统

## 🛠 技术架构

### 后端技术栈

- 核心框架：`Spring Boot 2.7.x`
- ORM 框架：`MyBatis Plus 3.5.x`
- 数据库：`MySQL 8.x`
- 模板引擎：`FreeMarker`
- SQL 解析：`Druid`
- 数据模拟：`datafaker`
- 工具库：
  - `Apache Commons Lang3`
  - `Hutool`
  - `Gson`
  - `Easy Excel`
- 接口文档：`Knife4j`

### 系统架构

![系统架构图](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666144811181-37d5bd7f-28fa-4b17-9147-ae7de8de1585-20221019132502647-20221019132511901.png)

核心模块：

1. Schema 构建器：统一转换各类输入为标准 Schema
2. 代码生成器：基于 Schema 生成多种格式代码
3. 数据生成器：支持多种数据生成策略
4. 共享服务：提供资源共享和复用能力

## 🚀 快速开始

### 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+

### 开发环境搭建

1. 克隆项目

```bash
git clone https://github.com/your-username/sql-generation.git
```

2. 初始化数据库

```bash
# 执行SQL脚本
source sql/create_table.sql
```

3. 配置数据库连接

```yaml
# 修改 application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_db
    username: your_username
    password: your_password
```

4. 启动项目

```bash
mvn spring-boot:run
```

### Docker 部署

```bash
# 构建镜像
docker build -t sql-generation .

# 运行容器
docker run -d -p 8080:8080 sql-generation
```

## 📈 未来规划

### 1. 代码生成增强

- 增加更多框架模板
  - Spring Cloud 微服务模板
  - React 组件模板
  - 更多主流框架支持

### 2. 数据库支持扩展

- 增加对更多数据库的支持
  - PostgreSQL
  - MongoDB
  - Oracle
- 添加数据库版本兼容层
  - 跨版本 SQL 语法支持
  - 数据类型自动转换

### 3. 智能化功能

- AI 辅助功能
  - 智能字段推荐
  - 智能索引建议
  - 智能表关联推荐
- 性能优化建议
  - SQL 性能分析
  - 索引优化建议

### 4. 协作功能

- 团队协作
  - 模板共享和版本控制
  - 团队权限管理
  - 操作日志和审计
- 在线协作
  - 实时多人编辑
  - 评论和讨论功能

### 5. 安全性增强

- 安全模块
  - 细粒度的权限控制
  - 数据脱敏功能
  - 操作审计日志
- API 安全
  - 接口限流
  - 防 SQL 注入增强

### 6. 可视化功能

- 表结构可视化
  - ER 图自动生成
  - 表关系图谱
- 数据分析可视化
  - 数据分布图表
  - 字段使用统计

### 7. 导入导出增强

- 支持更多格式
  - Markdown 文档导出
  - PDF 格式导出
  - Word 文档导出
- 批量操作
  - 批量表导入
  - 批量代码生成

### 8. 测试数据增强

- 智能测试数据
  - 基于业务规则的数据生成
  - 关联数据自动生成
- 测试场景
  - 测试用例自动生成
  - 接口测试数据生成

### 9. 监控和运维

- 系统监控
  - 性能监控
  - 接口调用统计
- 运维功能
  - 系统配置管理
  - 缓存管理

### 10. 文档功能

- 文档生成
  - 数据字典自动生成
  - API 文档自动更新
  - 数据库设计文档
- 版本管理
  - 文档版本控制
  - 变更历史记录

## 💡 代码质量改进计划

### 1. 测试覆盖

- 单元测试
  - 核心模块测试用例
  - 边界条件测试
  - 异常场景测试
- 集成测试
  - 端到端测试
  - 性能测试
  - 压力测试

### 2. 代码规范

- 静态代码检查
  - CheckStyle
  - PMD
  - FindBugs
- 统一编码规范
  - 命名规范
  - 注释规范
  - 异常处理规范

### 3. 性能优化

- 缓存优化
  - 多级缓存
  - 缓存预热
  - 缓存更新策略
- 并发处理
  - 线程池优化
  - 异步处理
  - 批量处理
- 大数据量处理
  - 分页处理
  - 流式处理
  - 数据压缩

## 📚 开发文档

### 核心模块说明

#### 1. Schema 构建器 (TableSchemaBuilder)

```java
// 示例：从SQL构建Schema
TableSchema schema = TableSchemaBuilder.buildFromSql(sqlString);
```

#### 2. 生成器模块

- SqlBuilder: SQL 代码生成
- JavaCodeBuilder: Java 代码生成
- FrontendCodeBuilder: 前端代码生成
- DataBuilder: 测试数据生成

## 🤝 参与贡献

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交改动 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 📄 开源协议

本项目采用 MIT 协议开源 - 详见 [LICENSE](LICENSE) 文件

## 🙏 致谢

- 词库数据：[funNLP](https://github.com/fighting41love/funNLP)
- 示例表结构：[YesAPI](https://open.yesapi.cn/list1.html)
