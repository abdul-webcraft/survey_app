package com.survey.services;

import com.survey.exceptions.BadRequestException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Option;
import com.survey.payloads.request.OptionRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OptionService {

    ResponseEntity<?> createOption(Long questionId, OptionRequest optionRequest) throws ResourceNotFoundException, BadRequestException, ServiceLogicException;
    ResponseEntity<?> updateOption(Long id, OptionRequest optionRequest) throws ResourceNotFoundException, ServiceLogicException;
    ResponseEntity<?> deleteOption(Long id) throws ResourceNotFoundException, ServiceLogicException;
    ResponseEntity<?> getOptionsByQuestion(Long questionId) throws ResourceNotFoundException, ServiceLogicException;

    ResponseEntity<?> getOptionById(Long id) throws ResourceNotFoundException, ServiceLogicException;
    ResponseEntity<?> getAllOptions() throws ResourceNotFoundException, ServiceLogicException;


}
