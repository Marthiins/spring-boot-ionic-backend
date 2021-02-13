package com.marthiins.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marthiins.cursomc.domain.Categoria;
import com.marthiins.cursomc.domain.Cidade;
import com.marthiins.cursomc.domain.Estado;
import com.marthiins.cursomc.domain.Produto;
import com.marthiins.cursomc.repositories.CategoriaRepository;
import com.marthiins.cursomc.repositories.CidadeRepository;
import com.marthiins.cursomc.repositories.EstadoRepository;
import com.marthiins.cursomc.repositories.ProdutoRepository;


@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository; //Os repository vai ser o objeto responsavel por salvar os dados no Banco de Dados
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}/* Implemenar a CommandLineRunner para puxar a função run abaixo */

	@Override
	public void run(String... args) throws Exception {
	
		Categoria cat1 = new Categoria (null, "Informática");
		Categoria cat2 = new Categoria (null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		//Associação da Categorias aos produtos temos que adicionar
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//Associar os Produtos as Categorias
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1 , cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1 , cat2));
	
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2)); //Criar LIsta automatica
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3)); // criar a Lista para salvar todos os produtos por enquanto so temos 3
	
	
        Estado est1 = new Estado(null, "Brasilia");
		Estado est2 = new Estado(null, "Goias");
		
		Cidade c1 = new Cidade(null, "Brazlândia", est1);
		Cidade c2 = new Cidade(null, "Vendinha", est2);
		Cidade c3 = new Cidade(null, "Monte Alto", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
	
	    estadoRepository.saveAll(Arrays.asList(est1, est2));
	    cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
	    
	}

}
