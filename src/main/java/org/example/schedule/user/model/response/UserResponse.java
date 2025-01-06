package org.example.schedule.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.schedule.common.entity.User;
import org.example.schedule.user.model.dto.UserDto;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long userId;
    private String userName;
    private String email;
    private LocalDateTime createdAt;

    public UserResponse(User user) {
        this.userId=user.getUserId();
        this.userName=user.getUsername();
        this.email=user.getEmail();
        this.createdAt=user.getCreatedAt();
    }
}
