package com.marthiins.cursomc.services.exception;


//OK REVISADO

public class FileException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileException(String msg) {
		super(msg); //Esse super é a super Classe do RuntimeException
	}
	
	public FileException (String msg, Throwable cause) {
		super(msg , cause);
	}
}
