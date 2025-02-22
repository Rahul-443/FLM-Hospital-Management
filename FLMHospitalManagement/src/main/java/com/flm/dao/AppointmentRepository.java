package com.flm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flm.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

}
