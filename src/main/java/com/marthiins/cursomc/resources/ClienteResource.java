package com.marthiins.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marthiins.cursomc.domain.Cliente;
import com.marthiins.cursomc.dto.ClienteDTO;
import com.marthiins.cursomc.dto.ClienteNewDTO;
import com.marthiins.cursomc.services.ClienteService;


/* -- Controlador REST -- */
@RestController// Anotações necessárias para Rest;
@RequestMapping(value="/clientes") // Necessário para direcionar a url para esse recurso;
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)// Para a função poder funcionar deve-se anota-la com o devido metodo;
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {// Para identificar o id que o usuário digitou a anotação PathVarable direciona;
		//O ResponseEntity automaticamente já encapsula varias info http para o serviço rest;
		
		
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto){
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT) // Para a função poder funcionar deve-se anota-la com o devido metodo PUT fazer a alteração
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto,@PathVariable Integer id){
      Cliente obj = service.fromDTO(objDto);
		//Só para garantir que a cliente vai ser atualizada é que vou passar pela URL
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE) // Para a função poder funcionar deve-se anota-la com o devido metodo delete 
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build(); //Tem que implementar lá no Cliente Service
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping( method = RequestMethod.GET) // Para a função poder retornar todas as Clientes
	public ResponseEntity<List<ClienteDTO>> findAll() { //converter lista de categoria para ClienteDTO, vai em categoria DTO é cria um construtor que recebe um objeto da camada de dominio
		List<Cliente> list = service.findAll();// FindAll metodo para voltar todas as Clientes
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList()); //Converter lista para outra lista Percorrer a lista usando o metodo stream / para cada obj da minha lista estou usando -> o aero function para criar uma função anonima que recebe um objeto e criar uma categoriaDto obj como argumento 
		return ResponseEntity.ok().body(listDto);
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/page",method = RequestMethod.GET) // Para a função poder retornar todas as Clientes
	public ResponseEntity<Page<ClienteDTO>> findPage(
			 @RequestParam(value="page", defaultValue="0") Integer page,
			 @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			 @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			 @RequestParam(value="direction", defaultValue="ASC") String direction) { //converter lista de categoria para ClienteDTO, vai em categoria DTO é cria um construtor que recebe um objeto da camada de dominio
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);// FindAll metodo para voltar todas as Clientes
		Page<ClienteDTO> listDto =list.map(obj -> new ClienteDTO(obj)); //Converter lista para outra lista DTO
		return ResponseEntity.ok().body(listDto);
	}
	//como a URL já é value="/clientes" só preciso adicionar value="/picture"
	@RequestMapping(value="/picture", method=RequestMethod.POST)//AndPoint para enviar a foto de perfil do cliente
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file) {
		URI uri = service.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}
	
}
