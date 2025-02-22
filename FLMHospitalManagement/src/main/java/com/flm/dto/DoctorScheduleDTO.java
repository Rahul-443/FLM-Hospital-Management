package com.flm.dto;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorScheduleDTO {
	
	private String doctorId;

	private String dayOfWeek; 
	
	private LocalTime startTime; 

	private LocalTime endTime; 

	private boolean isAvailable;

}
