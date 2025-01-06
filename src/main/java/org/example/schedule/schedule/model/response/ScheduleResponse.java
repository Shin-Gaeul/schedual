package org.example.schedule.schedule.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.schedule.common.entity.Schedule;
import org.example.schedule.schedule.model.dto.ScheduleDto;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {
    private Long id;
    private Long userId;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleResponse(ScheduleDto scheduleDto) {
        this.id = scheduleDto.getId();
        this.userId = scheduleDto.getUserId();
        this.username = scheduleDto.getUsername();
        this.title = scheduleDto.getTitle();
        this.content = scheduleDto.getContent();
        this.createdAt = scheduleDto.getCreatedAt();
        this.updatedAt = scheduleDto.getUpdatedAt();
    }

}
