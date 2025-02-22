package com.flm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flm.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
