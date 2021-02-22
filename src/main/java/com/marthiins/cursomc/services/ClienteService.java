package com.marthiins.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marthiins.cursomc.domain.Cliente;
import com.marthiins.cursomc.dto.ClienteDTO;
import com.marthiins.cursomc.repositories.ClienteRepository;
import com.marthiins.cursomc.services.exception.DataIntegrityException;
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
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId()); //Chamei esse find aqui porque ele já busca o objeto no banco e caso esse Id não exista ele me da uma excessão
		updateData(newObj, obj);
		return repo.save(newObj); //mesmo metodo de inserir, porem quando o id esta nulo ele insere, porem quando não esta ele atualiza
	}
	 
	public void delete (Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
	      throw new DataIntegrityException("Não é possivel excluir porque há entidades relacionadas.");// tenho que receber essa Cliente aqui na camada do ClienteResource
		}

	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {//paginação para as Clientes
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
	    return repo.findAll(pageRequest); //Depois Cliente Resource para o metodo end point para pegar a requisição e chamar o metodo do service
	}
	
	//Metodo auxiliar que instancia um cliente atraves do DTO
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	}


