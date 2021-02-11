package com.marthiins.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marthiins.cursomc.domain.Categoria;
import com.marthiins.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias") //categoria é um endpoint
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) { //para o spring saber que esse id /{id} da minha URL para o ID da variavel colocamos a anotação @PathVable
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	
}
