package com.survey.repository;

import com.survey.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option,Long> {
    List<Option> findByQuestionId(Long questionId);
}
