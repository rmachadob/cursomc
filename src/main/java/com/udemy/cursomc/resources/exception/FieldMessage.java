package com.udemy.cursomc.resources.exception;

import java.io.Serializable;
//classe auxiliar para mostrar a lista de erros em pares (nome do campo e msg de erro em relação ao campo)
//para carregar esses dados crio essa classe auxiliar
public class FieldMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String message;
	
	public FieldMessage() {
			}
	
	public FieldMessage(String fieldName, String message) {
		super();
		this.fieldName=fieldName;
		this.message=message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
