package com.survey.service.impl;

import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Dimension;
import com.survey.model.Option;
import com.survey.model.Question;
import com.survey.model.Survey;
import com.survey.payloads.request.SurveyRequest;
import com.survey.repository.DimensionRepository;
import com.survey.repository.OptionRepository;
import com.survey.repository.QuestionRepository;
import com.survey.repository.SurveyRepository;
import com.survey.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private DimensionRepository dimensionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Override
    public Survey createSurvey(SurveyRequest surveyRequest) throws AlreadyExistsException, ServiceLogicException {
        try {
            if(surveyRepository.existsByName(surveyRequest.getName())){
                throw new AlreadyExistsException("Survey name are Already Exists !!");
            }
            Survey survey=new Survey();
            survey.setName(surveyRequest.getName());
            return surveyRepository.save(survey);
        } catch (AlreadyExistsException e) {
            throw new AlreadyExistsException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceLogicException();
        }
    }

    @Override
    public Survey updateSurvey(Long id, SurveyRequest surveyRequest) throws ResourceNotFoundException, ServiceLogicException {
        try {
            // Survey existing = surveyRepository.findById(id)
            // .orElseThrow(() -> new ResourceNotFoundException("Survey not found"));
            Survey survey = getSurveyById(id);
            survey.setName(surveyRequest.getName());
            return surveyRepository.save(survey);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }
    }

    @Override
    public void deleteSurvey(Long id) throws ResourceNotFoundException, ServiceLogicException {
        try {
            //        Survey survey = surveyRepository.findById(id)
            //                .orElseThrow(() -> new ResourceNotFoundException("Survey not found"));
            Survey survey = getSurveyById(id);
            surveyRepository.deleteById(survey.getId());
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }
    }

    @Override
    public List<Survey> getAllSurveys() throws ResourceNotFoundException, ServiceLogicException {

        try {
            List<Survey> surveys = surveyRepository.findAll();
            if(surveys.isEmpty()){
                throw new ResourceNotFoundException("Survey are not Exists");
            }
            return surveys;
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }
    }

    @Override
    public Survey getSurveyById(Long id) throws ResourceNotFoundException, ServiceLogicException {

        try {
            return surveyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Survey not found"));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }
    }

    @Override
    public Survey createSurveyWithDimensionQuestionAndAddOptions(SurveyRequest surveyRequest) throws AlreadyExistsException, ServiceLogicException {
        try {
            if(surveyRepository.existsByName(surveyRequest.getName())){
                throw new AlreadyExistsException("Survey name are Already Exists !!");
            }
            Survey survey=new Survey();
            survey.setName(surveyRequest.getName());
            Survey savedSurvey = surveyRepository.save(survey);

            if (surveyRequest.getDimensions() != null && !surveyRequest.getDimensions().isEmpty()) {
                List<Dimension> dimensions = surveyRequest.getDimensions().stream().map(dimensionDto -> {
                    Dimension dimension = new Dimension();
                    dimension.setName(dimensionDto.getName());
                    dimension.setSurvey(savedSurvey);
                    Dimension savedDimension = dimensionRepository.save(dimension);
                    if (dimensionDto.getQuestions()!= null && !dimensionDto.getQuestions().isEmpty()) {
                        List<Question> questions = dimensionDto.getQuestions().stream().map(questionDto -> {
                            Question question = new Question();
                            question.setText(questionDto.getText());
                            question.setType(questionDto.getType());
                            question.setDimension(savedDimension);
                            Question savedQuestion = questionRepository.save(question);
                            if (questionDto.getOptions()!= null && !questionDto.getOptions().isEmpty()) {
                                List<Option> options = questionDto.getOptions().stream().map(optionDto -> {
                                    Option option = new Option();
                                    option.setText(optionDto.getText());
                                    option.setQuestion(savedQuestion);
                                    Option savedOption = optionRepository.save(option);
                                    return option;
                                }).toList();
                            }
                            return question;
                        }).toList();
                    }
                    return savedDimension;
                }).toList();
            }
            return savedSurvey;
        }catch (AlreadyExistsException e){
            throw new AlreadyExistsException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }
    }

}
