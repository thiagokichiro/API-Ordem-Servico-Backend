package com.trkdev.osworks.domain.exception;

/*
Classe que representa erros de domínio / negócio
*/
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NegocioException(String message) {
		super(message);
	}
}
