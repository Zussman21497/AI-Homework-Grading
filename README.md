# 产品需求文档 (PRD)：AI 智改协同模块 Demo

**版本：** V1.0 (MVP)  
**目标：** 验证“AI 初改 + 教师红绿灯复核”模式在缩短批改周期、提升学生互动率上的可行性。

---

## 一、 用户场景与痛点回溯

**教师：** 批改量大、重复性高；对 AI 结果不信任（怕判错影响名誉）。

**学生：** 反馈滞后；看不懂纯文字解析；缺乏纠错动力。

---

## 二、 核心功能设计

### 1. 教师端：极简批改工作台

**核心逻辑：** AI 负责 80% 的体力活，老师负责 20% 的决策权。

#### 作业导入看板：

- 支持批量上传图片（模拟拍照连拍）。
- 自动切题预览： 系统展示切割后的题块，并标明题型（客观/主观）。

#### 红绿灯复核流（核心交互）：

- **绿色（高置信）：** AI 判定准确率 > 90% 的题目，默认收起。老师可“一键通过”。
- **黄色/红色（低置信）：** AI 判定模糊或可能错误的题目，自动高亮展开。

#### 极速纠偏：

- 老师点击题目上的 √ 或 × 即可修改 AI 结果。修改后，系统自动同步学生端分数。

---

### 2. 学生端：交互式反馈中心

**核心逻辑：** 延迟满足感，将“给答案”变成“做挑战”。

#### 秒级报告：

- 提交后立即显示得分情况及错题分布。

#### 分步引导解析（Hint 机制）：

- 针对错题，初始隐藏完整答案。
- 提供“求助 AI”按钮，点击后分段弹出：
    - 思路点拨
    - 关键公式
    - 完整步骤

#### 订正勋章墙（奖励机制）：

- **自主订正奖励：** 在不看最终答案的情况下订正成功，获得“智慧星”。
- **举一反三奖励：** 完成系统推荐的 1 道同类变式题，获得“掌握勋章”。

---


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

## 成果展示

<img width="1896" height="587" alt="b53723de-b786-4112-a8a8-b1182899b881" src="https://github.com/user-attachments/assets/033795d9-5e84-41f6-8925-863bd1055ad2" />
<img width="687" height="445" alt="d68eb56a-ef63-48d4-b3eb-3548afe75800" src="https://github.com/user-attachments/assets/274c37a9-f93e-432c-9920-f94658113c61" />
<img width="769" height="551" alt="d631f2aa-f0ac-439e-a722-6747cee1158c" src="https://github.com/user-attachments/assets/376c008d-d841-4b03-bb82-1cb8a1550657" />
<img width="1889" height="574" alt="d91e626d-34c6-4e07-9fd6-61a03c2ce3f9" src="https://github.com/user-attachments/assets/f326d06f-bbec-42fc-83ff-9b18b185a620" />
<img width="617" height="694" alt="a19b5651-4e3b-4f2e-9352-7b03cfd1360b" src="https://github.com/user-attachments/assets/2ce59b4d-42db-4dd7-82d2-60d793f21568" />



