package com.survey.service.impl;

import com.survey.Enum.QuestionType;
import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.BadRequestException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Dimension;
import com.survey.model.Option;
import com.survey.model.Question;
import com.survey.payloads.request.OptionRequest;
import com.survey.payloads.request.QuestionRequest;
import com.survey.payloads.response.ApiResponse;
import com.survey.repository.DimensionRepository;
import com.survey.repository.OptionRepository;
import com.survey.repository.QuestionRepository;
import com.survey.services.DimensionService;
import com.survey.services.OptionService;
import com.survey.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private DimensionRepository dimensionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private DimensionService dimensionService;

    @Override
    public ResponseEntity<?> createQuestion(Long dimensionId, QuestionRequest questionRequest) throws ResourceNotFoundException, AlreadyExistsException, ServiceLogicException {
        try {
                    Dimension dimension = dimensionRepository.findById(dimensionId)
                .orElseThrow(() -> new ResourceNotFoundException("Dimension not found"));
//            Dimension dimension = dimensionService.getDimensionById(dimensionId);
            if(questionRepository.existsByText(questionRequest.getText())){
                throw new AlreadyExistsException("Question are Already exist !!");
            }
            Question question=new Question();
            question.setText(questionRequest.getText());
            question.setType(questionRequest.getType());
            question.setDimension(dimension);
            Question savedQuestion = questionRepository.save(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.name(),savedQuestion ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> updateQuestion(Long id, QuestionRequest questionRequest) throws ResourceNotFoundException, ServiceLogicException {
        try {
                    Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
//            Question question = getQuestionById(id);
            question.setText(questionRequest.getText());
            question.setType(questionRequest.getType());
            Question updatedQuestion = questionRepository.save(question);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),updatedQuestion ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> deleteQuestion(Long id) throws ResourceNotFoundException, ServiceLogicException {
        try {
            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
//            Question question = getQuestionById(id);
            questionRepository.deleteById(question.getId());
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.NO_CONTENT.name(),"Question deleted successfully !!" ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> getQuestionsByDimension(Long dimensionId) throws ResourceNotFoundException, ServiceLogicException {
        try {
            Dimension dimension = dimensionRepository.findById(dimensionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Dimension not found"));
//            Dimension dimension = dimensionService.getDimensionById(dimensionId);
            List<Question> questions = questionRepository.findByDimensionId(dimension.getId());
            if(questions.isEmpty()){
                throw new ResourceNotFoundException("Question are not exists !!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),questions ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> getQuestionById(Long id) throws ResourceNotFoundException, ServiceLogicException {
        try {
            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),question ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> getAllQuestion() throws ResourceNotFoundException, ServiceLogicException {
        try {
            List<Question> questions = questionRepository.findAll();
            if(questions.isEmpty()){
                throw new ResourceNotFoundException("Question are not exists !!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),questions ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> addOptionToQuestion(Long questionId, OptionRequest optionRequest) throws ResourceNotFoundException, BadRequestException, ServiceLogicException {
        try {
                    Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
//            Question question = getQuestionById(questionId);
            if (question.getType() == QuestionType.COMMENT) {
                throw new BadRequestException("Comment type questions cannot have options");
            }
            Option option=new Option();
            option.setText(optionRequest.getText());
            option.setQuestion(question);
            Option savedOption = optionRepository.save(option);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),savedOption ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> removeOptionFromQuestion(Long questionId, Long optionId) throws ResourceNotFoundException, BadRequestException, ServiceLogicException {
        try {
            Option option = optionRepository.findById(optionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Option not found"));
            if (!option.getQuestion().getId().equals(questionId)) {
                throw new BadRequestException("Option does not belong to the specified question");
            }
            optionRepository.delete(option);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.NO_CONTENT.name(),"Option deleted successfully !!" ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<?> createQuestionAndAddOptions(Long dimensionId, QuestionRequest questionRequest) throws ResourceNotFoundException, AlreadyExistsException, ServiceLogicException {
        try {
            Dimension dimension = dimensionRepository.findById(dimensionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Dimension not found"));
//            Dimension dimension = dimensionService.getDimensionById(dimensionId);
            if(questionRepository.existsByText(questionRequest.getText())){
                throw new AlreadyExistsException("Question are Already exist !!");
            }
            // Create the question
            Question question=new Question();
            question.setText(questionRequest.getText());
            question.setType(questionRequest.getType());
            question.setDimension(dimension);

            // Save the question
            Question savedQuestion = questionRepository.save(question);

            // Create and save options if applicable
            if (questionRequest.getOptions() != null && !questionRequest.getOptions().isEmpty()) {
                List<Option> options = questionRequest.getOptions().stream().map(optionDto -> {
                    Option option = new Option();
                    option.setText(optionDto.getText());
                    option.setQuestion(savedQuestion);
                    return option;
                }).collect(Collectors.toList());
                optionRepository.saveAll(options);
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.name(),savedQuestion ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new ServiceLogicException();
        }

    }
}
