package com.tracker.smarttracker.service;

import com.tracker.smarttracker.dto.UserDto;
import com.tracker.smarttracker.dto.UserRecordDto;
import com.tracker.smarttracker.model.User;
import com.tracker.smarttracker.repo.UserRepository;
import com.tracker.smarttracker.util.ConverterHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ConverterHelper converterHelper;

    public List<UserDto> findAll() {
        var users = userRepository.findAll();
        return  converterHelper.convertToDtoList(users, UserDto.class);
    }

    public UserDto findByUserId(long id) {
        var user = userRepository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException("User not found in DB"));
        return converterHelper.convertToDto(user, UserDto.class);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public UserDto save(UserRecordDto userDto) {
        var user = converterHelper.convertToEntity(userDto, User.class);
        var savedUser = userRepository.save(user);
        return converterHelper.convertToDto(savedUser, UserDto.class);
    }
}
