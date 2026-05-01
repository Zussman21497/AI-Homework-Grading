package org.example.aihomeworkgrading.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 作业提交实体
 */
@Data
@Entity
@Table(name = "submission")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 学生姓名 */
    private String studentName;

    /** 作业内容 */
    @Column(columnDefinition = "TEXT")
    private String content;

    /** AI 批改结果 */
    @Column(columnDefinition = "TEXT")
    private String feedback;

    /** 分数（0-100） */
    private Integer score;
}
