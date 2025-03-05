package com.flm.service;

import java.util.List;

import com.flm.dto.DoctorDetailsDTO;
import com.flm.dto.RegisterDoctorDTO;

public interface DoctorService {
	
	public DoctorDetailsDTO saveDoctor(RegisterDoctorDTO doctorDto);
	
	public DoctorDetailsDTO updateDoctor(String doctorId , RegisterDoctorDTO doctorDto);
	
	public DoctorDetailsDTO getDoctorDetails(String doctorId);
	
	public List<DoctorDetailsDTO> getAllDoctorsDeatils();
	
	 public List<DoctorDetailsDTO> getDoctorsByName(String name);
	 
	public void deleteDoctor(String doctorId);

}
