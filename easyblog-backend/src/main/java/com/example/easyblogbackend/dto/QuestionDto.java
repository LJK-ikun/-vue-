package com.example.easyblogbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class QuestionDto {
    
    @NotBlank(message = "问题内容不能为空")
    @Size(max = 1000, message = "问题内容不能超过1000个字符")
    private String content;
    
    @NotBlank(message = "选项A不能为空")
    @Size(max = 500, message = "选项A不能超过500个字符")
    private String optionA;
    
    @NotBlank(message = "选项B不能为空")
    @Size(max = 500, message = "选项B不能超过500个字符")
    private String optionB;
    
    @Size(max = 500, message = "选项C不能超过500个字符")
    private String optionC;
    
    @Size(max = 500, message = "选项D不能超过500个字符")
    private String optionD;
    
    @NotBlank(message = "正确答案不能为空")
    private String correctAnswer;
    
    @NotBlank(message = "分类不能为空")
    private String category;
    
    @NotBlank(message = "难度不能为空")
    private String difficulty;
    
    @Size(max = 200, message = "解析不能超过200个字符")
    private String explanation;
    
    private String tags;

    public QuestionDto() {
    }

    public QuestionDto(String content, String optionA, String optionB, String optionC, String optionD, 
                      String correctAnswer, String category, String difficulty, String explanation, String tags) {
        this.content = content;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.category = category;
        this.difficulty = difficulty;
        this.explanation = explanation;
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
