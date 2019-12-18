package com.udemy.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

//anotação com o ctrl shift O ele já acha e importa a dependência correspondente
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.cursomc.domain.Categoria;
//essa classe será um controlador rest que vai responder por esse endpoint
@RestController 
@RequestMapping(value="/categorias")//nome do endpoint rest
public class CategoriaResource {

	//para ser uma função REST devo associar com algum dos verbos do HTTP
	//como é uma requisição básica onde estou obtendo dados, uso o get
	@RequestMapping(method=RequestMethod.GET)
	
	public List<Categoria> listar() {
		
		Categoria cat1= new Categoria(1, "Informática");
		Categoria cat2 = new Categoria(2, "Escritório");
		
		List<Categoria> lista = new ArrayList<>();
		lista.add(cat1);
		lista.add(cat2);
		
		return lista;
	}
	
}
