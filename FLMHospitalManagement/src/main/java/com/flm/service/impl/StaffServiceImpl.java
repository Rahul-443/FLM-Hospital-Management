package com.flm.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.flm.builder.StaffBuilder;
import com.flm.dao.StaffRepository;
import com.flm.dto.RegisterStaffDTO;
import com.flm.dto.StaffDetailsDTO;
import com.flm.exception.StaffNotFoundException;
import com.flm.exception.StaffServiceException;
import com.flm.model.Staff;
import com.flm.service.StaffService;
import com.flm.util.Constants;

@Service
public class StaffServiceImpl implements StaffService {
    
    private final StaffRepository staffRepository;
    
    private final Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public StaffDetailsDTO saveStaff(RegisterStaffDTO staffDto) {
        try {
            Staff staff = StaffBuilder.buildStaffFromDTO(staffDto);
            Staff savedStaff = staffRepository.save(staff);
            logger.info("Staff " + Constants.CREATED, savedStaff.getStaffId());
            return StaffBuilder.buildStaffDetailsDTOFromStaff(savedStaff);
        } catch (Exception e) {
            logger.error("{} saving staff: {}", Constants.ERROR, e.getMessage());
            throw new StaffServiceException(Constants.ERROR + " saving staff: " + e.getMessage());
        }
    }

    @Override
    public StaffDetailsDTO updateStaff(String staffId, RegisterStaffDTO staffDto) {
        try {
            Staff existingStaff = staffRepository.findById(staffId)
                .orElseThrow(() -> new StaffNotFoundException("Staff " + Constants.NOT_FOUND + staffId));

            logger.info(Constants.RETRIEVED, "Staff", staffId);

            Staff updatedStaff = StaffBuilder.buildStaffFromDTO(staffDto);

            // Preserve essential details from existing staff
            updatedStaff.getUser().setUserId(existingStaff.getUser().getUserId());
            updatedStaff.getUser().setPassword(existingStaff.getUser().getPassword());
            updatedStaff.setStaffId(existingStaff.getStaffId());
            updatedStaff.getAddress().setAddressId(existingStaff.getAddress().getAddressId());

            staffRepository.save(updatedStaff);
            logger.info("Staff " + Constants.UPDATED, staffId);

            return StaffBuilder.buildStaffDetailsDTOFromStaff(updatedStaff);

        } catch (DataAccessException e) {
            logger.error("{} updating staff with ID: {} - Exception: {}", Constants.ERROR, staffId, e.getMessage());
            throw new StaffServiceException(Constants.ERROR + " updating staff with ID: " + staffId);
        } catch (StaffNotFoundException e) {
            logger.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("{} updating staff with ID: {} - Exception - {}", Constants.ERROR, staffId, e);
            throw new StaffServiceException(Constants.ERROR + " updating staff with ID: " + staffId);
        }
    }

    @Override
    public StaffDetailsDTO getStaffDetails(String staffId) {
        try {
            Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new StaffNotFoundException("Staff " + Constants.NOT_FOUND + staffId));

            logger.info(Constants.RETRIEVED, "Staff", staffId);
            return StaffBuilder.buildStaffDetailsDTOFromStaff(staff);
            
        } catch (DataAccessException e) {
            logger.error("{} fetching staff details with ID: {} - Exception: {}", Constants.ERROR, staffId, e.getMessage());
            throw new StaffServiceException(Constants.ERROR + " fetching staff details with ID: " + staffId);
        } catch (StaffNotFoundException e) {
            logger.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("{} fetching staff details with ID: {} - Exception - {}", Constants.ERROR, staffId, e);
            throw new StaffServiceException(Constants.ERROR + " fetching staff details with ID: " + staffId);
        }
    }

    @Override
    public List<StaffDetailsDTO> getAllStaffDetails() {
        try {
            List<Staff> staffList = staffRepository.findAll();
            if (staffList.isEmpty()) {
                logger.info("No staff members found");
            }
            return staffList.stream()
                    .map(StaffBuilder::buildStaffDetailsDTOFromStaff)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("{} Fetching all staff details: {}", Constants.ERROR, e.getMessage());
            throw new StaffServiceException(Constants.ERROR + " Fetching all staff details: " + e.getMessage());
        }
    }
    
    @Override
    public List<StaffDetailsDTO> getStaffByName(String name) {
        try {
            List<Staff> staffList = staffRepository.findByFirstNameContainingIgnoreCase(name);
            if (staffList.isEmpty()) {
                logger.warn("No staff found with name containing: {}", name);
                throw new StaffNotFoundException("No staff found with name: " + name);
            }

            logger.info("Retrieved {} staff members with name containing: {}", staffList.size(), name);
            return staffList.stream()
                          .map(StaffBuilder::buildStaffDetailsDTOFromStaff)
                          .collect(Collectors.toList());

        } catch (StaffNotFoundException e) {
            logger.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("{} retrieving staff with name: {} - exception - {}", Constants.ERROR, name, e);
            throw new StaffServiceException(Constants.ERROR + " retrieving staff with name: " + name);
        }
    }

    @Override
    public void deleteStaff(String staffId) {
        try {
            if (!staffRepository.existsById(staffId)) {
                logger.warn("Staff {} not found", staffId);
                throw new StaffNotFoundException("Staff " + Constants.NOT_FOUND + staffId);
            }

            staffRepository.deleteById(staffId);
            logger.info("Staff deleted successfully with ID: {}", staffId);

        } catch (DataAccessException e) {
            logger.error("{} deleting staff with ID: {} - Exception: {}", Constants.ERROR, staffId, e.getMessage());
            throw new StaffServiceException(Constants.ERROR + " deleting staff with ID: " + staffId);
        } catch (StaffNotFoundException e) {
            logger.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("{} deleting staff with ID: {} - exception - {}", Constants.ERROR, staffId, e);
            throw new StaffServiceException(Constants.ERROR + " deleting staff with ID: " + staffId);
        }
    }
}
