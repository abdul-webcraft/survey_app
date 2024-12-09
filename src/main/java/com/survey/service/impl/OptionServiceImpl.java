package com.survey.service.impl;

import com.survey.Enum.QuestionType;
import com.survey.exceptions.BadRequestException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Option;
import com.survey.model.Question;
import com.survey.payloads.request.OptionRequest;
import com.survey.repository.OptionRepository;
import com.survey.repository.QuestionRepository;
import com.survey.services.OptionService;
import com.survey.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Option createOption(Long questionId, OptionRequest optionRequest) throws ResourceNotFoundException, BadRequestException, ServiceLogicException {
        try {
            //        Question question = questionRepository.findById(questionId)
//                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
            Question question = questionService.getQuestionById(questionId);
            if (question.getType() == QuestionType.COMMENT) {
                throw new BadRequestException("Cannot add options to a COMMENT type question");
            }
            Option option=new Option();
            option.setText(optionRequest.getText());
            option.setQuestion(question);
            return optionRepository.save(option);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public Option updateOption(Long id, OptionRequest optionRequest) throws ResourceNotFoundException, ServiceLogicException {
        try {
            //        Option existing = optionRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Option not found"));
            Option option = getOptionById(id);
            option.setText(optionRequest.getText());
            return optionRepository.save(option);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public void deleteOption(Long id) throws ResourceNotFoundException, ServiceLogicException {
        try {
            //        if (!optionRepository.existsById(id)) {
//            throw new ResourceNotFoundException("Option not found");
//        }
            Option option = getOptionById(id);
            optionRepository.deleteById(option.getId());
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public List<Option> getOptionsByQuestion(Long questionId) throws ResourceNotFoundException, ServiceLogicException {
        try {
            Question question = questionService.getQuestionById(questionId);
            List<Option> options = optionRepository.findByQuestionId(question.getId());
            if(options.isEmpty()){
                throw new ResourceNotFoundException("Options sre not exists !!");
            }
            return options;
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public Option getOptionById(Long id) throws ResourceNotFoundException, ServiceLogicException {
        try {
            return optionRepository.findById(id)
                    .orElseThrow(()->new ResourceNotFoundException("Option not found !!"));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public List<Option> getAllOptions() throws ResourceNotFoundException, ServiceLogicException {
        try {
            List<Option> options = optionRepository.findAll();
            if (options.isEmpty()){
                throw new ResourceNotFoundException("Options are not exists !!");
            }
            return options;
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }
}
