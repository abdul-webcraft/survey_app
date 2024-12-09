package com.survey.services;

import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.BadRequestException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Option;
import com.survey.model.Question;
import com.survey.payloads.request.OptionRequest;
import com.survey.payloads.request.QuestionRequest;

import java.util.List;

public interface QuestionService {

    Question createQuestion(Long dimensionId, QuestionRequest questionRequest) throws ResourceNotFoundException, AlreadyExistsException, ServiceLogicException;
    Question updateQuestion(Long id, QuestionRequest questionRequest) throws ResourceNotFoundException, ServiceLogicException;
    void deleteQuestion(Long id) throws ResourceNotFoundException, ServiceLogicException;
    List<Question> getQuestionsByDimension(Long dimensionId) throws ResourceNotFoundException, ServiceLogicException;

    Question getQuestionById(Long id) throws ResourceNotFoundException, ServiceLogicException;
    List<Question> getAllQuestion() throws ResourceNotFoundException, ServiceLogicException;

    // New methods for options
    Option addOptionToQuestion(Long questionId, OptionRequest optionRequest) throws ResourceNotFoundException, BadRequestException, ServiceLogicException;
    void removeOptionFromQuestion(Long questionId, Long optionId) throws ResourceNotFoundException, com.survey.exceptions.BadRequestException, ServiceLogicException;

    Question createQuestionAndAddOptions(Long dimensionId,QuestionRequest questionRequest) throws ResourceNotFoundException, AlreadyExistsException, ServiceLogicException;

}
