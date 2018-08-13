package com.marcus.cursomc.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.marcus.cursomc.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);

}
