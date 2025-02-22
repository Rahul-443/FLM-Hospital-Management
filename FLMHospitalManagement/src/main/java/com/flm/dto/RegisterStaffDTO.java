package com.flm.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterStaffDTO {

	private String firstName;

	private String lastName;

	private String phoneNumber;

	private String email;

	private AddressDTO addressDTO;

	private LocalDate dateOfJoining;
	
	private double experienceInYears;

	private Long departmentId;

}
