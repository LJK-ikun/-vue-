package com.example.easyblogbackend.controller;

import com.example.easyblogbackend.entity.Progress;
import com.example.easyblogbackend.entity.User;
import com.example.easyblogbackend.service.ProgressService;
import com.example.easyblogbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
@CrossOrigin(origins = "*")
public class ProgressController {
    
    @Autowired
    private ProgressService progressService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createProgress(@RequestBody Progress progress) {
        try {
            User user = userService.getUserById(progress.getUser().getId());
            if (user == null) {
                return ResponseEntity.badRequest().body("用户不存在");
            }
            
            Progress createdProgress = progressService.createProgress(user, progress.getQuestion());
            return ResponseEntity.ok(createdProgress);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getProgressById(@PathVariable Long id) {
        Progress progress = progressService.getProgressById(id);
        if (progress == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(progress);
    }
    
    @GetMapping("/user/{userId}/question/{questionId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getProgressByUserAndQuestion(
            @PathVariable Long userId, 
            @PathVariable Long questionId) {
        
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }
        
        return progressService.getProgressByUserIdAndQuestionId(user, questionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Progress>> getProgressByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<Progress> progressList = progressService.getProgressByUser(user);
        return ResponseEntity.ok(progressList);
    }
    
    @GetMapping("/user/{userId}/active")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Progress>> getActiveProgressByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<Progress> progressList = progressService.getActiveProgressByUser(user);
        return ResponseEntity.ok(progressList);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateProgress(@PathVariable Long id, @RequestBody Progress progress) {
        try {
            progress.setId(id);
            Progress updatedProgress = progressService.updateProgress(progress);
            return ResponseEntity.ok(updatedProgress);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/user/{userId}/question/{questionId}/answer")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateProgressAnswer(
            @PathVariable Long userId,
            @PathVariable Long questionId,
            @RequestBody String answer) {
        
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.badRequest().body("用户不存在");
            }
            
            Progress progress = progressService.updateProgressAnswer(user, questionId, answer);
            return ResponseEntity.ok(progress);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/user/{userId}/question/{questionId}/done")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> markQuestionAsDone(
            @PathVariable Long userId,
            @PathVariable Long questionId) {
        
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.badRequest().body("用户不存在");
            }
            
            Progress progress = progressService.markQuestionAsDone(user, questionId);
            return ResponseEntity.ok(progress);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/user/{userId}/question/{questionId}/attempts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> incrementAttempts(
            @PathVariable Long userId,
            @PathVariable Long questionId) {
        
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.badRequest().body("用户不存在");
            }
            
            progressService.incrementAttempts(user, questionId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/user/{userId}/question/{questionId}/time")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateTimeSpent(
            @PathVariable Long userId,
            @PathVariable Long questionId,
            @RequestParam long additionalTime) {
        
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.badRequest().body("用户不存在");
            }
            
            progressService.updateTimeSpent(user, questionId, additionalTime);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/user/{userId}/completed-count")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Long> countCompletedQuestionsByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        long count = progressService.countCompletedQuestionsByUser(user);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/user/{userId}/total-count")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Long> countTotalProgressByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        long count = progressService.countTotalProgressByUser(user);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/user/{userId}/completion-rate")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Double> getCompletionRateByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        double rate = progressService.getCompletionRateByUser(user);
        return ResponseEntity.ok(rate);
    }
    
    @GetMapping("/user/{userId}/incomplete")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Progress>> getIncompleteProgressByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<Progress> progressList = progressService.getIncompleteProgressByUser(user);
        return ResponseEntity.ok(progressList);
    }
    
    @GetMapping("/user/{userId}/recent")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Progress>> getRecentProgressByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<Progress> progressList = progressService.getRecentProgressByUser(user);
        return ResponseEntity.ok(progressList);
    }
    
    @GetMapping("/user/{userId}/category/{category}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Progress>> getProgressByUserAndCategory(
            @PathVariable Long userId,
            @PathVariable String category) {
        
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<Progress> progressList = progressService.getProgressByUserAndCategory(user, category);
        return ResponseEntity.ok(progressList);
    }
    
    @GetMapping("/user/{userId}/difficulty/{difficulty}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Progress>> getProgressByUserAndDifficulty(
            @PathVariable Long userId,
            @PathVariable String difficulty) {
        
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        List<Progress> progressList = progressService.getProgressByUserAndDifficulty(user, difficulty);
        return ResponseEntity.ok(progressList);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteProgress(@PathVariable Long id) {
        try {
            progressService.deleteProgress(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
