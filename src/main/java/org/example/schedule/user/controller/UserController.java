package org.example.schedule.user.controller;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.schedule.user.model.dto.UserDto;
import org.example.schedule.user.model.request.LoginRequest;
import org.example.schedule.user.model.response.UserResponse;
import org.example.schedule.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        UserDto userdto = userService.createUser(userDto);

        return new ResponseEntity<>("등록되었습니다", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> getUser(@Valid @RequestBody LoginRequest request, HttpSession session) {
        UserResponse userResponse = userService.getUserId(request);
        Long userId = userResponse.getUserId();
        session.setAttribute("userId", userId);
        return new ResponseEntity<>("로그인 성공했습니다.", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>("로그아웃 성공했습니다.",HttpStatus.OK);
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> findAllUser() {

        List<UserResponse> allProfiles = userService.findAllUser();

        return new ResponseEntity<>(allProfiles, HttpStatus.OK);
    }



    @PatchMapping("/{userid}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userid") Long userid, @RequestBody UserDto userDto) {
        UserDto userdto = userService.updateUser(userid, userDto);
        return ResponseEntity.ok(userdto);
    }

    @DeleteMapping("/{userid}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userid") Long userid,String password) {
        userService.deleteUser(userid,password);
        return ResponseEntity.noContent().build();
    }

}
