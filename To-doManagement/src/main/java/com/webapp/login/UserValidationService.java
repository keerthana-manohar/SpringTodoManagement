package com.webapp.login;

import org.springframework.stereotype.Service;

@Service
public class UserValidationService {
	
	public boolean UserValid(String user, String Password) {
		
		if (user.equals("Keerthana") && Password .equals("abc"))
		{
			return true;
		}
				
		return false;

	}

}
