package com.marthiins.cursomc.services.exception;


//OK REVISADO

public class AuthorizationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthorizationException(String msg) {
		super(msg); //Esse super é a super Classe do RuntimeException
	}
	
	public AuthorizationException (String msg, Throwable cause) {
		super(msg , cause);
	}
}
