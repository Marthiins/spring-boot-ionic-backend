package com.marthiins.cursomc.resources.exception;

import java.io.Serializable;

//OK REVISADO

public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private Integer status; //status http do erro;
	private String msg; //mensagem do erro;
	private Long timeStamp; //Instante que ocorreu o erro;
	
	public StandardError(Integer status, String msg, Long timeStamp) {
		super();
		this.status = status;
		this.msg = msg;
		this.timeStamp = timeStamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
