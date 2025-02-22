package com.flm.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
	
private String roomNumber; // Unique room number (e.g., A101)
    
    private String roomType; // E.g., ICU, General, Private, etc.
    
    private Integer capacity; // Number of beds in the room

    private List<BedDTO> bedsList; 

}
