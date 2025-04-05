package com.flm.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flm.dto.LoginDTO;
import com.flm.exception.InvalidCredentialsException;
import com.flm.exception.PasswordDoesntExistException;
import com.flm.model.User;
import com.flm.security.JwtService;
import com.flm.service.StaffService;
import com.flm.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    StaffService staffService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
    	 try {
    	        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUserEmail());
    	        
    	        if (passwordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())) {
    	            Map<String, Object> extraClaims = new HashMap<>();
    	            extraClaims.put("roles", userDetails.getAuthorities().stream()
    	                    .map(GrantedAuthority::getAuthority)
    	                    .collect(Collectors.toList()));

    	            String token = jwtUtil.generateToken(extraClaims, loginDTO.getUserEmail());
    	            return ResponseEntity.ok(token);
    	        } else {
    	            throw new InvalidCredentialsException("Invalid credentials");
    	        }

    	    } catch (UsernameNotFoundException ex) {
    	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
    	    } catch (InvalidCredentialsException | PasswordDoesntExistException ex) {
    	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    	    }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody LoginDTO loginDTO) {
    	
    	 User user = userService.resetPassword(loginDTO);
    	 return new ResponseEntity<String>(user.getStaff().getStaffId(),HttpStatus.OK);
    }
    
    
}
