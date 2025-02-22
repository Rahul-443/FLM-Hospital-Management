package com.flm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.flm.builder.DoctorBuilder;
import com.flm.dao.DoctorRepository;
import com.flm.dto.DoctorDetailsDTO;
import com.flm.dto.RegisterDoctorDTO;
import com.flm.exception.DoctorNotFoundException;
import com.flm.exception.DoctorServiceException;
import com.flm.model.Doctor;
import com.flm.service.DoctorService;
import com.flm.util.Constants;

@Service
public class DoctorServiceImpl implements DoctorService {
    
    @Autowired
    DoctorRepository doctorRepository;

    private final Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);

    @Override
    public DoctorDetailsDTO saveDoctor(RegisterDoctorDTO doctorDto) {
        try {
            Doctor doctor = DoctorBuilder.buildDoctorFromDTO(doctorDto);
            Doctor savedDoctor = doctorRepository.save(doctor);
            logger.info("Doctor " + Constants.CREATED, savedDoctor.getStaffId());
            return DoctorBuilder.buildDoctorDetailsDTOFromDoctor(savedDoctor);
        } catch (Exception e) {
            logger.error(Constants.ERROR+ " saving doctor: {}", e.getMessage());
            throw new DoctorServiceException(Constants.ERROR+ " saving doctor" + e.getMessage());
        }
    }

    @Override
    public DoctorDetailsDTO updateDoctor(String doctorId, RegisterDoctorDTO doctorDto) {
        try {
            Optional<Doctor> existingDoctorOpt = doctorRepository.findById(doctorId);
            if (existingDoctorOpt.isPresent()) {
                Doctor existingDoctor = existingDoctorOpt.get();
                Doctor updatedDoctor = DoctorBuilder.buildDoctorFromDTO(doctorDto);
                updatedDoctor.getUser().setPassword(existingDoctor.getUser().getPassword());
                updatedDoctor.setStaffId(existingDoctor.getStaffId());
                doctorRepository.save(updatedDoctor);
                logger.info("Doctor " + Constants.CREATED, doctorId);
                DoctorDetailsDTO doctorDetailsDTO = DoctorBuilder.buildDoctorDetailsDTOFromDoctor(updatedDoctor);
                return doctorDetailsDTO;
            } else {
                logger.warn("Doctor" + Constants.NOT_FOUND, doctorId);
                throw new DoctorNotFoundException("Doctor" + Constants.NOT_FOUND + doctorId);
            }
        } catch (Exception e) {
        	logger.error(Constants.ERROR+ " Updating doctor: {}", e.getMessage());
            throw new DoctorServiceException(Constants.ERROR+ " Updating doctor" + e.getMessage());
        }
    }

    @Override
    public DoctorDetailsDTO getDoctorDetails(String doctorId) {
        try {
            Optional<Doctor> doctor = doctorRepository.findById(doctorId);
            if (doctor.isPresent()) {
                DoctorDetailsDTO doctorDetailsDTO = DoctorBuilder.buildDoctorDetailsDTOFromDoctor(doctor.get());
                logger.info("Retrieved doctor details for ID: {}", doctorId);
                return doctorDetailsDTO;
            } else {
            	logger.warn("Doctor" + Constants.NOT_FOUND, doctorId);
                throw new DoctorNotFoundException("Doctor" + Constants.NOT_FOUND + doctorId);
            }
        } catch (Exception e) {
        	logger.error(Constants.ERROR+ " Fetching Doctor deatails with id: {}", doctorId);
            throw new DoctorServiceException(Constants.ERROR+ " Fetching Doctor deatails with id: "+ doctorId);
        }
    }

    @Override
    public List<DoctorDetailsDTO> getAllDoctorsDeatils() {
        try {
            List<Doctor> doctors = doctorRepository.findAll();
            if (doctors.isEmpty()) {
                logger.info("No doctors found");
            }
            return doctors.stream()
                    .map(DoctorBuilder::buildDoctorDetailsDTOFromDoctor)
                    .collect(Collectors.toList());
        } catch (Exception e) {
        	logger.error(Constants.ERROR+ " Fetching All doctors deatails: {}", e.getMessage());
            throw new DoctorServiceException(Constants.ERROR+ " Fetching All doctors deatails:" + e.getMessage());
        }
    }

    @Override
    public void deleteDoctor(String doctorId) {
        try {
            doctorRepository.deleteById(doctorId);
            logger.info("Doctor deleted with ID: {}", doctorId);
        } catch (EmptyResultDataAccessException e) {
        	logger.warn("Doctor" + Constants.NOT_FOUND, doctorId);
            throw new DoctorNotFoundException("Doctor" + Constants.NOT_FOUND + doctorId);
        } catch (Exception e) {
        	logger.error(Constants.ERROR+ " Deleting doctor: {}", e.getMessage());
            throw new DoctorServiceException(Constants.ERROR+ " Deleting doctor" + e.getMessage());
        }
    }
}
