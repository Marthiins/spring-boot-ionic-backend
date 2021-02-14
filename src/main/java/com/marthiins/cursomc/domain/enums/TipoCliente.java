package com.marthiins.cursomc.domain.enums;

public enum TipoCliente {

	//No tipo de cliente podemos colocar o tipo com a virgula e tambem colocar o numero dos valores
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Juridica");
	
	//Criar duas variaveis uma para guardar o codigo e outra a descrição

	private int cod;
	private String descrição;
	
	//construtor do tipo enumerado ele é sempre private
	private TipoCliente(int cod, String descrição) {
		this.cod = cod;
		this.descrição = descrição;
	}
	//Uma vez que estanciamos um metodo enumerado não mudamos o nome dele por isso que so temos o get
	public int getCod() {
		return cod;
	}

	
	public String getDescrição() {
		return descrição;
	}      
	
	public static TipoCliente toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		//O For vai percorrer dos valores possiveis do meu tipo enumerado Cliente (Pessoa Fisica e Pessoa Juridica)
		for (TipoCliente x : TipoCliente.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
	    //Se não for nenhum irei lançar uma exceção
	     throw new IllegalArgumentException("Id inválido:" + cod);
	}
	
}
