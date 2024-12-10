package com.survey.service.impl;

import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Dimension;
import com.survey.model.Survey;
import com.survey.payloads.request.DimensionRequest;
import com.survey.payloads.response.ApiResponse;
import com.survey.repository.DimensionRepository;
import com.survey.repository.SurveyRepository;
import com.survey.services.DimensionService;
import com.survey.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DimensionServiceImpl implements DimensionService {

    @Autowired
    private DimensionRepository dimensionRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyService surveyService;

    @Override
    public ResponseEntity<?> createDimension(Long surveyId, DimensionRequest dimensionRequest) throws ResourceNotFoundException, AlreadyExistsException, ServiceLogicException {
        try {
                    Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found"));
//            Survey survey = surveyService.getSurveyById(surveyId);
            if(dimensionRepository.existsByName(dimensionRequest.getName())){
                throw new AlreadyExistsException("Dimension are Already exists !!");
            }
            Dimension dimension=new Dimension();
            dimension.setName(dimensionRequest.getName());
            dimension.setSurvey(survey);
            Dimension savedDimension = dimensionRepository.save(dimension);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.name(),savedDimension ));        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> updateDimension(Long id, DimensionRequest dimensionRequest) throws ResourceNotFoundException, ServiceLogicException {
        try {
                    Dimension dimension = dimensionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dimension not found"));
//            Dimension dimension = getDimensionById(id);
            dimension.setName(dimensionRequest.getName());
            Dimension updatedDimension = dimensionRepository.save(dimension);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),updatedDimension ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> deleteDimension(Long id) throws ResourceNotFoundException, ServiceLogicException {
        try {
            Dimension dimension = dimensionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Dimension not found"));
//                Dimension dimension = getDimensionById(id);
                dimensionRepository.deleteById(dimension.getId());
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.NO_CONTENT.name(),"Dimension deleted successfully !!" ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> getDimensionsBySurvey(Long surveyId) throws ResourceNotFoundException, ServiceLogicException {
        try {
            Survey survey = surveyRepository.findById(surveyId)
                    .orElseThrow(() -> new ResourceNotFoundException("Survey not found"));
//            Survey survey = surveyService.getSurveyById(surveyId);
            List<Dimension> dimensions = dimensionRepository.findBySurveyId(survey.getId());
            if(dimensions.isEmpty()){
                throw new ResourceNotFoundException("Dimensions are not exists");
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),dimensions ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> getDimensionById(Long id) throws ResourceNotFoundException, ServiceLogicException {
        try {
            Dimension dimension = dimensionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Dimension not found"));
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),dimension ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> getAllDimension() throws ResourceNotFoundException, ServiceLogicException {
        try {
            List<Dimension> dimensions = dimensionRepository.findAll();
            if(dimensions.isEmpty()){
                throw new ResourceNotFoundException("Dimension are not exists !!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),dimensions ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }
}
