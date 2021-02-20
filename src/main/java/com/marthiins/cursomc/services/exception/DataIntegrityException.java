package com.marthiins.cursomc.services.exception;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String msg) {
		super(msg); //Esse super é a super Classe do RuntimeException
	}
	
	public DataIntegrityException (String msg, Throwable cause) {
		super(msg , cause);
	}
}
