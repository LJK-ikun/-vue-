package com.example.easyblogbackend.repository;

import com.example.easyblogbackend.entity.Progress;
import com.example.easyblogbackend.entity.User;
import com.example.easyblogbackend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    
    Optional<Progress> findByUserAndQuestion(User user, Question question);
    
    List<Progress> findByUser(User user);
    
    @Query("SELECT p FROM Progress p WHERE p.user = :user AND p.question.isActive = true")
    List<Progress> findActiveProgressByUser(@Param("user") User user);
    
    @Query("SELECT COUNT(p) FROM Progress p WHERE p.user = :user AND p.isDone = true")
    Long countCompletedQuestionsByUser(@Param("user") User user);
    
    @Query("SELECT COUNT(p) FROM Progress p WHERE p.user = :user")
    Long countTotalProgressByUser(@Param("user") User user);
    
    @Query("SELECT p FROM Progress p WHERE p.user = :user AND p.isDone = false")
    List<Progress> findIncompleteProgressByUser(@Param("user") User user);
    
    @Query("SELECT p FROM Progress p WHERE p.user = :user ORDER BY p.lastAccessedAt DESC")
    List<Progress> findRecentProgressByUser(@Param("user") User user);
    
    @Query("SELECT p FROM Progress p WHERE p.user = :user AND p.question.categoryName = :category")
    List<Progress> findProgressByUserAndCategory(@Param("user") User user, @Param("category") String category);
    
    @Query("SELECT p FROM Progress p WHERE p.user = :user AND p.question.difficulty = :difficulty")
    List<Progress> findProgressByUserAndDifficulty(@Param("user") User user, @Param("difficulty") String difficulty);
    
    @Query("SELECT p FROM Progress p WHERE p.user = :user AND p.question.id = :questionId")
    Optional<Progress> findByUserIdAndQuestionId(@Param("user") User user, @Param("questionId") Long questionId);
}
