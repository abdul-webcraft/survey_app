package com.survey.service.impl;

import com.survey.config.AppConstants;
import com.survey.exceptions.AlreadyExistsException;
import com.survey.exceptions.ResourceNotFoundException;
import com.survey.exceptions.ServiceLogicException;
import com.survey.model.Role;
import com.survey.model.User;
import com.survey.payloads.request.UserRequest;
import com.survey.payloads.response.ApiResponse;
import com.survey.repository.RoleRepository;
import com.survey.repository.UserRepository;
import com.survey.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> registerUser(UserRequest userRequest) throws AlreadyExistsException, ServiceLogicException {
        try{
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new AlreadyExistsException("Email already Exists  !!");
        }
        User user=new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setMobileNo(userRequest.getMobileNo());
        user.setAddress(userRequest.getAddress());

        Role role = roleRepository.findById(AppConstants.ADMIN_USER).get();
        user.getRoles().add(role);

        // Save user and map to response DTO
        User savedUser = userRepository.save(user);
        ApiResponse<?> response=new ApiResponse<>(HttpStatus.CREATED.name(), savedUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }catch (AlreadyExistsException ex) {
        throw new AlreadyExistsException(ex.getMessage());
    }catch (Exception ex) {
        throw new ServiceLogicException();
    }
    }

    @Override
    public User getUserByUsername(String username) throws ResourceNotFoundException, ServiceLogicException {
        try {
            return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("Username are not exists !!"));
        }catch (ResourceNotFoundException ex){
            throw new ResourceNotFoundException(ex.getMessage());
        }catch(Exception e) {
            throw new ServiceLogicException();
        }
    }
}
