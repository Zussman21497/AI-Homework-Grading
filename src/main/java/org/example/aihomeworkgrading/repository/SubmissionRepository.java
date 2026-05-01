package org.example.aihomeworkgrading.repository;

import org.example.aihomeworkgrading.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    /** 按学生姓名查询所有提交记录 */
    List<Submission> findByStudentName(String studentName);
}
