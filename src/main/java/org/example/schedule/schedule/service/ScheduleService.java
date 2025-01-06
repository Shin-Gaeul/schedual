package org.example.schedule.schedule.service;

import org.example.schedule.common.entity.Schedule;
import org.example.schedule.common.entity.User;
import org.example.schedule.schedule.model.dto.ScheduleDto;
import org.example.schedule.schedule.model.request.CreateRequest;
import org.example.schedule.schedule.model.request.UpdateRequest;
import org.example.schedule.schedule.model.response.ScheduleResponse;
import org.example.schedule.schedule.repository.ScheduleRepository;
import org.example.schedule.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }
    //생성
    @Transactional
    public ScheduleDto createSchedule(CreateRequest request,Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Schedule schedule = new Schedule();
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleDto(savedSchedule);
    }
    //조회
    public ScheduleResponse getSchedule(Long userId, Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));
        ScheduleDto scheduleDto = new ScheduleDto(schedule);
        return new ScheduleResponse(scheduleDto);

    }
    //수정
    @Transactional
    public ScheduleDto updateSchedule(Long userId, Long id, UpdateRequest updaterequest) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));
        if (!schedule.getUser().getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to edit this post");
        }
        schedule.setTitle(updaterequest.getTitle());
        schedule.setContent(updaterequest.getContent());

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleDto();
    }
    //삭제
    @Transactional
    public void deleteSchedule(Long userId, Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        if (!schedule.getUser().getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to edit this post");
        }

        scheduleRepository.delete(schedule);
    }

}
