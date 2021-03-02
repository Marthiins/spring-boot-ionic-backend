package com.marthiins.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marthiins.cursomc.domain.Cliente;
import com.marthiins.cursomc.domain.ItemPedido;
import com.marthiins.cursomc.domain.PagamentoComBoleto;
import com.marthiins.cursomc.domain.Pedido;
import com.marthiins.cursomc.domain.enums.EstadoPagamento;
import com.marthiins.cursomc.repositories.ItemPedidoRepository;
import com.marthiins.cursomc.repositories.PagamentoRepository;
import com.marthiins.cursomc.repositories.PedidoRepository;
import com.marthiins.cursomc.security.UserSS;
import com.marthiins.cursomc.services.exception.AuthorizationException;
import com.marthiins.cursomc.services.exception.ObjectNotFoundException;


//OK REVISADO

@Service
public class PedidoService { //Classe responsavel por fazer a consulta nos repositorios
	
	@Autowired //para instanciar um repositorio no spring utilizamos a anotação @Autowired
	private PedidoRepository repo; //declarar uma dependencia de um objeto do tipo Repository
	
	@Autowired
	private BoletoService boletoService;
	
	//Salvar Pagamento no Banco de Dados
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) { //Operação capaz de buscar a categoria pelo codigo
		Optional<Pedido> obj = repo.findById(id); //findOne faz a busca no banco de Dados com base no Id
		/* Esse modelo é para o Sprint a partir da 2.0 */
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj );
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		//Buscamos o usuário logado e buscamos somente os pedidos deste usuário;
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}
}

