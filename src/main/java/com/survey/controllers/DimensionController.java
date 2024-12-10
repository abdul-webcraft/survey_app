package com.survey.controllers;

import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Dimension;
import com.survey.payloads.request.DimensionRequest;
import com.survey.services.DimensionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class DimensionController {

    @Autowired
    private DimensionService dimensionService;

    @PostMapping("dimension/survey/{surveyId}")
    public ResponseEntity<?> createDimension(
            @PathVariable Long surveyId,
            @Valid @RequestBody DimensionRequest dimensionRequest) throws ResourceNotFoundException, AlreadyExistsException, ServiceLogicException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dimensionService.createDimension(surveyId, dimensionRequest));
    }

    @PutMapping("dimension/{id}")
    public ResponseEntity<?> updateDimension(
            @PathVariable Long id,
            @Valid @RequestBody DimensionRequest dimensionRequest) throws ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.ok(dimensionService.updateDimension(id, dimensionRequest));
    }

    @DeleteMapping("dimension/{id}")
    public ResponseEntity<?> deleteDimension(@PathVariable Long id) throws ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.ok(dimensionService.deleteDimension(id));
    }

    @GetMapping("dimensions/survey/{surveyId}")
    public ResponseEntity<?> getDimensionsBySurvey(@PathVariable Long surveyId) throws ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.ok(dimensionService.getDimensionsBySurvey(surveyId));
    }

    @GetMapping("dimension/{id}")
    public ResponseEntity<?> getDimensionById(@PathVariable Long id) throws ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.ok(dimensionService.getDimensionById(id));
    }

    @GetMapping("dimensions")
    public ResponseEntity<?> getAllDimensions() throws ResourceNotFoundException, ServiceLogicException {
        return ResponseEntity.ok(dimensionService.getAllDimension());
    }
}
