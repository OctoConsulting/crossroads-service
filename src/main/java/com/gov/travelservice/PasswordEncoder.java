package com.gov.travelservice;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
	
	public static void main(String args[]) {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		System.out.println("timpwd" + b.encode("timpwd"));
		System.out.println("warnerpwd" + b.encode("warnerpwd"));
		System.out.println("Lorenzopwd" + b.encode("Lorenzopwd"));
		System.out.println("millerpwd" + b.encode("millerpwd"));
		System.out.println("waldenpwd" + b.encode("waldenpwd"));
		System.out.println("" + b.encode(""));
	}

}
