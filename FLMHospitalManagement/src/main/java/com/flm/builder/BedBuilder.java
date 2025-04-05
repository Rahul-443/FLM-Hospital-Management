package com.flm.builder;

import com.flm.dto.BedDTO;
import com.flm.model.Bed;
import com.flm.model.Room;

public class BedBuilder {

    // Convert Bed Entity to DTO
    public static BedDTO buildBedDTOFromBed(Bed bed) {
        return BedDTO.builder()
                .bedNumber(bed.getBedNumber())
                .isOccupied(bed.getIsOccupied())
                .roomNumber(bed.getRoom().getRoomNumber())
                .build();
    }

    // Convert Bed DTO to Entity
    public static Bed buildBedFromDto(BedDTO bedDTO) {
        Bed bed = Bed.builder()
                .bedNumber(bedDTO.getBedNumber())
                .isOccupied(bedDTO.getIsOccupied() != null ? bedDTO.getIsOccupied() : false)
                .build();
        return bed;
    }

    // Convert Bed DTO to Entity with Room association
    public static Bed buildBedFromDto(BedDTO bedDTO, Room room) {
        Bed bed = buildBedFromDto(bedDTO);
        bed.setRoom(room);
        return bed;
    }
}
