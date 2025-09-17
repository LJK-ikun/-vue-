package com.example.easyblogbackend.repository;

import com.example.easyblogbackend.entity.ExamRecord;
import com.example.easyblogbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExamRecordRepository extends JpaRepository<ExamRecord, Long> {
    
    List<ExamRecord> findByUser(User user);
    
    Page<ExamRecord> findByUser(User user, Pageable pageable);
    
    @Query("SELECT e FROM ExamRecord e WHERE e.user = :user AND e.status = 'COMPLETED' ORDER BY e.createdAt DESC")
    List<ExamRecord> findCompletedExamsByUser(@Param("user") User user);
    
    @Query("SELECT e FROM ExamRecord e WHERE e.user = :user AND e.status = 'IN_PROGRESS' ORDER BY e.startTime DESC")
    List<ExamRecord> findInProgressExamsByUser(@Param("user") User user);
    
    @Query("SELECT e FROM ExamRecord e WHERE e.user = :user AND e.status = 'COMPLETED' ORDER BY e.score DESC")
    List<ExamRecord> findBestScoredExamsByUser(@Param("user") User user);
    
    @Query("SELECT e FROM ExamRecord e WHERE e.user = :user AND e.startTime BETWEEN :startDate AND :endDate")
    List<ExamRecord> findExamsByUserAndDateRange(@Param("user") User user, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(e) FROM ExamRecord e WHERE e.user = :user AND e.status = 'COMPLETED'")
    Long countCompletedExamsByUser(@Param("user") User user);
    
    @Query("SELECT AVG(e.score) FROM ExamRecord e WHERE e.user = :user AND e.status = 'COMPLETED'")
    Double getAverageScoreByUser(@Param("user") User user);
    
    @Query("SELECT SUM(e.timeSpent) FROM ExamRecord e WHERE e.user = :user AND e.status = 'COMPLETED'")
    Long getTotalTimeSpentByUser(@Param("user") User user);
    
    @Query("SELECT e FROM ExamRecord e WHERE e.user = :user AND e.examName = :examName")
    List<ExamRecord> findExamsByUserAndExamName(@Param("user") User user, @Param("examName") String examName);
    
    @Query("SELECT e FROM ExamRecord e WHERE e.status = 'COMPLETED' ORDER BY e.score DESC")
    Page<ExamRecord> findTopScores(Pageable pageable);
    
    @Query("SELECT e FROM ExamRecord e WHERE e.status = 'COMPLETED' AND e.createdAt >= :date")
    List<ExamRecord> findRecentCompletedExams(@Param("date") LocalDateTime date);
}
