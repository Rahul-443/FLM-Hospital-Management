package com.flm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flm.dto.AppointmentDTO;
import com.flm.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/book-appointment")
    public ResponseEntity<AppointmentDTO> bookAppointment(@RequestBody AppointmentDTO appointmentDTO) {

    	AppointmentDTO appointment = appointmentService.bookAppointment(appointmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

}
