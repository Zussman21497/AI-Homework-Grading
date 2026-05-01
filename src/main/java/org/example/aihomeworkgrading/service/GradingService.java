package org.example.aihomeworkgrading.service;

import org.example.aihomeworkgrading.model.HomeworkRecord;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * AI 批改引擎（Mock 实现）
 * 通过随机数模拟 AI 判分和置信度逻辑
 */
@Service
public class GradingService {

    private static final double CONFIDENCE_THRESHOLD = 0.8;
    private final Random random = new Random();

    /**
     * 对单道题目执行 Mock AI 批改：
     * - 随机生成 0.0~1.0 的置信度
     * - confidence < 0.8 → status = pending_review
     * - confidence >= 0.8 → status = approved
     */
    public HomeworkRecord mockGrade(HomeworkRecord record) {
        double confidence = random.nextDouble(); // [0.0, 1.0)
        int aiScore = random.nextInt(11);        // 0~10 分（可按需调整满分）

        record.setConfidence(Math.round(confidence * 100.0) / 100.0);
        record.setAiScore(aiScore);
        record.setStatus(confidence < CONFIDENCE_THRESHOLD ? "pending_review" : "approved");
        return record;
    }

    /**
     * 批量批改
     */
    public List<HomeworkRecord> mockGradeAll(List<HomeworkRecord> records) {
        records.forEach(this::mockGrade);
        return records;
    }
}
