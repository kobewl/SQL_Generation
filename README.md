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
