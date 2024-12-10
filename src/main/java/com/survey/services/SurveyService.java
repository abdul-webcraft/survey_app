package com.survey.services;

import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Survey;
import com.survey.payloads.request.SurveyRequest;
import com.survey.payloads.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SurveyService {

    ResponseEntity<ApiResponse<?>> createSurvey(SurveyRequest surveyRequest) throws AlreadyExistsException, ServiceLogicException;
     ResponseEntity<ApiResponse<?>> updateSurvey(Long id, SurveyRequest surveyRequest) throws ResourceNotFoundException, ServiceLogicException;
    ResponseEntity<ApiResponse<?>> deleteSurvey(Long id) throws ResourceNotFoundException, ServiceLogicException;
    ResponseEntity<ApiResponse<?>> getAllSurveys() throws ResourceNotFoundException, ServiceLogicException;
     ResponseEntity<ApiResponse<?>> getSurveyById(Long id) throws ResourceNotFoundException, ServiceLogicException;

     ResponseEntity<ApiResponse<?>> createSurveyWithDimensionQuestionAndAddOptions(SurveyRequest surveyRequest) throws AlreadyExistsException, ServiceLogicException;
}
