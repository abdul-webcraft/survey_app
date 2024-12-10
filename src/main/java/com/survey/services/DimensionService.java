package com.survey.services;

import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Dimension;
import com.survey.payloads.request.DimensionRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DimensionService {

    ResponseEntity<?> createDimension(Long surveyId, DimensionRequest dimensionRequest) throws ResourceNotFoundException, AlreadyExistsException, ServiceLogicException;
    ResponseEntity<?> updateDimension(Long id, DimensionRequest dimensionRequest) throws ResourceNotFoundException, ServiceLogicException;
    ResponseEntity<?> deleteDimension(Long id) throws ResourceNotFoundException, ServiceLogicException;
    ResponseEntity<?> getDimensionsBySurvey(Long surveyId) throws ResourceNotFoundException, ServiceLogicException;

    ResponseEntity<?> getDimensionById(Long id) throws ResourceNotFoundException, ServiceLogicException;
    ResponseEntity<?> getAllDimension() throws ResourceNotFoundException, ServiceLogicException;


}
