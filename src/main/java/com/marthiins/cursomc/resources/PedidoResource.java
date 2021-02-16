package com.marthiins.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marthiins.cursomc.domain.Pedido;
import com.marthiins.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos") //categoria é um endpoint
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET) // Para a função poder funcionar deve-se anota-la com o devido metodo;
	public ResponseEntity<?> find(@PathVariable Integer id) { //para o spring saber que esse id /{id} da minha URL para o ID da variavel colocamos a anotação @PathVable
		Pedido obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	
}
