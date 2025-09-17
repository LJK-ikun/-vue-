package com.example.easyblogbackend.controller;

import com.example.easyblogbackend.entity.Question;
import com.example.easyblogbackend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = "*")
public class QuestionController {
    
    @Autowired
    private QuestionService questionService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createQuestion(@RequestBody Question question) {
        try {
            Question createdQuestion = questionService.createQuestion(question);
            return ResponseEntity.ok(createdQuestion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
        Question question = questionService.getQuestionById(id);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        questionService.incrementViewCount(id);
        return ResponseEntity.ok(question);
    }
    
    @GetMapping("/active/{id}")
    public ResponseEntity<?> getActiveQuestionById(@PathVariable Long id) {
        return questionService.getActiveQuestionById(id)
                .map(question -> {
                    questionService.incrementViewCount(id);
                    return ResponseEntity.ok(question);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<Page<Question>> getAllQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Question> questions = questionService.getAllQuestions(pageable);
        return ResponseEntity.ok(questions);
    }
    
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Page<Question>> getQuestionsByCategory(
            @PathVariable String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Question> questions = questionService.getQuestionsByCategory(categoryName, pageable);
        return ResponseEntity.ok(questions);
    }
    
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<Page<Question>> getQuestionsByDifficulty(
            @PathVariable String difficulty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Question> questions = questionService.getQuestionsByDifficulty(difficulty, pageable);
        return ResponseEntity.ok(questions);
    }
    
    @GetMapping("/category/{category}/difficulty/{difficulty}")
    public ResponseEntity<Page<Question>> getQuestionsByCategoryAndDifficulty(
            @PathVariable String category,
            @PathVariable String difficulty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Question> questions = questionService.getQuestionsByCategoryAndDifficulty(category, difficulty, pageable);
        return ResponseEntity.ok(questions);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Question>> searchQuestions(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Question> questions = questionService.searchQuestions(keyword, pageable);
        return ResponseEntity.ok(questions);
    }
    
    @GetMapping("/filter")
    public ResponseEntity<Page<Question>> getQuestionsByFilters(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Question> questions = questionService.getQuestionsByFilters(category, difficulty, keyword, pageable);
        return ResponseEntity.ok(questions);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        try {
            question.setId(id);
            Question updatedQuestion = questionService.updateQuestion(question);
            return ResponseEntity.ok(updatedQuestion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/{id}/like")
    public ResponseEntity<?> likeQuestion(@PathVariable Long id) {
        try {
            questionService.incrementLikeCount(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = questionService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/difficulties")
    public ResponseEntity<List<String>> getAllDifficulties() {
        List<String> difficulties = questionService.getAllDifficulties();
        return ResponseEntity.ok(difficulties);
    }
    
    @GetMapping("/most-viewed")
    public ResponseEntity<List<Question>> getMostViewedQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        List<Question> questions = questionService.getMostViewedQuestions(pageable);
        return ResponseEntity.ok(questions);
    }
    
    @GetMapping("/most-liked")
    public ResponseEntity<List<Question>> getMostLikedQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        List<Question> questions = questionService.getMostLikedQuestions(pageable);
        return ResponseEntity.ok(questions);
    }
    
    @GetMapping("/latest")
    public ResponseEntity<List<Question>> getLatestQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        List<Question> questions = questionService.getLatestQuestions(pageable);
        return ResponseEntity.ok(questions);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalQuestionsCount() {
        long count = questionService.getTotalQuestionsCount();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/count/category/{categoryName}")
    public ResponseEntity<Long> getQuestionsCountByCategory(@PathVariable String categoryName) {
        long count = questionService.getQuestionsCountByCategory(categoryName);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/count/difficulty/{difficulty}")
    public ResponseEntity<Long> getQuestionsCountByDifficulty(@PathVariable String difficulty) {
        long count = questionService.getQuestionsCountByDifficulty(difficulty);
        return ResponseEntity.ok(count);
    }
}
