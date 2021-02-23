package com.marthiins.cursomc.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marthiins.cursomc.domain.Categoria;
import com.marthiins.cursomc.domain.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	/*
	 * Documentação do Spring Data
	 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
	 */

	/* Anotação para pesquisa JPQL - nesse caso uma pesquisa customizada */
	// @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	// Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

	/* Pesquisa utilizando a estrutura de nomeação do Spring Data, aqui ele mesmo fara a pesquisa*/
	// Page<Produto> findDistinctByNomeContainingAndCategoria(String nome, List<Categoria> categorias, Pageable pageRequest);

	/*E caso inserir a pesquisa JPQL o spring dará preferência para a anotação*/
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
}
