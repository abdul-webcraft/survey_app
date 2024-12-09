package com.survey.repository;

import com.survey.model.Question;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {


    List<Question> findByDimensionId(Long dimensionId);

    boolean existsByText(String text);
}
