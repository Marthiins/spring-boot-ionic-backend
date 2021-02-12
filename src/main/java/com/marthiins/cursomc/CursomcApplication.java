package com.marthiins.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marthiins.cursomc.domain.Categoria;
import com.marthiins.cursomc.repositories.CategoriaRepository;


@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository; //Categoria repository vai ser o objeto responsavel por salvar as categoria no Banco de Dados
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}/* Implemenar a CommandLineRunner para puxar a função run abaixo */

	@Override
	public void run(String... args) throws Exception {
	
		Categoria cat1 = new Categoria (null, "Informática");
		Categoria cat2 = new Categoria (null, "Escritório");
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2)); //Criar LIsta automatica

	}

}
