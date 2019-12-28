package com.udemy.cursomc.resources;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
//anotação com o ctrl shift O ele já acha e importa a dependência correspondente
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	public ResponseEntity<Categoria> find(@PathVariable Integer id){//recebe o parametro na URL
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);//ok para indicar que houve sucesso na operação
	}

	//void = retorna uma resposta http mas sem corpo
	@RequestMapping(method=RequestMethod.POST)//POST pq eh pra criar
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){//@RequestBody faz o JSON ser voncertido para o obj java automaticamente
		//chama o serviço q insere a nova categoria no BD
		obj = service.insert(obj);//a operação save do repository retorna um objeto, por isso eu guardo no obj

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(obj.getId()).toUri();
		//fromCurrentRequest pega a URI que usamos para inserir, nesse caso categoria
		//path("/{id}" pega a URI e acrescenta o id do objeto que foi criado
		//buildAndExpand atribui o novo valor e converte para URI (toUri)
		return ResponseEntity.created(uri).build();//esse created recebe a URI como argumento e eh o metodo que me devolve o codigo http, nesse caso 201(Created)
		//build para gerar essa resposta 

	}
	//esse metodo é uma mistura do GET e do POST, pq ele tem q receber o obj (RequestBody) e receber o parametro da URL (PathVariable)
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)//esse tipo de requisição vai ter id que nem no GET, por isso value=id
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();//retorna conteudo vazio (no content)

	}
	
	@RequestMapping(value="/{id}", method =RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}


