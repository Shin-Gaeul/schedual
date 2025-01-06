package org.example.schedule.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schedule.common.entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String userName;
    private String email;
    private LocalDateTime createdAt;
    private String password;

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUsername();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.password = user.getPassword();
    }

}