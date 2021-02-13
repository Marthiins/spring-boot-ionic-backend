package com.marthiins.cursomc.services.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String msg) {
		super(msg); //Esse super é a super Classe do RuntimeException
	}
	
	public ObjectNotFoundException (String msg, Throwable cause) {
		super(msg , cause);
	}
}
