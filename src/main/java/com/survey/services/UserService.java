package com.survey.services;

import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.User;
import com.survey.payloads.request.UserRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
//
//    ResponseEntity<?> createUser(UserRequest userRequest)throws AlreadyExistsException, ServiceLogicException;
//    ResponseEntity<?> updateUser(Long id,UserRequest userRequest)throws ResourceNotFoundException, ServiceLogicException;
//    ResponseEntity<?> getUserById(Long id)throws ResourceNotFoundException, ServiceLogicException;
//    ResponseEntity<?> getAllUser()throws ResourceNotFoundException, ServiceLogicException;
//    ResponseEntity<?> deleteUser(Long id)throws ResourceNotFoundException, ServiceLogicException;
    ResponseEntity<?> registerUser(UserRequest userRequest)throws AlreadyExistsException, ServiceLogicException;
    User getUserByUsername(String username)throws ResourceNotFoundException,ServiceLogicException;
}
