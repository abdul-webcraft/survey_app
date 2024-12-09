package com.survey.services;

import com.survey.exceptions.BadRequestException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Option;
import com.survey.payloads.request.OptionRequest;

import java.util.List;

public interface OptionService {

    Option createOption(Long questionId, OptionRequest optionRequest) throws ResourceNotFoundException, BadRequestException, ServiceLogicException;
    Option updateOption(Long id, OptionRequest optionRequest) throws ResourceNotFoundException, ServiceLogicException;
    void deleteOption(Long id) throws ResourceNotFoundException, ServiceLogicException;
    List<Option> getOptionsByQuestion(Long questionId) throws ResourceNotFoundException, ServiceLogicException;

    Option getOptionById(Long id) throws ResourceNotFoundException, ServiceLogicException;
    List<Option> getAllOptions() throws ResourceNotFoundException, ServiceLogicException;


}
