package com.flm.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDetailsDTO {
	
	private String patientId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String gender;

    private LocalDate dateOfBirth; // Stored in 'YYYY-MM-DD' format.

	private AddressDTO addressDTO;

    private List<AppointmentDTO> appointmentsList;

}
