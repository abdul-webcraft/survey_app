package com.survey.services;

import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.BadRequestException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Option;
import com.survey.model.Question;
import com.survey.payloads.request.OptionRequest;
import com.survey.payloads.request.QuestionRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {

    ResponseEntity<?> createQuestion(Long dimensionId, QuestionRequest questionRequest) throws ResourceNotFoundException, AlreadyExistsException, ServiceLogicException;
    ResponseEntity<?> updateQuestion(Long id, QuestionRequest questionRequest) throws ResourceNotFoundException, ServiceLogicException;
    ResponseEntity<?> deleteQuestion(Long id) throws ResourceNotFoundException, ServiceLogicException;
    ResponseEntity<?> getQuestionsByDimension(Long dimensionId) throws ResourceNotFoundException, ServiceLogicException;

    ResponseEntity<?> getQuestionById(Long id) throws ResourceNotFoundException, ServiceLogicException;
    ResponseEntity<?> getAllQuestion() throws ResourceNotFoundException, ServiceLogicException;

    // New methods for options
    ResponseEntity<?> addOptionToQuestion(Long questionId, OptionRequest optionRequest) throws ResourceNotFoundException, BadRequestException, ServiceLogicException;
    ResponseEntity<?> removeOptionFromQuestion(Long questionId, Long optionId) throws ResourceNotFoundException, com.survey.exceptions.BadRequestException, ServiceLogicException;

    ResponseEntity<?> createQuestionAndAddOptions(Long dimensionId,QuestionRequest questionRequest) throws ResourceNotFoundException, AlreadyExistsException, ServiceLogicException;

}
