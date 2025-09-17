package com.example.easyblogbackend.dto;

import jakarta.validation.constraints.NotNull;

public class ProgressDto {
    
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    @NotNull(message = "问题ID不能为空")
    private Long questionId;
    
    private String answer;
    
    private Boolean completed;
    
    private Integer attempts;
    
    private Long timeSpent;

    public ProgressDto() {
    }

    public ProgressDto(Long userId, Long questionId, String answer, Boolean completed, Integer attempts, Long timeSpent) {
        this.userId = userId;
        this.questionId = questionId;
        this.answer = answer;
        this.completed = completed;
        this.attempts = attempts;
        this.timeSpent = timeSpent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }
}
