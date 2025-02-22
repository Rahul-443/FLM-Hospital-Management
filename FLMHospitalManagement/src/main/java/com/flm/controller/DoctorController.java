package com.flm.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flm.dto.RegisterDoctorDTO;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
	
	@PostMapping
	public void registerDoctor(@RequestBody RegisterDoctorDTO doctorDto) {
		
		
		
	}
	
	@PutMapping("/{id}")
	public void updateDoctor(@PathVariable(name = "id") String doctorId , @RequestBody RegisterDoctorDTO doctorDto) {
		
		
		
	}
	
	@GetMapping
	public void getDoctorDetails(@RequestParam(name = "id") String doctorId) {
		
		
		
	}
	
	@GetMapping
	public void getAllDcotors() {
		
		
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteDcotor(@PathVariable(name = "id") String doctorId) {
		
		
		
	}

}
