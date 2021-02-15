package com.marthiins.cursomc.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1 ,"Pendente"),
	QUITADO(2 ,"Quitado"),
	CANCELADO(3 ,"Cancelado");
	
	//Criar duas variaveis uma para guardar o codigo e outra a descrição

		private int cod;
		private String descricao;
		
		//construtor do tipo enumerado ele é sempre private
		private EstadoPagamento(int cod, String descricao) {
			this.cod = cod;
			this.descricao = descricao;
		}
		//Uma vez que estanciamos um metodo enumerado não mudamos o nome dele por isso que so temos o get
		public int getCod() {
			return cod;
		}

		
		public String getDescricao() {
			return descricao;
		}      
		
		public static EstadoPagamento toEnum(Integer cod) {
			
			if (cod == null) {
				return null;
			}
			//O For vai percorrer dos valores possiveis do meu tipo enumerado Cliente (Pessoa Fisica e Pessoa Juridica)
			for (EstadoPagamento x : EstadoPagamento.values()) {
				if (cod.equals(x.getCod())) {
					return x;
				}
			}
		    //Se não for nenhum irei lançar uma exceção
		     throw new IllegalArgumentException("Id inválido:" + cod);
		}
}
