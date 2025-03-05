package com.flm.service;

import java.util.List;

import com.flm.dto.PatientDetailsDTO;
import com.flm.dto.RegisterPatientDTO;

public interface PatientService {
	
    public PatientDetailsDTO savePatient(RegisterPatientDTO patientDto);
    
    public PatientDetailsDTO updatePatient(String patientId, RegisterPatientDTO patientDto);
    
    public PatientDetailsDTO getPatientDetails(String patientId);
    
    public List<PatientDetailsDTO> getAllPatientDetails();
    
    public List<PatientDetailsDTO> getPatientsByName(String name);
    
    public void deletePatient(String patientId);

	public PatientDetailsDTO getPatientByPhone(String phoneNumber);
    
    

}
