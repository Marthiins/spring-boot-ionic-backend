package com.marthiins.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marthiins.cursomc.domain.Categoria;
import com.marthiins.cursomc.dto.CategoriaDTO;
import com.marthiins.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias") //categoria é um endpoint
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET) // Para a função poder funcionar deve-se anota-la com o devido metodo;
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { //para o spring saber que esse id /{id} da minha URL para o ID da variavel colocamos a anotação @PathVable
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert (@RequestBody Categoria obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT) // Para a função poder funcionar deve-se anota-la com o devido metodo PUT fazer a alteração
    public ResponseEntity<Void> update (@RequestBody Categoria obj,@PathVariable Integer id){
      //Só para garantir que a categoria vai ser atualizada é que vou passar pela URL
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
}
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE) // Para a função poder funcionar deve-se anota-la com o devido metodo delete 
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build(); //Tem que implementar lá no Categoria Service
	}
	
	@RequestMapping(method = RequestMethod.GET) // Para a função poder retornar todas as Categorias
	public ResponseEntity<List<CategoriaDTO>> findAll() { //converter lista de categoria para CategoriaDTO, vai em categoria DTO é cria um construtor que recebe um objeto da camada de dominio
		List<Categoria> list = service.findAll();// FindAll metodo para voltar todas as Categorias
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList()); //Converter lista para outra lista Percorrer a lista usando o metodo stream / para cada obj da minha lista estou usando -> o aero function para criar uma função anonima que recebe um objeto e criar uma categoriaDto obj como argumento 
		return ResponseEntity.ok().body(listDto);
		
	}
	
}
