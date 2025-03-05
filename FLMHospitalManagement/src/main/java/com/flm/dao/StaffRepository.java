package com.flm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flm.model.Staff;

public interface StaffRepository extends JpaRepository<Staff, String>{
	
	List<Staff> findByFirstNameContainingIgnoreCase(String name);
}
