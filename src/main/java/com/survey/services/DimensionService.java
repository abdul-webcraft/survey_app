package com.survey.services;

import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Dimension;
import com.survey.payloads.request.DimensionRequest;

import java.util.List;

public interface DimensionService {

    Dimension createDimension(Long surveyId, DimensionRequest dimensionRequest) throws ResourceNotFoundException, AlreadyExistsException, ServiceLogicException;
    Dimension updateDimension(Long id, DimensionRequest dimensionRequest) throws ResourceNotFoundException, ServiceLogicException;
    void deleteDimension(Long id) throws ResourceNotFoundException, ServiceLogicException;
    List<Dimension> getDimensionsBySurvey(Long surveyId) throws ResourceNotFoundException, ServiceLogicException;

    Dimension getDimensionById(Long id) throws ResourceNotFoundException, ServiceLogicException;
    List<Dimension> getAllDimension() throws ResourceNotFoundException, ServiceLogicException;


}
