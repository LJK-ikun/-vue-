package com.example.easyblogbackend.controller;

import com.example.easyblogbackend.entity.ExamRecord;
import com.example.easyblogbackend.entity.User;
import com.example.easyblogbackend.service.ExamRecordService;
import com.example.easyblogbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/exam-records")
@CrossOrigin(origins = "*")
public class ExamRecordController {
    
    @Autowired
    private ExamRecordService examRecordService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createExamRecord(@RequestBody ExamRecord examRecord) {
        try {
            User user = userService.getUserById(examRecord.getUser().getId());
            if (user == null) {
                return ResponseEntity.badRequest().body("用户不存在");
            }
            
            ExamRecord createdRecord = examRecordService.createExamRecord(
                    user, 
                    examRecord.getExamName(), 
                    examRecord.getQuestionIds());
            return ResponseEntity.ok(createdRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getExamRecordById(@PathVariable Long id) {
        ExamRecord examRecord = examRecordService.getExamRecordById(id);
        if (examRecord == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(examRecord);
    }
    
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ExamRecord>> getExamRecordsByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<ExamRecord> records = examRecordService.getExamRecordsByUser(user);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/user/{userId}/page")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Page<ExamRecord>> getExamRecordsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<ExamRecord> records = examRecordService.getExamRecordsByUser(user, pageable);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/user/{userId}/completed")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ExamRecord>> getCompletedExamsByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<ExamRecord> records = examRecordService.getCompletedExamsByUser(user);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/user/{userId}/in-progress")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ExamRecord>> getInProgressExamsByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<ExamRecord> records = examRecordService.getInProgressExamsByUser(user);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/user/{userId}/best-scored")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ExamRecord>> getBestScoredExamsByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<ExamRecord> records = examRecordService.getBestScoredExamsByUser(user);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/user/{userId}/date-range")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ExamRecord>> getExamsByUserAndDateRange(
            @PathVariable Long userId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<ExamRecord> records = examRecordService.getExamsByUserAndDateRange(user, startDate, endDate);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/user/{userId}/exam-name/{examName}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ExamRecord>> getExamsByUserAndExamName(
            @PathVariable Long userId,
            @PathVariable String examName) {
        
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<ExamRecord> records = examRecordService.getExamsByUserAndExamName(user, examName);
        return ResponseEntity.ok(records);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateExamRecord(@PathVariable Long id, @RequestBody ExamRecord examRecord) {
        try {
            examRecord.setId(id);
            ExamRecord updatedRecord = examRecordService.updateExamRecord(examRecord);
            return ResponseEntity.ok(updatedRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/{id}/complete")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> completeExam(
            @PathVariable Long id,
            @RequestParam Integer correctCount,
            @RequestParam Double score,
            @RequestParam Long timeSpent,
            @RequestParam String userAnswers,
            @RequestParam String correctAnswers) {
        
        try {
            ExamRecord examRecord = examRecordService.completeExam(
                    id, correctCount, score, timeSpent, userAnswers, correctAnswers);
            return ResponseEntity.ok(examRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/{id}/fail")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> failExam(@PathVariable Long id) {
        try {
            examRecordService.failExam(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/user/{userId}/completed-count")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Long> countCompletedExamsByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        long count = examRecordService.countCompletedExamsByUser(user);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/user/{userId}/average-score")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Double> getAverageScoreByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Double averageScore = examRecordService.getAverageScoreByUser(user);
        return ResponseEntity.ok(averageScore);
    }
    
    @GetMapping("/user/{userId}/total-time")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Long> getTotalTimeSpentByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Long totalTime = examRecordService.getTotalTimeSpentByUser(user);
        return ResponseEntity.ok(totalTime);
    }
    
    @GetMapping("/top-scores")
    public ResponseEntity<Page<ExamRecord>> getTopScores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "score") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<ExamRecord> records = examRecordService.getTopScores(pageable);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/recent-completed")
    public ResponseEntity<List<ExamRecord>> getRecentCompletedExams(
            @RequestParam(defaultValue = "7") int days) {
        
        LocalDateTime sinceDate = LocalDateTime.now().minusDays(days);
        List<ExamRecord> records = examRecordService.getRecentCompletedExams(sinceDate);
        return ResponseEntity.ok(records);
    }
    
    @PostMapping("/{id}/progress")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateExamProgress(
            @PathVariable Long id,
            @RequestParam Integer currentQuestionIndex,
            @RequestBody String userAnswer) {
        
        try {
            ExamRecord examRecord = examRecordService.updateExamProgress(
                    id, currentQuestionIndex, userAnswer);
            return ResponseEntity.ok(examRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteExamRecord(@PathVariable Long id) {
        try {
            examRecordService.deleteExamRecord(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
