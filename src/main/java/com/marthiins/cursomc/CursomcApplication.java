package com.marthiins.cursomc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marthiins.cursomc.services.S3Service;


@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private S3Service s3Service;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}/* Implemenar a CommandLineRunner para puxar a função run abaixo */

	@Override
	public void run(String... args) throws Exception {
	
		s3Service.uploadFile("C:\\Users\\usuario 1\\Desktop\\Sistema Loja Online\\fotos\\patricia-souza.jpg");
		
	}

}
