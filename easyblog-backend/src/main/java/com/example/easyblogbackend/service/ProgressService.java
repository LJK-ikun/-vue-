package com.example.easyblogbackend.service;

import com.example.easyblogbackend.entity.Progress;
import com.example.easyblogbackend.entity.User;
import com.example.easyblogbackend.entity.Question;
import com.example.easyblogbackend.repository.ProgressRepository;
import com.example.easyblogbackend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProgressService {
    
    @Autowired
    private ProgressRepository progressRepository;
    
    @Autowired
    private QuestionRepository questionRepository;
    
    public Progress createProgress(User user, Question question) {
        Optional<Progress> existingProgress = progressRepository.findByUserAndQuestion(user, question);
        if (existingProgress.isPresent()) {
            return existingProgress.get();
        }
        
        Progress progress = new Progress();
        progress.setUser(user);
        progress.setQuestion(question);
        progress.setCreatedAt(LocalDateTime.now());
        progress.setUpdatedAt(LocalDateTime.now());
        progress.setLastAccessedAt(LocalDateTime.now());
        
        return progressRepository.save(progress);
    }
    
    public Progress getProgressById(Long id) {
        return progressRepository.findById(id).orElse(null);
    }
    
    public Optional<Progress> getProgressByUserAndQuestion(User user, Question question) {
        return progressRepository.findByUserAndQuestion(user, question);
    }
    
    public List<Progress> getProgressByUser(User user) {
        return progressRepository.findByUser(user);
    }
    
    public List<Progress> getActiveProgressByUser(User user) {
        return progressRepository.findActiveProgressByUser(user);
    }
    
    public Progress updateProgress(Progress progress) {
        Progress existingProgress = progressRepository.findById(progress.getId())
                .orElseThrow(() -> new RuntimeException("学习进度不存在"));
        
        existingProgress.setAnswer(progress.getAnswer());
        existingProgress.setIsDone(progress.getIsDone());
        existingProgress.setTimeSpent(progress.getTimeSpent());
        existingProgress.setAttempts(progress.getAttempts());
        existingProgress.setLastAccessedAt(LocalDateTime.now());
        existingProgress.setUpdatedAt(LocalDateTime.now());
        
        return progressRepository.save(existingProgress);
    }
    
    public Progress updateProgressAnswer(User user, Long questionId, String answer) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("题目不存在"));
        
        Progress progress = createProgress(user, question);
        progress.setAnswer(answer);
        progress.setLastAccessedAt(LocalDateTime.now());
        progress.setUpdatedAt(LocalDateTime.now());
        
        return progressRepository.save(progress);
    }
    
    public Progress markQuestionAsDone(User user, Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("题目不存在"));
        
        Progress progress = createProgress(user, question);
        progress.setIsDone(true);
        progress.setLastAccessedAt(LocalDateTime.now());
        progress.setUpdatedAt(LocalDateTime.now());
        
        return progressRepository.save(progress);
    }
    
    public void incrementAttempts(User user, Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("题目不存在"));
        
        Progress progress = createProgress(user, question);
        progress.setAttempts(progress.getAttempts() + 1);
        progress.setLastAccessedAt(LocalDateTime.now());
        progress.setUpdatedAt(LocalDateTime.now());
        
        progressRepository.save(progress);
    }
    
    public void updateTimeSpent(User user, Long questionId, long additionalTime) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("题目不存在"));
        
        Progress progress = createProgress(user, question);
        progress.setTimeSpent(progress.getTimeSpent() + additionalTime);
        progress.setLastAccessedAt(LocalDateTime.now());
        progress.setUpdatedAt(LocalDateTime.now());
        
        progressRepository.save(progress);
    }
    
    public long countCompletedQuestionsByUser(User user) {
        return progressRepository.countCompletedQuestionsByUser(user);
    }
    
    public long countTotalProgressByUser(User user) {
        return progressRepository.countTotalProgressByUser(user);
    }
    
    public double getCompletionRateByUser(User user) {
        long total = countTotalProgressByUser(user);
        if (total == 0) return 0.0;
        
        long completed = countCompletedQuestionsByUser(user);
        return (double) completed / total * 100;
    }
    
    public List<Progress> getIncompleteProgressByUser(User user) {
        return progressRepository.findIncompleteProgressByUser(user);
    }
    
    public List<Progress> getRecentProgressByUser(User user) {
        return progressRepository.findRecentProgressByUser(user);
    }
    
    public List<Progress> getProgressByUserAndCategory(User user, String category) {
        return progressRepository.findProgressByUserAndCategory(user, category);
    }
    
    public List<Progress> getProgressByUserAndDifficulty(User user, String difficulty) {
        return progressRepository.findProgressByUserAndDifficulty(user, difficulty);
    }
    
    public Optional<Progress> getProgressByUserIdAndQuestionId(User user, Long questionId) {
        return progressRepository.findByUserIdAndQuestionId(user, questionId);
    }
    
    public void deleteProgress(Long id) {
        progressRepository.deleteById(id);
    }
}
