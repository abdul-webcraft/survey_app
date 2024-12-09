package com.survey.controllers;

import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Survey;
import com.survey.payloads.request.SurveyRequest;
import com.survey.services.SurveyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @PostMapping("survey")
    public ResponseEntity<Survey> createSurvey(@Valid @RequestBody SurveyRequest surveyRequest) throws AlreadyExistsException, ServiceLogicException {
        return ResponseEntity.status(HttpStatus.CREATED).body(surveyService.createSurvey(surveyRequest));
    }

    @GetMapping("survey/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable Long id) throws ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.ok(surveyService.getSurveyById(id));
    }

    @GetMapping("surveys")
    public ResponseEntity<List<Survey>> getAllSurvey() throws ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }

    @PutMapping("survey/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable Long id, @Valid @RequestBody SurveyRequest surveyRequest) throws ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.ok(surveyService.updateSurvey(id, surveyRequest));
    }

    @DeleteMapping("survey/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) throws ResourceNotFoundException, ServiceLogicException {
        surveyService.deleteSurvey(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("survey/")
    public ResponseEntity<Survey> createSurveyWithDimensionQuestionAndAddOptions(@Valid @RequestBody SurveyRequest surveyRequest) throws AlreadyExistsException, ServiceLogicException {
        return ResponseEntity.status(HttpStatus.CREATED).body(surveyService.createSurveyWithDimensionQuestionAndAddOptions(surveyRequest));
    }
}
