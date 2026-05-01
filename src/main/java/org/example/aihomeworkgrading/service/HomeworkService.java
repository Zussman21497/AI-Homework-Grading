package org.example.aihomeworkgrading.service;

import org.example.aihomeworkgrading.model.HomeworkRecord;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.LinkedHashMap;

/**
 * 作业数据存储与业务逻辑（内存 Map，无需数据库）
 */
@Service
public class HomeworkService {

    private final Map<String, HomeworkRecord> store = new ConcurrentHashMap<>();
    private final GradingService gradingService;

    public HomeworkService(GradingService gradingService) {
        this.gradingService = gradingService;
        initMockData();
    }

    // ------------------------------------------------------------------ //
    //  接口方法
    // ------------------------------------------------------------------ //

    /** POST /api/homework/upload — 接收题目列表，执行 Mock AI 批改后存储并返回 */
    public List<HomeworkRecord> upload(List<HomeworkRecord> records) {
        gradingService.mockGradeAll(records);
        records.forEach(r -> store.put(r.getQuestionId(), r));
        return records;
    }

    /**
     * POST /api/homework/upload-photo — 接收图片文件，模拟 OCR 切题并返回 Mock 题目
     * @param studentName 学生姓名（从 multipart 参数传入）
     * @return 模拟切割出的 4 道新题
     */
    public List<HomeworkRecord> uploadPhoto(String studentName) {
        // 用时间戳生成唯一前缀，避免和已有题目 ID 冲突
        String prefix = studentName.replaceAll("\\s+", "") + "_" + System.currentTimeMillis() % 10000;

        List<HomeworkRecord> newRecords = new ArrayList<>(List.of(
            build(prefix + "_q1", "subjective",
                studentName + " 的作答：设圆的半径为 r=5，求面积。答：S=78.5",
                List.of("第一步：圆面积公式 S=πr²",
                        "第二步：代入 r=5，S=3.14×25",
                        "第三步：S≈78.5")),

            build(prefix + "_q2", "objective",
                studentName + " 的作答：一元二次方程 x²-5x+6=0 的根是？答：x=2 或 x=3",
                List.of("因式分解：(x-2)(x-3)=0",
                        "解得 x=2 或 x=3")),

            build(prefix + "_q3", "subjective",
                studentName + " 的作答：已知等差数列首项为 2，公差为 3，求第 5 项。答：14",
                List.of("第一步：通项公式 aₙ=a₁+(n-1)d",
                        "第二步：a₅=2+(5-1)×3",
                        "第三步：a₅=2+12=14")),

            build(prefix + "_q4", "objective",
                studentName + " 的作答：log₂(8)=?  答：3",
                List.of("2³=8，故 log₂(8)=3"))
        ));

        return upload(newRecords);
    }

    /** GET /api/homework/review-list — 返回所有 pending_review 的题目 */
    public List<HomeworkRecord> getReviewList() {
        return store.values().stream()
                .filter(r -> "pending_review".equals(r.getStatus()))
                .sorted(Comparator.comparing(HomeworkRecord::getQuestionId))
                .toList();
    }

    /** GET /api/homework/all — 返回全部题目（前端进度条用） */
    public List<HomeworkRecord> getAll() {
        return store.values().stream()
                .sorted(Comparator.comparing(HomeworkRecord::getQuestionId))
                .toList();
    }

    /**
     * PUT /api/homework/correct — 教师纠偏
     * @param questionId 题目 ID
     * @param approved   true = 教师确认正确(√)，false = 教师标记错误(×)
     */
    public HomeworkRecord correct(String questionId, boolean approved) {
        HomeworkRecord record = store.get(questionId);
        if (record == null) {
            throw new NoSuchElementException("题目不存在: " + questionId);
        }
        record.setStatus("corrected");
        // 教师标记错误时将分数清零，标记正确时保留 AI 分数
        if (!approved) {
            record.setAiScore(0);
        }
        return record;
    }

