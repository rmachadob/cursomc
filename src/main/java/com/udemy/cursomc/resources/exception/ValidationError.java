package com.udemy.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

//essa classe herda o StandardError e acrescenta a listinha de mensagens
public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);

	}
//esse getList que faz aparecer no JSON a palavra List, entao troco por error
	//pro Java o q importa é o que está no metodo get, essa palavra q vai ser convertida no JSON
	public List<FieldMessage> getErrors() {
		return errors;
	}
	//eu não quero acrescentar uma lista inteira de uma vez, quero acrescentar um erro de cada vez nessa lista, entao ao inves do set 
	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}



}
