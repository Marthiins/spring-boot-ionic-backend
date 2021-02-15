package com.marthiins.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marthiins.cursomc.domain.Categoria;
import com.marthiins.cursomc.domain.Cidade;
import com.marthiins.cursomc.domain.Cliente;
import com.marthiins.cursomc.domain.Endereco;
import com.marthiins.cursomc.domain.Estado;
import com.marthiins.cursomc.domain.Pagamento;
import com.marthiins.cursomc.domain.PagamentoComBoleto;
import com.marthiins.cursomc.domain.PagamentoComCartao;
import com.marthiins.cursomc.domain.Pedido;
import com.marthiins.cursomc.domain.Produto;
import com.marthiins.cursomc.domain.enums.EstadoPagamento;
import com.marthiins.cursomc.domain.enums.TipoCliente;
import com.marthiins.cursomc.repositories.CategoriaRepository;
import com.marthiins.cursomc.repositories.CidadeRepository;
import com.marthiins.cursomc.repositories.ClienteRepository;
import com.marthiins.cursomc.repositories.EnderecoRepository;
import com.marthiins.cursomc.repositories.EstadoRepository;
import com.marthiins.cursomc.repositories.PagamentoRepository;
import com.marthiins.cursomc.repositories.PedidoRepository;
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
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	
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
	    
	    //Instanciar o Cliente
	    Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
	    cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
	    
	    //os endereços conhece a cidade e os clientes
	    Endereco e1 =new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
	    Endereco e2 =new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
	
	   //Associar o cliente para conhecer os endreços
	    cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
	    
	    clienteRepository.saveAll(Arrays.asList(cli1));
	    enderecoRepository.saveAll(Arrays.asList(e1 , e2));
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    //Estanciando os pedidos
	    Pedido ped1 = new Pedido(null, sdf.parse("14/02/2021 20:50"), cli1, e1);
	    Pedido ped2 = new Pedido(null, sdf.parse("15/02/2021 21:50"), cli1, e2);
	    
	    Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, null, 6);
	    ped1.setPagamento(pagto1);
	    
	    Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("14/01/2021 00:00"), null);
	    ped2.setPagamento(pagto2);
	    
	    cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
	
	    pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
	    pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
	}

}