    /**
     * GET /api/homework/variant/{questionId} — 返回同类变式题
     * Demo 中按题目 ID 映射一道固定变式题
     */
    public HomeworkRecord getVariant(String questionId) {
        // 变式题库：每道原题对应一道同类变式
        Map<String, HomeworkRecord> variants = new LinkedHashMap<>();

        variants.put("math_001", buildVariant("var_math_001", "subjective",
            "已知直角三角形两直角边分别为 6 和 8，求斜边长。",
            "答案关键词：10",
            List.of("第一步：套用勾股定理 c²=a²+b²",
                    "第二步：c²=36+64=100",
                    "第三步：c=10")));

        variants.put("math_003", buildVariant("var_math_003", "subjective",
            "求 g(x)=x²-4x+4 的最小值及取得最小值时的 x。",
            "答案关键词：0，x=2",
            List.of("第一步：配方 g(x)=(x-2)²",
                    "第二步：最小值在 x=2 时取得",
                    "第三步：g(2)=0")));

        variants.put("math_005", buildVariant("var_math_005", "subjective",
            "用反证法证明：不存在最大的自然数。",
            "答案关键词：反证，矛盾",
            List.of("第一步：假设存在最大自然数 N",
                    "第二步：则 N+1 也是自然数且 N+1>N，矛盾",
                    "第三步：故不存在最大自然数")));

        // 客观题变式
        variants.put("math_002", buildVariant("var_math_002", "objective",
            "3+5=?", "答案关键词：8",
            List.of("直接计算：3+5=8")));

        variants.put("math_004", buildVariant("var_math_004", "objective",
            "cos(0°)=?", "答案关键词：1",
            List.of("单位圆定义：cos(0°)=1")));

        variants.put("math_006", buildVariant("var_math_006", "objective",
            "6×8=?", "答案关键词：48",
            List.of("直接计算：6×8=48")));

        // 找不到对应变式时返回通用题
        return variants.getOrDefault(questionId, buildVariant(
            "var_default", "objective",
            "1+1=?", "答案关键词：2",
            List.of("直接计算：1+1=2")));
    }

    private HomeworkRecord buildVariant(String id, String type, String question,
                                        String hint, List<String> steps) {
        HomeworkRecord r = new HomeworkRecord();
        r.setQuestionId(id);
        r.setType(type);
        r.setStudentAnswer(question);   // 复用 studentAnswer 字段存题目内容
        r.setAnalysisSteps(steps);
        r.setAiScore(0);
        r.setConfidence(1.0);
        r.setStatus("variant");
        return r;
    }

    private void initMockData() {
        List<HomeworkRecord> seeds = new ArrayList<>();

        seeds.add(build("math_001", "subjective", "设 a=3, b=4，求斜边长。答：c=5",
                List.of("第一步：识别直角三角形", "第二步：代入勾股定理 a²+b²=c²", "第三步：c=√(9+16)=5")));

        seeds.add(build("math_002", "objective", "2+2=?  答：4",
                List.of("直接计算即可")));

        seeds.add(build("math_003", "subjective", "求 f(x)=x²+2x+1 的最小值。答：0",
                List.of("第一步：配方 f(x)=(x+1)²", "第二步：最小值在 x=-1 时取得", "第三步：f(-1)=0")));

        seeds.add(build("math_004", "objective", "sin(90°)=?  答：1",
                List.of("单位圆定义：sin(90°)=1")));

        seeds.add(build("math_005", "subjective", "证明√2 是无理数。答：反证法……",
                List.of("第一步：假设√2=p/q（最简分数）", "第二步：推出 p,q 均为偶数，矛盾", "第三步：故√2 是无理数")));

        seeds.add(build("math_006", "objective", "3×7=?  答：21",
                List.of("直接计算")));

        upload(seeds);
    }

    private HomeworkRecord build(String id, String type, String answer, List<String> steps) {
        HomeworkRecord r = new HomeworkRecord();
        r.setQuestionId(id);
        r.setType(type);
        r.setStudentAnswer(answer);
        r.setAnalysisSteps(steps);
        return r;
    }
}
