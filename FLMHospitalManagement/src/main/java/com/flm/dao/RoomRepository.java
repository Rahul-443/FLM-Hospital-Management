package com.flm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flm.model.Room;

public interface RoomRepository extends JpaRepository<Room, String>{

}
