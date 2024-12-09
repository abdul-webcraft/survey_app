package com.survey.repository;

import com.survey.model.Dimension;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DimensionRepository extends JpaRepository<Dimension,Long> {
    List<Dimension> findBySurveyId(Long surveyId);

    boolean existsByName(String name);
}
