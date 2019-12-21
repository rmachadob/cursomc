package com.udemy.cursomc.resources.exception;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//trata as exceções da camada REST, que por boas práticas não recebe os tratamentos ou blocos try catch
//nessa classe eu intercepto e centralizo minha manipulação das exceções
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.udemy.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	//implementa uma classe auxiliar que vai interceptar as exceções e dentro do framework tem q ter essa assinatura abaixo
	@ExceptionHandler(ObjectNotFoundException.class)//no parêntesis eu indico que é um tratador de exceções desse tipo de exceção
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){//httpServletRequest para receber os valores da requisição

		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());//passa os dados do erro .value para ir no formato de numero inteiro (ero 404) v.g.

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);//o retorno é essa classe 
	}

}
