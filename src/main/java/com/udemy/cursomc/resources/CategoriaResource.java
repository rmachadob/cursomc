package com.udemy.cursomc.resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
//anotação com o ctrl shift O ele já acha e importa a dependência correspondente
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.cursomc.domain.Categoria;
import com.udemy.cursomc.services.CategoriaService;
//essa classe será um controlador rest que vai responder por esse endpoint
@RestController 
@RequestMapping(value="/categorias")//nome do endpoint rest
public class CategoriaResource {

	//para ser uma função REST devo associar com algum dos verbos do HTTP
	//como é uma requisição básica onde estou obtendo dados, uso o get


	@Autowired//para falar com a camada de serviço:
	private CategoriaService service;

	//tipo do spring que encapsula a resposta com diversos elementos HTTP
	//<?> pq pode ser qualquer tipo
	@RequestMapping(value="/{id}", method =RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id){
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);//ok para indicar que houve sucesso na operação
	}


}


