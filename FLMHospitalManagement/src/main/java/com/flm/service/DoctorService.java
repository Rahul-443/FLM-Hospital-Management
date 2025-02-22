package com.flm.service;

import java.util.List;

import com.flm.dto.DoctorDetailsDTO;
import com.flm.dto.RegisterDoctorDTO;

public interface DoctorService {
	
	public String saveDoctor(RegisterDoctorDTO doctorDto);
	
	public String updateDoctor(String doctorId , RegisterDoctorDTO doctorDto);
	
	public DoctorDetailsDTO getDoctorDetails(String doctorId);
	
	public List<DoctorDetailsDTO> getAllDoctorsDeatils();
	
	public String deleteDoctor(String doctorId);

}
