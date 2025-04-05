package com.flm.service;

import com.flm.dto.LoginDTO;
import com.flm.model.User;

public interface UserService {
	
	public User resetPassword(LoginDTO loginDTO);

}
