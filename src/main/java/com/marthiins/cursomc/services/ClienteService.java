package com.marthiins.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marthiins.cursomc.domain.Cliente;
import com.marthiins.cursomc.repositories.ClienteRepository;
import com.marthiins.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService { //Classe responsavel por fazer a consulta nos repositorios
	
	@Autowired //para instanciar um repositorio no spring utilizamos a anotação @Autowired
	private ClienteRepository repo; //declarar uma dependencia de um objeto do tipo Repository
	
	public Cliente find(Integer id) { //Operação capaz de buscar a categoria pelo codigo
		Optional<Cliente> obj = repo.findById(id); //findOne faz a busca no banco de Dados com base no Id
		/* Esse modelo é para o Sprint a partir da 2.0 */
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	
	}


	}


