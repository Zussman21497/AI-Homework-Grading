package org.example.aihomeworkgrading.service;

import lombok.RequiredArgsConstructor;
import org.example.aihomeworkgrading.model.Submission;
import org.example.aihomeworkgrading.repository.SubmissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    /** 查询所有提交记录 */
    public List<Submission> findAll() {
        return submissionRepository.findAll();
    }

    /** 按 ID 查询单条记录 */
    public Submission findById(Long id) {
        return submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found: " + id));
    }

    /**
     * 保存提交并触发 AI 批改（占位实现）
     * 后续替换为真实 AI 调用逻辑
     */
    public Submission save(Submission submission) {
        // TODO: 调用 AI 接口，将结果写入 feedback 和 score
        submission.setFeedback("AI 批改结果（待实现）");
        submission.setScore(0);
        return submissionRepository.save(submission);
    }

    /** 删除提交记录 */
    public void deleteById(Long id) {
        submissionRepository.deleteById(id);
    }
}
