package org.example.aihomeworkgrading.controller;

import lombok.RequiredArgsConstructor;
import org.example.aihomeworkgrading.model.HomeworkRecord;
import org.example.aihomeworkgrading.service.HomeworkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/homework")
@RequiredArgsConstructor
public class HomeworkController {

    private final HomeworkService homeworkService;

    /** POST /api/homework/upload — 批量上传题目 JSON，触发 Mock AI 批改 */
    @PostMapping("/upload")
    public ResponseEntity<List<HomeworkRecord>> upload(@RequestBody List<HomeworkRecord> records) {
        return ResponseEntity.ok(homeworkService.upload(records));
    }

    /**
     * POST /api/homework/upload-photo — 接收作业图片（multipart），模拟 OCR 切题
     * 参数：file=图片文件，studentName=学生姓名
     */
    @PostMapping("/upload-photo")
    public ResponseEntity<List<HomeworkRecord>> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "studentName", defaultValue = "新同学") String studentName) {
        // Demo：忽略图片内容，直接返回 Mock 切题结果
        return ResponseEntity.ok(homeworkService.uploadPhoto(studentName));
    }

    /** GET /api/homework/review-list — 获取待复核题目（confidence < 0.8） */
    @GetMapping("/review-list")
    public ResponseEntity<List<HomeworkRecord>> reviewList() {
        return ResponseEntity.ok(homeworkService.getReviewList());
    }

    /** GET /api/homework/all — 获取全部题目（进度条用） */
    @GetMapping("/all")
    public ResponseEntity<List<HomeworkRecord>> all() {
        return ResponseEntity.ok(homeworkService.getAll());
    }

    /**
     * PUT /api/homework/correct — 教师纠偏
     * Body: { "questionId": "math_001", "approved": true }
     */
    /** GET /api/homework/variant/{questionId} — 返回同类变式题 */
    @GetMapping("/variant/{questionId}")
    public ResponseEntity<HomeworkRecord> variant(@PathVariable String questionId) {
        return ResponseEntity.ok(homeworkService.getVariant(questionId));
    }
}

