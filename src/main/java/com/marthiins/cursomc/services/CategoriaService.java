package com.marthiins.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marthiins.cursomc.domain.Categoria;
import com.marthiins.cursomc.dto.CategoriaDTO;
import com.marthiins.cursomc.repositories.CategoriaRepository;
import com.marthiins.cursomc.services.exception.DataIntegrityException;
import com.marthiins.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService { //Classe responsavel por fazer a consulta nos repositorios
	
	@Autowired //para instanciar um repositorio no spring utilizamos a anotação @Autowired
	private CategoriaRepository repo; //declarar uma dependencia de um objeto do tipo Repository
	
	public Categoria find(Integer id) { //Operação capaz de buscar( é find) a categoria pelo codigo
		Optional<Categoria> obj = repo.findById(id); //findOne faz a busca no banco de Dados com base no Id
		/* Esse modelo é para o Sprint a partir da 2.0 */
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! ID: " + id + ", Tipo: " + Categoria.class.getName()));
	
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null); //garantir na hora de inserir um objeto novo
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId()); //Chamei esse find aqui porque ele já busca o objeto no banco e caso esse Id não exista ele me da uma excessão
		updateData(newObj, obj);
		return repo.save(newObj); //mesmo metodo de inserir, porem quando o id esta nulo ele insere, porem quando não esta ele atualiza
	}
	 
	public void delete (Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
	      throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos.");// tenho que receber essa Categoria aqui na camada do CategoriaResource
		}

	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {//paginação para as Categorias
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
	    return repo.findAll(pageRequest); //Depois Categoria Resource para o metodo end point para pegar a requisição e chamar o metodo do service
	}
	
	//Metodo auxiliar que instancia uma categoria atraves do DTO
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}

	


