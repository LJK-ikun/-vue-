package com.example.easyblogbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "exam_records")
@EntityListeners(AuditingEntityListener.class)
public class ExamRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "exam_name")
    private String examName;
    
    @Column(name = "total_questions")
    private Integer totalQuestions = 0;
    
    @Column(name = "correct_count")
    private Integer correctCount = 0;
    
    @Column(name = "score")
    private Double score = 0.0;
    
    @Column(name = "time_spent")
    private Long timeSpent = 0L;
    
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    @Column(name = "status")
    private String status = "IN_PROGRESS"; // IN_PROGRESS, COMPLETED, FAILED
    
    @ElementCollection
    @CollectionTable(name = "exam_question_records", joinColumns = @JoinColumn(name = "exam_record_id"))
    @OrderColumn(name = "question_order")
    private List<Long> questionIds;
    
    @Column(name = "user_answers", columnDefinition = "TEXT")
    private String userAnswers;
    
    @Column(name = "correct_answers", columnDefinition = "TEXT")
    private String correctAnswers;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
