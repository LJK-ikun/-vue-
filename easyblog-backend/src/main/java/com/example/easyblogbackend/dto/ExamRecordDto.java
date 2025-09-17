package com.example.easyblogbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ExamRecordDto {
    
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    @NotBlank(message = "考试名称不能为空")
    @Size(max = 100, message = "考试名称不能超过100个字符")
    private String examName;
    
    @NotNull(message = "问题ID列表不能为空")
    private List<Long> questionIds;
    
    private Integer currentQuestionIndex;
    
    private String userAnswers;
    
    private String correctAnswers;
    
    private Integer correctCount;
    
    private Double score;
    
    private Long timeSpent;
    
    private String status;

    public ExamRecordDto() {
    }

    public ExamRecordDto(Long userId, String examName, List<Long> questionIds, Integer currentQuestionIndex, 
                        String userAnswers, String correctAnswers, Integer correctCount, Double score, 
                        Long timeSpent, String status) {
        this.userId = userId;
        this.examName = examName;
        this.questionIds = questionIds;
        this.currentQuestionIndex = currentQuestionIndex;
        this.userAnswers = userAnswers;
        this.correctAnswers = correctAnswers;
        this.correctCount = correctCount;
        this.score = score;
        this.timeSpent = timeSpent;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public List<Long> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Long> questionIds) {
        this.questionIds = questionIds;
    }

    public Integer getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(Integer currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public String getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(String userAnswers) {
        this.userAnswers = userAnswers;
    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Integer getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(Integer correctCount) {
        this.correctCount = correctCount;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
