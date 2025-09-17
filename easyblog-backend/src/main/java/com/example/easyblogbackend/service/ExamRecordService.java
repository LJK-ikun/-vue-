package com.example.easyblogbackend.service;

import com.example.easyblogbackend.entity.ExamRecord;
import com.example.easyblogbackend.entity.User;
import com.example.easyblogbackend.repository.ExamRecordRepository;
import com.example.easyblogbackend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExamRecordService {
    
    @Autowired
    private ExamRecordRepository examRecordRepository;
    
    @Autowired
    private QuestionRepository questionRepository;
    
    public ExamRecord createExamRecord(User user, String examName, List<Long> questionIds) {
        ExamRecord examRecord = new ExamRecord();
        examRecord.setUser(user);
        examRecord.setExamName(examName);
        examRecord.setTotalQuestions(questionIds.size());
        examRecord.setQuestionIds(questionIds);
        examRecord.setStartTime(LocalDateTime.now());
        examRecord.setStatus("IN_PROGRESS");
        examRecord.setCreatedAt(LocalDateTime.now());
        examRecord.setUpdatedAt(LocalDateTime.now());
        
        return examRecordRepository.save(examRecord);
    }
    
    public ExamRecord getExamRecordById(Long id) {
        return examRecordRepository.findById(id).orElse(null);
    }
    
    public List<ExamRecord> getExamRecordsByUser(User user) {
        return examRecordRepository.findByUser(user);
    }
    
    public Page<ExamRecord> getExamRecordsByUser(User user, Pageable pageable) {
        return examRecordRepository.findByUser(user, pageable);
    }
    
    public List<ExamRecord> getCompletedExamsByUser(User user) {
        return examRecordRepository.findCompletedExamsByUser(user);
    }
    
    public List<ExamRecord> getInProgressExamsByUser(User user) {
        return examRecordRepository.findInProgressExamsByUser(user);
    }
    
    public List<ExamRecord> getBestScoredExamsByUser(User user) {
        return examRecordRepository.findBestScoredExamsByUser(user);
    }
    
    public List<ExamRecord> getExamsByUserAndDateRange(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return examRecordRepository.findExamsByUserAndDateRange(user, startDate, endDate);
    }
    
    public List<ExamRecord> getExamsByUserAndExamName(User user, String examName) {
        return examRecordRepository.findExamsByUserAndExamName(user, examName);
    }
    
    public ExamRecord updateExamRecord(ExamRecord examRecord) {
        ExamRecord existingRecord = examRecordRepository.findById(examRecord.getId())
                .orElseThrow(() -> new RuntimeException("考试记录不存在"));
        
        existingRecord.setExamName(examRecord.getExamName());
        existingRecord.setCorrectCount(examRecord.getCorrectCount());
        existingRecord.setScore(examRecord.getScore());
        existingRecord.setTimeSpent(examRecord.getTimeSpent());
        existingRecord.setStatus(examRecord.getStatus());
        existingRecord.setUserAnswers(examRecord.getUserAnswers());
        existingRecord.setCorrectAnswers(examRecord.getCorrectAnswers());
        existingRecord.setUpdatedAt(LocalDateTime.now());
        
        return examRecordRepository.save(existingRecord);
    }
    
    public ExamRecord completeExam(Long examRecordId, Integer correctCount, Double score, Long timeSpent, 
                                 String userAnswers, String correctAnswers) {
        ExamRecord examRecord = getExamRecordById(examRecordId);
        if (examRecord == null) {
            throw new RuntimeException("考试记录不存在");
        }
        
        examRecord.setCorrectCount(correctCount);
        examRecord.setScore(score);
        examRecord.setTimeSpent(timeSpent);
        examRecord.setEndTime(LocalDateTime.now());
        examRecord.setStatus("COMPLETED");
        examRecord.setUserAnswers(userAnswers);
        examRecord.setCorrectAnswers(correctAnswers);
        examRecord.setUpdatedAt(LocalDateTime.now());
        
        return examRecordRepository.save(examRecord);
    }
    
    public void failExam(Long examRecordId) {
        ExamRecord examRecord = getExamRecordById(examRecordId);
        if (examRecord == null) {
            throw new RuntimeException("考试记录不存在");
        }
        
        examRecord.setStatus("FAILED");
        examRecord.setEndTime(LocalDateTime.now());
        examRecord.setUpdatedAt(LocalDateTime.now());
        
        examRecordRepository.save(examRecord);
    }
    
    public long countCompletedExamsByUser(User user) {
        return examRecordRepository.countCompletedExamsByUser(user);
    }
    
    public Double getAverageScoreByUser(User user) {
        return examRecordRepository.getAverageScoreByUser(user);
    }
    
    public Long getTotalTimeSpentByUser(User user) {
        return examRecordRepository.getTotalTimeSpentByUser(user);
    }
    
    public Page<ExamRecord> getTopScores(Pageable pageable) {
        return examRecordRepository.findTopScores(pageable);
    }
    
    public List<ExamRecord> getRecentCompletedExams(LocalDateTime sinceDate) {
        return examRecordRepository.findRecentCompletedExams(sinceDate);
    }
    
    public void deleteExamRecord(Long id) {
        examRecordRepository.deleteById(id);
    }
    
    public ExamRecord updateExamProgress(Long examRecordId, Integer currentQuestionIndex, String userAnswer) {
        ExamRecord examRecord = getExamRecordById(examRecordId);
        if (examRecord == null) {
            throw new RuntimeException("考试记录不存在");
        }
        
        // 更新用户答案
        if (examRecord.getUserAnswers() == null) {
            examRecord.setUserAnswers("[]");
        }
        
        // 这里可以添加更新当前题目答案的逻辑
        examRecord.setUpdatedAt(LocalDateTime.now());
        
        return examRecordRepository.save(examRecord);
    }
}
