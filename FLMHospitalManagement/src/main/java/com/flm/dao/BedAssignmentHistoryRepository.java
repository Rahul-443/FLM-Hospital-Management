package com.flm.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flm.model.Bed;
import com.flm.model.BedAssignmentHistory;

public interface BedAssignmentHistoryRepository extends JpaRepository<BedAssignmentHistory, Long>{
	
	public Optional<BedAssignmentHistory> findByBedAndVacatedAtIsNull(Bed bed);

}
