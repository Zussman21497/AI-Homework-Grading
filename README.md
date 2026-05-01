# AI Homework Grading

AI 辅助作业批改系统，Spring Boot 后端 + React 前端。

---

## 环境要求

| 工具 | 版本 |
|------|------|
| Java | 17+ |
| Maven | 3.8+ |
| Node.js | 18+ |
| npm | 9+ |

---

## 启动后端

```bash
# 在项目根目录执行
./mvnw spring-boot:run
```

Windows 下：

```bash
mvnw.cmd spring-boot:run
```

后端默认监听 `http://localhost:8080`。

H2 控制台（开发用）：`http://localhost:8080/h2-console`

---

## 启动前端

```bash
cd frontend
npm install   # 首次运行需要安装依赖
npm start
```

前端默认运行在 `http://localhost:3000`。

---

## 跨域（CORS）配置

后端已在 `src/main/java/org/example/aihomeworkgrading/config/CorsConfig.java` 中配置 CORS，
允许来自 `http://localhost:3000` 的请求访问所有 `/api/**` 接口。

如需修改允许的源或路径，编辑该文件中的 `addCorsMappings` 方法即可。

前端开发时也可在 `frontend/package.json` 中添加代理，作为备选方案：

```json
"proxy": "http://localhost:8080"
```

---

## 项目结构

```
AI-Homework-Grading/
├── src/                          # Spring Boot 后端
│   └── main/
│       ├── java/org/example/aihomeworkgrading/
│       │   ├── AiHomeworkGradingApplication.java
│       │   └── config/
│       │       └── CorsConfig.java
│       └── resources/
│           └── application.properties
├── frontend/                     # React 前端
│   ├── src/
│   └── package.json
└── pom.xml
```
