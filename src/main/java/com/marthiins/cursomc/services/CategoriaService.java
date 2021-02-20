package com.marthiins.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marthiins.cursomc.domain.Categoria;
import com.marthiins.cursomc.repositories.CategoriaRepository;
import com.marthiins.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService { //Classe responsavel por fazer a consulta nos repositorios
	
	@Autowired //para instanciar um repositorio no spring utilizamos a anotação @Autowired
	private CategoriaRepository repo; //declarar uma dependencia de um objeto do tipo Repository
	
	public Categoria find(Integer id) { //Operação capaz de buscar( é find) a categoria pelo codigo
		Optional<Categoria> obj = repo.findById(id); //findOne faz a busca no banco de Dados com base no Id
		/* Esse modelo é para o Sprint a partir da 2.0 */
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null); //garantir na hora de inserir um objeto novo
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId()); //Chamei esse find aqui porque ele já busca o objeto no banco e caso esse Id não exista ele me da uma excessão
		return repo.save(obj); //mesmo metodo de inserir, porem quando o id esta nulo ele insere, porem quando não esta ele atualiza
	}

}


