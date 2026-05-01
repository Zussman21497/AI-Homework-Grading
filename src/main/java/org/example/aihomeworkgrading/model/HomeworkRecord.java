package org.example.aihomeworkgrading.model;

import lombok.Data;

import java.util.List;

/**
 * 作业题目记录（不持久化，纯内存 Demo 用）
 */
@Data
public class HomeworkRecord {

    /** 题目唯一 ID，如 math_001 */
    private String questionId;

    /** 题型：objective（客观题）/ subjective（主观题） */
    private String type;

    /** 学生作答内容 */
    private String studentAnswer;

    /** AI 给出的分数 */
    private Integer aiScore;

    /**
     * AI 判定置信度（0.0 ~ 1.0）
     * < 0.8 时状态自动设为 pending_review
     */
    private Double confidence;

    /** 分步解析提示列表 */
    private List<String> analysisSteps;

    /**
     * 批改状态：
     *   approved        — AI 高置信，已通过
     *   pending_review  — 置信度低，待教师复核
     *   corrected       — 教师已纠偏
     */
    private String status;
}
