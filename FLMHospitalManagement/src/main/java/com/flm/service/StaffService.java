package com.flm.service;

import java.util.List;

import com.flm.dto.RegisterStaffDTO;
import com.flm.dto.StaffDetailsDTO;

public interface StaffService {
	
	 public StaffDetailsDTO saveStaff(RegisterStaffDTO staffDto);
	 
	 public StaffDetailsDTO updateStaff(String staffId, RegisterStaffDTO staffDto);
	 
	 public StaffDetailsDTO getStaffDetails(String staffId);
	 
	 public List<StaffDetailsDTO> getAllStaffDetails();
	 
	 public List<StaffDetailsDTO> getStaffByName(String name);
	 
	 public void deleteStaff(String staffId);

}
