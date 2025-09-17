package com.example.easyblogbackend.repository;

import com.example.easyblogbackend.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    Page<Question> findByIsActiveTrue(Pageable pageable);
    
    Page<Question> findByCategoryName(String categoryName, Pageable pageable);
    
    Page<Question> findByDifficulty(String difficulty, Pageable pageable);
    
    Page<Question> findByCategoryNameAndDifficulty(String categoryName, String difficulty, Pageable pageable);
    
    Page<Question> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String keyword, String keyword2, Pageable pageable);
    
    @Query("SELECT q FROM Question q WHERE q.isActive = true AND " +
           "(:category IS NULL OR q.categoryName = :category) AND " +
           "(:difficulty IS NULL OR q.difficulty = :difficulty) AND " +
           "(:keyword IS NULL OR LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(q.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Question> findQuestionsByFilters(
            @Param("category") String category,
            @Param("difficulty") String difficulty,
            @Param("keyword") String keyword,
            Pageable pageable);
    
    @Query("SELECT DISTINCT q.categoryName FROM Question q WHERE q.isActive = true")
    List<String> findAllCategories();
    
    @Query("SELECT DISTINCT q.difficulty FROM Question q WHERE q.isActive = true")
    List<String> findAllDifficulties();
    
    @Query("SELECT q FROM Question q WHERE q.isActive = true ORDER BY q.viewCount DESC")
    List<Question> findMostViewedQuestions(Pageable pageable);
    
    @Query("SELECT q FROM Question q WHERE q.isActive = true ORDER BY q.likeCount DESC")
    List<Question> findMostLikedQuestions(Pageable pageable);
    
    @Query("SELECT q FROM Question q WHERE q.isActive = true ORDER BY q.createdAt DESC")
    List<Question> findLatestQuestions(Pageable pageable);
    
    @Query("SELECT q FROM Question q WHERE q.isActive = true AND q.id = :id")
    Optional<Question> findActiveById(@Param("id") Long id);
    
    Long countByIsActiveTrue();
    
    Long countByCategoryNameAndIsActiveTrue(String categoryName);
    
    Long countByDifficultyAndIsActiveTrue(String difficulty);
}
