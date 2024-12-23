package com.survey.controllers;

import com.survey.exceptions.BadRequestException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Option;
import com.survey.payloads.request.OptionRequest;
import com.survey.services.OptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class OptionController {

    @Autowired
    private OptionService optionService;

    @PostMapping("option/question/{questionId}")
    public ResponseEntity<?> createOption(
            @PathVariable Long questionId,
            @Valid @RequestBody OptionRequest optionRequest) throws BadRequestException, ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(optionService.createOption(questionId, optionRequest));
    }

    @PutMapping("option/{id}")
    public ResponseEntity<?> updateOption(
            @PathVariable Long id,
            @Valid @RequestBody OptionRequest optionRequest) throws ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.ok(optionService.updateOption(id, optionRequest));
    }

    @DeleteMapping("option/{id}")
    public ResponseEntity<?> deleteOption(@PathVariable Long id) throws ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.ok(optionService.deleteOption(id));
    }

    @GetMapping("options/question/{questionId}")
    public ResponseEntity<?> getOptionsByQuestion(@PathVariable Long questionId) throws ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.ok(optionService.getOptionsByQuestion(questionId));
    }

    @GetMapping("option/{id}")
    public ResponseEntity<?> getOptionById(@PathVariable Long id) throws ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.ok(optionService.getOptionById(id));
    }

    @GetMapping("options")
    public ResponseEntity<?> getAllOptions() throws ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.ok(optionService.getAllOptions());
    }
}
