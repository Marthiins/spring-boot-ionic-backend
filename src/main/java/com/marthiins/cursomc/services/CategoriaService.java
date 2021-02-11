package com.marthiins.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marthiins.cursomc.domain.Categoria;
import com.marthiins.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired //para instanciar um repositorio no spring utilizamos a anotação @Autowired
	private CategoriaRepository repo; //declarar uma dependencia de um objeto do tipo Repository
	
	public Categoria find(Integer id) { //Operação capaz de buscar a categoria pelo codigo
		Optional<Categoria> obj = repo.findById(id); //findOne faz a busca no banco de Dados com base no Id
	    return obj.orElse(null);
	    

	}

}
