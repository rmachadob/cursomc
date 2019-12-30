package com.udemy.cursomc.resources;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
//anotação com o ctrl shift O ele já acha e importa a dependência correspondente
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.cursomc.domain.Cliente;
import com.udemy.cursomc.dto.ClienteDTO;
import com.udemy.cursomc.services.ClienteService;
//essa classe será um controlador rest que vai responder por esse endpoint
@RestController 
@RequestMapping(value="/clientes")//nome do endpoint rest
public class ClienteResource {

	//para ser uma função REST devo associar com algum dos verbos do HTTP
	//como é uma requisição básica onde estou obtendo dados, uso o get


	@Autowired//para falar com a camada de serviço:
	private ClienteService service;

	//tipo do spring que encapsula a resposta com diversos elementos HTTP
	//<?> pq pode ser qualquer tipo
	@RequestMapping(value="/{id}", method =RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);//ok para indicar que houve sucesso na operação
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)//esse tipo de requisição vai ter id que nem no GET, por isso value=id
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
		
		Cliente obj = service.fromDTO(objDto);
		
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();//retorna conteudo vazio (no content)

	}

	@RequestMapping(value="/{id}", method =RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll(){
		//pra cada elemento dessa lista preciso instanciar o DTO correspondente
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		//stream para percorrer a lista, map para efetuar uma operação pra cada elemento da lista. cada elemento da lista estou apelidando de obj
		//pra cada elemento obj uso o operador arrow function, ou seja, uma função anônima que recebe o obj e cria o new ClienteDTO passando o obj como argumento
		//feito isso eu volto esse stream de objetos para io tipo lista com o Collectors.toList
		return ResponseEntity.ok().body(listDto);//ok para indicar que houve sucesso na operação
	}

	//endpoint pra pegar a requisição e chamar o metodo de serviço
	@RequestMapping(value ="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, //@Requestparam para que eles sejam parametros opcionais. o nome do parametro é page e o valor padrão é zero. ou seja, se eu nao informar o parametro da pagina ele vai pra primeira automaticamente
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, //24 pq é multiplo de 2, 3 e 4, entao fica melhor para a responsividade
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, //quero ordenar pelo campo nome
			@RequestParam(value="direction", defaultValue="ASC") String direction){//padrao ascendente

		//retorna um page de categoria
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		//converte para uma page de ClienteDTO
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));	//como o Page já é Java 8 compliance, entao nao preciso nem do stream nem do collect

		return ResponseEntity.ok().body(listDto);//ok para indicar que houve sucesso na operação
	}


}


