package org.example.schedule.schedule.controller;


import jakarta.servlet.http.HttpSession;
import org.example.schedule.schedule.model.dto.ScheduleDto;
import org.example.schedule.schedule.model.request.CreateRequest;
import org.example.schedule.schedule.model.request.UpdateRequest;
import org.example.schedule.schedule.model.response.ScheduleResponse;
import org.example.schedule.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    private HttpSession session;

    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody CreateRequest createRequest, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        ScheduleDto createSchedule = scheduleService.createSchedule(createRequest, userId);

        return ResponseEntity.ok(createSchedule);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> getSchedule(@PathVariable Long id,HttpSession session ) {
        Long userId = (Long) session.getAttribute("userId");
        ScheduleResponse scheduleResponse = scheduleService.getSchedule(id, userId);
        return ResponseEntity.ok(scheduleResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable Long id, @RequestBody HttpSession session, UpdateRequest updateRequest) {
        Long userId = (Long) session.getAttribute("userId");
        ScheduleDto updateSchedule = scheduleService.updateSchedule(id,userId,updateRequest);
        return ResponseEntity.ok(updateSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id,  HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        scheduleService.deleteSchedule(id, userId);
        return ResponseEntity.noContent().build();
    }


}
