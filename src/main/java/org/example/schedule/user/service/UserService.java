package org.example.schedule.user.service;


import jakarta.transaction.Transactional;
import org.example.schedule.common.entity.User;
import org.example.schedule.user.model.dto.UserDto;
import org.example.schedule.user.model.request.LoginRequest;
import org.example.schedule.user.model.response.UserResponse;
import org.example.schedule.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.example.schedule.common.excption.LoginException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //유저생성
    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        User savedUser = userRepository.save(user);

        return new UserDto(savedUser);
    }

    //조회
    public UserResponse getUserId(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    UserDto userDto = new UserDto(user);
    return new UserResponse(user);

    }

    public List<UserResponse> findAllUser() {
        List<User> users= userRepository.findAll();
        List<UserResponse> userResponses = users.stream()
                .map(user -> new UserResponse(user))  // User 객체를 UserResponse로 변환
                .collect(Collectors.toList());
        return userResponses;
    }

    //수정
    @Transactional
    public UserDto updateUser(Long userid, UserDto userDto) {
        User user = userRepository.findByUserId(userid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setUsername(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        userRepository.save(user);
        return new UserDto(user);
    }

    @Transactional
    public void deleteUser(Long userid, String password) {
        User user = userRepository.findByUserId(userid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 다릅니다");
        }
        if (!user.getUserId().equals(userid)) {
            throw new LoginException("본인 계정만 탈퇴 가능합니다.");
        }
        userRepository.delete(user);
    }

}
