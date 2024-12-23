package com.survey.controllers;

import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Option;
import com.survey.model.Question;
import com.survey.payloads.request.OptionRequest;
import com.survey.payloads.request.QuestionRequest;
import com.survey.services.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("question/dimension/{dimensionId}")
    public ResponseEntity<?> createQuestion(
            @PathVariable Long dimensionId,
            @Valid @RequestBody QuestionRequest questionRequest) throws ResourceNotFoundException, AlreadyExistsException , ServiceLogicException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(questionService.createQuestion(dimensionId, questionRequest));
    }

    @PutMapping("question/{id}")
    public ResponseEntity<?> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody QuestionRequest questionRequest) throws ResourceNotFoundException ,ServiceLogicException{
        return ResponseEntity.ok(questionService.updateQuestion(id, questionRequest));
    }

    @DeleteMapping("question/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) throws ResourceNotFoundException,ServiceLogicException {
        return ResponseEntity.ok(questionService.deleteQuestion(id));
    }

    @GetMapping("question/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) throws ResourceNotFoundException,ServiceLogicException {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @GetMapping("questions")
    public ResponseEntity<?> getAllQuestion() throws ResourceNotFoundException,ServiceLogicException {
        return ResponseEntity.ok(questionService.getAllQuestion());
    }

    @GetMapping("questions/dimension/{dimensionId}")
    public ResponseEntity<?> getQuestionsByDimension(@PathVariable Long dimensionId) throws ResourceNotFoundException,ServiceLogicException {
        return ResponseEntity.ok(questionService.getQuestionsByDimension(dimensionId));
    }

    @PostMapping("question/{questionId}/option")
    public ResponseEntity<?> addOptionToQuestion(
            @PathVariable Long questionId,
            @Valid @RequestBody OptionRequest optionRequest) throws ResourceNotFoundException, com.survey.exceptions.BadRequestException,ServiceLogicException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(questionService.addOptionToQuestion(questionId, optionRequest));
    }

    @DeleteMapping("question/{questionId}/option/{optionId}")
    public ResponseEntity<?> removeOptionFromQuestion(
            @PathVariable Long questionId, @PathVariable Long optionId) throws com.survey.exceptions.BadRequestException, ResourceNotFoundException,ServiceLogicException {
        return ResponseEntity.ok(questionService.removeOptionFromQuestion(questionId, optionId));
    }

    @PostMapping("question/options/dimension/{dimensionId}")
    public ResponseEntity<?> createQuestionAndOptions(
            @PathVariable Long dimensionId,
            @Valid @RequestBody QuestionRequest questionRequest) throws ResourceNotFoundException, AlreadyExistsException,ServiceLogicException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(questionService.createQuestionAndAddOptions(dimensionId, questionRequest));
    }
}
