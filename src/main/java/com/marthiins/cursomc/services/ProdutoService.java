package com.marthiins.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marthiins.cursomc.domain.Categoria;
import com.marthiins.cursomc.domain.Produto;
import com.marthiins.cursomc.repositories.CategoriaRepository;
import com.marthiins.cursomc.repositories.ProdutoRepository;
import com.marthiins.cursomc.services.exception.ObjectNotFoundException;


//OK REVISADO


@Service
public class ProdutoService { // Classe responsavel por fazer a consulta nos repositorios

	@Autowired // para instanciar um repositorio no spring utilizamos a anotação @Autowired
	private ProdutoRepository repo; // declarar uma dependencia de um objeto do tipo Repository

	@Autowired
	private CategoriaRepository categoriaRepository;

	
	public Produto find(Integer id) { //Operação capaz de buscar a categoria pelo codigo
		Optional<Produto> obj = repo.findById(id); //findOne faz a busca no banco de Dados com base no Id
		/* Esse modelo é para o Sprint a partir da 2.0 */
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);	
	}
}