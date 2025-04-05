package com.flm.service;

import java.util.List;

import com.flm.dto.BedDTO;
import com.flm.dto.RoomDTO;

public interface RoomService {
	
	public RoomDTO addRoom(RoomDTO roomDTO);
	
	public BedDTO addBedToRoom(String roomId, BedDTO bedDTO);
	
	public void assignBedToPatient(String bedId, String patientId);
	
	public void vacateBed(String bedNumber);
	
	public List<BedDTO> getAvailableBedsInRoom(String roomNumber);
	
	public List<BedDTO> getAllAvailableBeds();
	
	public BedDTO getBedDetailsByPatientId(String patientId);

	public RoomDTO updateRoom(String roomNumber, RoomDTO updatedRoomDTO);

	public void deleteRoom(String roomNumber);

	public BedDTO updateBed(String bedNumber, BedDTO updatedBedDTO);

	public void deleteBed(String bedNumber);


}
