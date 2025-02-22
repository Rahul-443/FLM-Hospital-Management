package com.flm.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
	
	private String appointmentId;
	
	private Long doctorId;

	private Long patientId;

	private LocalDate appointmentDate;

	private LocalTime startTime;

	private LocalTime endTime;
	
	private String notes;

	public AppointmentDTO(Long doctorId, Long patientId, LocalDate appointmentDate, LocalTime startTime,
			LocalTime endTime, String notes) {
		super();
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.appointmentDate = appointmentDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.notes = notes;
	}
	
}
