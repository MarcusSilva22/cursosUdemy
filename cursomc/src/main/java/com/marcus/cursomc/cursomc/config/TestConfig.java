package com.marcus.cursomc.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.marcus.cursomc.cursomc.services.DBService;
import com.marcus.cursomc.cursomc.services.EmailService;
import com.marcus.cursomc.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantoateDatabase() throws ParseException{
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService(){
		return new MockEmailService();
	}

}
