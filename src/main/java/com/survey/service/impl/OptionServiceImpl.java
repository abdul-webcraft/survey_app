package com.survey.service.impl;

import com.survey.Enum.QuestionType;
import com.survey.exceptions.BadRequestException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Option;
import com.survey.model.Question;
import com.survey.payloads.request.OptionRequest;
import com.survey.payloads.response.ApiResponse;
import com.survey.repository.OptionRepository;
import com.survey.repository.QuestionRepository;
import com.survey.services.OptionService;
import com.survey.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    @Override
    public ResponseEntity<?> createOption(Long questionId, OptionRequest optionRequest) throws ResourceNotFoundException, BadRequestException, ServiceLogicException {
        try {
                    Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
//            Question question = questionService.getQuestionById(questionId);
            if (question.getType() == QuestionType.COMMENT) {
                throw new BadRequestException("Cannot add options to a COMMENT type question");
            }
            Option option=new Option();
            option.setText(optionRequest.getText());
            option.setQuestion(question);
            Option savedOption = optionRepository.save(option);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.name(),savedOption ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> updateOption(Long id, OptionRequest optionRequest) throws ResourceNotFoundException, ServiceLogicException {
        try {
                    Option option = optionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found"));
//            Option option = getOptionById(id);
            option.setText(optionRequest.getText());
            Option updatedOption = optionRepository.save(option);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),updatedOption ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> deleteOption(Long id) throws ResourceNotFoundException, ServiceLogicException {
        try {
            Option option = optionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Option not found"));
//            Option option = getOptionById(id);
            optionRepository.deleteById(option.getId());
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.NO_CONTENT.name(),"Option deleted successfully !!" ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> getOptionsByQuestion(Long questionId) throws ResourceNotFoundException, ServiceLogicException {
        try {
            Question question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
//            Question question = questionService.getQuestionById(questionId);
            List<Option> options = optionRepository.findByQuestionId(question.getId());
            if(options.isEmpty()){
                throw new ResourceNotFoundException("Options sre not exists !!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),options ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> getOptionById(Long id) throws ResourceNotFoundException, ServiceLogicException {
        try {
            Option option = optionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Option not found !!"));
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),option ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> getAllOptions() throws ResourceNotFoundException, ServiceLogicException {
        try {
            List<Option> options = optionRepository.findAll();
            if (options.isEmpty()){
                throw new ResourceNotFoundException("Options are not exists !!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),options ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }
}
