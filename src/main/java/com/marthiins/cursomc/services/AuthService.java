package com.marthiins.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.marthiins.cursomc.domain.Cliente;
import com.marthiins.cursomc.repositories.ClienteRepository;
import com.marthiins.cursomc.services.exception.ObjectNotFoundException;

@Service // Componente do framework
public class AuthService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	BCryptPasswordEncoder pe;

	@Autowired
	EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encotrado");
		}

		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		clienteRepository.save(cliente);// clienteRepository.save para salvar ele no banco de dados
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
//Para gerar os caracteres unicode https://unicode-table.com/pt/
		int opt = rand.nextInt(3);//o opt é para escolher digito entre letra Maiscula e minuscula  
		//rand.nextInt Gera um numero inteiro de 0 ate 2
		if (opt == 0) {// Gera um digito;
			return (char) (rand.nextInt(10) + 48);//+ 48 é o código do zero na tabela unicode
		} else if (opt == 1) {// Gera letra maiúscula;
			return (char) (rand.nextInt(26) + 65);// São 26 letras possiveis e + 65 é o código da primeira Maiuscula Letra tabela unicode
		} else {// Gera letra minuscula;
			return (char) (rand.nextInt(26) + 97);// São 26 letras possiveis e + 97 é o código da primeira Letra Minuscula tabela unicode
		}
	}
}
