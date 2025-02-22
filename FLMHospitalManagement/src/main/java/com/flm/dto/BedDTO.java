package com.flm.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BedDTO {

	private String bedNumber;

	private Boolean isOccupied;

	private Long roomId; // Associated room for this bed

}
