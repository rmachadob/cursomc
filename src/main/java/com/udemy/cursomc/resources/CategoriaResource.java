package com.udemy.cursomc.resources;

//anotação com o ctrl shift O ele já acha e importa a dependência correspondente
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//essa classe será um controlador rest que vai responder por esse endpoint
@RestController 
@RequestMapping(value="/categorias")//nome do endpoint rest
public class CategoriaResource {

	//para ser uma função REST devo associar com algum dos verbos do HTTP
	//como é uma requisição básica onde estou obtendo dados dados, uso o get
	@RequestMapping(method=RequestMethod.GET)
	public String listar() {
		return "Hurray! REST está funcionando!";
	}
	
}
