package com.flm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flm.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, String>{

}
