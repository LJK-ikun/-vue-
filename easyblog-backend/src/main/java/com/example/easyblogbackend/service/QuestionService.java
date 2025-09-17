package com.example.easyblogbackend.service;

import com.example.easyblogbackend.entity.Question;
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
public class QuestionService {
    
    @Autowired
    private QuestionRepository questionRepository;
    
    public Question createQuestion(Question question) {
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());
        question.setIsActive(true);
        question.setViewCount(0L);
        question.setLikeCount(0L);
        return questionRepository.save(question);
    }
    
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }
    
    public Optional<Question> getActiveQuestionById(Long id) {
        return questionRepository.findActiveById(id);
    }
    
    public Page<Question> getAllQuestions(Pageable pageable) {
        return questionRepository.findByIsActiveTrue(pageable);
    }
    
    public Page<Question> getQuestionsByCategory(String categoryName, Pageable pageable) {
        return questionRepository.findByCategoryName(categoryName, pageable);
    }
    
    public Page<Question> getQuestionsByDifficulty(String difficulty, Pageable pageable) {
        return questionRepository.findByDifficulty(difficulty, pageable);
    }
    
    public Page<Question> getQuestionsByCategoryAndDifficulty(String categoryName, String difficulty, Pageable pageable) {
        return questionRepository.findByCategoryNameAndDifficulty(categoryName, difficulty, pageable);
    }
    
    public Page<Question> searchQuestions(String keyword, Pageable pageable) {
        return questionRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, pageable);
    }
    
    public Page<Question> getQuestionsByFilters(String category, String difficulty, String keyword, Pageable pageable) {
        return questionRepository.findQuestionsByFilters(category, difficulty, keyword, pageable);
    }
    
    public Question updateQuestion(Question question) {
        Question existingQuestion = questionRepository.findById(question.getId())
                .orElseThrow(() -> new RuntimeException("题目不存在"));
        
        existingQuestion.setTitle(question.getTitle());
        existingQuestion.setContent(question.getContent());
        existingQuestion.setAnswer(question.getAnswer());
        existingQuestion.setCategoryId(question.getCategoryId());
        existingQuestion.setCategoryName(question.getCategoryName());
        existingQuestion.setDifficulty(question.getDifficulty());
        existingQuestion.setTags(question.getTags());
        existingQuestion.setUpdatedAt(LocalDateTime.now());
        
        return questionRepository.save(existingQuestion);
    }
    
    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("题目不存在"));
        question.setIsActive(false);
        question.setUpdatedAt(LocalDateTime.now());
        questionRepository.save(question);
    }
    
    public void incrementViewCount(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("题目不存在"));
        question.setViewCount(question.getViewCount() + 1);
        question.setUpdatedAt(LocalDateTime.now());
        questionRepository.save(question);
    }
    
    public void incrementLikeCount(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("题目不存在"));
        question.setLikeCount(question.getLikeCount() + 1);
        question.setUpdatedAt(LocalDateTime.now());
        questionRepository.save(question);
    }
    
    public List<String> getAllCategories() {
        return questionRepository.findAllCategories();
    }
    
    public List<String> getAllDifficulties() {
        return questionRepository.findAllDifficulties();
    }
    
    public List<Question> getMostViewedQuestions(Pageable pageable) {
        return questionRepository.findMostViewedQuestions(pageable);
    }
    
    public List<Question> getMostLikedQuestions(Pageable pageable) {
        return questionRepository.findMostLikedQuestions(pageable);
    }
    
    public List<Question> getLatestQuestions(Pageable pageable) {
        return questionRepository.findLatestQuestions(pageable);
    }
    
    public long getTotalQuestionsCount() {
        return questionRepository.countByIsActiveTrue();
    }
    
    public long getQuestionsCountByCategory(String categoryName) {
        return questionRepository.countByCategoryNameAndIsActiveTrue(categoryName);
    }
    
    public long getQuestionsCountByDifficulty(String difficulty) {
        return questionRepository.countByDifficultyAndIsActiveTrue(difficulty);
    }
}
