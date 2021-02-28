package com.marthiins.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.marthiins.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	// Esse SimpleMailMessage esta sendo preparado no AbastractEmailService;
	void sendEmail(SimpleMailMessage msg);

	// thymeleaf add no pom.xml, enviar email tipo html;
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);

}
