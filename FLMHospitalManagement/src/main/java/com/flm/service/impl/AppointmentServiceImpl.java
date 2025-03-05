package com.flm.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flm.builder.AppointmentBuilder;
import com.flm.dao.AppointmentRepository;
import com.flm.dao.DoctorRepository;
import com.flm.dao.PatientRepository;
import com.flm.dto.AppointmentDTO;
import com.flm.exception.AppointmentAlreadyExistsException;
import com.flm.exception.AppointmentNotFoundException;
import com.flm.exception.DoctorNotFoundException;
import com.flm.exception.InvalidDateException;
import com.flm.exception.PatientNotFoundException;
import com.flm.model.Appointment;
import com.flm.model.Doctor;
import com.flm.model.Patient;
import com.flm.service.AppointmentService;
import com.flm.util.Constants;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
    }

    @Transactional
    public AppointmentDTO bookAppointment(AppointmentDTO appointmentDTO) {
        logger.info("Booking appointment: {}", appointmentDTO);

        validateDate(appointmentDTO.getAppointmentDate());

        validateTime(appointmentDTO.getStartTime(), appointmentDTO.getEndTime());

        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor " + Constants.NOT_FOUND + appointmentDTO.getDoctorId()));
       

        if (!doctor.getIsEmployeeActive()) {  
            throw new DoctorNotFoundException("Doctor is no longer active and cannot take appointments.");
        }
        
        Patient patient = patientRepository.findById(appointmentDTO.getPatientId())
        		.orElseThrow(() -> new PatientNotFoundException("Patient " + Constants.NOT_FOUND + appointmentDTO.getPatientId()));
        		

        int count = appointmentRepository.countConflictingAppointments(
                doctor.getStaffId(),patient.getPatientId(), appointmentDTO.getAppointmentDate(), appointmentDTO.getStartTime(), appointmentDTO.getEndTime());

        if (count>0) {
            throw new AppointmentAlreadyExistsException(Constants.APPOINTMENT_EXISTS);
        }

        Appointment appointment = AppointmentBuilder.buildAppointmentFromDTO(appointmentDTO, doctor, patient, "SCHEDULED");
       
        Appointment savedAppointemnt = appointmentRepository.save(appointment);
        
        AppointmentDTO dto = AppointmentBuilder.buildDTOFromAppointment(savedAppointemnt);

        logger.info("Successfully booked appointment for patient {} with doctor {} on {} from {} to {}",
                appointmentDTO.getPatientId(), appointmentDTO.getDoctorId(), 
                appointmentDTO.getAppointmentDate(), appointmentDTO.getStartTime(), appointmentDTO.getEndTime());
        return dto;
    }
    
    @Transactional
    public void cancelAppointment(String appointmentId) {
        logger.info("Cancelling appointment with ID: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment "+ Constants.NOT_FOUND  + appointmentId));

        // Set status to CANCELLED instead of deleting
        appointment.setStatus("CANCELLED");
        appointmentRepository.save(appointment);

        logger.info("Appointment cancelled successfully with ID: {}", appointmentId);
    }
    
    private void validateDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        if (date.isBefore(today)) {
            throw new InvalidDateException("Appointment date must be today or later.");
        }
    }
    

    private void validateTime(LocalTime startTime, LocalTime endTime) {
        LocalTime startLimit = LocalTime.of(10, 0);
        LocalTime endLimit = LocalTime.of(17, 0);
        if(startTime.isAfter(endTime)) {
        	throw new InvalidDateException("End time cannot be before start time");
        }else if(startTime.equals(endTime)) {
        	throw new InvalidDateException("Start time and End Time can not be same");
        }
        else if (startTime.isBefore(startLimit) || endTime.isAfter(endLimit)) {
            throw new InvalidDateException("Appointment time must be between 10:00 AM and 5:00 PM.");
        }
    }
}
