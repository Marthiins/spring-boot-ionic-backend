package com.marthiins.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.marthiins.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}
