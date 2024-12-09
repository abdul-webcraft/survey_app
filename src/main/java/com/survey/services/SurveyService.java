package com.survey.services;

import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Survey;
import com.survey.payloads.request.SurveyRequest;

import java.util.List;

public interface SurveyService {

    Survey createSurvey(SurveyRequest surveyRequest) throws AlreadyExistsException, ServiceLogicException;
    Survey updateSurvey(Long id, SurveyRequest surveyRequest) throws ResourceNotFoundException, ServiceLogicException;
    void deleteSurvey(Long id) throws ResourceNotFoundException, ServiceLogicException;
    List<Survey> getAllSurveys() throws ResourceNotFoundException, ServiceLogicException;
    Survey getSurveyById(Long id) throws ResourceNotFoundException, ServiceLogicException;

    Survey createSurveyWithDimensionQuestionAndAddOptions(SurveyRequest surveyRequest) throws AlreadyExistsException, ServiceLogicException;
}
