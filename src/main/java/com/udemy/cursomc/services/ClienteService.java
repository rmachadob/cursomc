package com.udemy.cursomc.services;
import java.util.List;
//tratamento das exceções no subpacote exception
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udemy.cursomc.domain.Cidade;
import com.udemy.cursomc.domain.Cliente;
import com.udemy.cursomc.domain.Endereco;
import com.udemy.cursomc.domain.enums.TipoCliente;
import com.udemy.cursomc.dto.ClienteDTO;
import com.udemy.cursomc.dto.ClienteNewDTO;
import com.udemy.cursomc.repositories.CidadeRepository;
import com.udemy.cursomc.repositories.ClienteRepository;
import com.udemy.cursomc.repositories.EnderecoRepository;
import com.udemy.cursomc.services.exceptions.DataIntegrityException;
import com.udemy.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	//quando vc declara a dependência de um objeto do tipo repository dentro da classe
	//e usa a anotação autowired ele instancia o repository, 
	//usando injeção de dependência ou inversão de controle
	@Autowired
	private ClienteRepository repo;	
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;


	//	 e esse metodo recebe uma função que instancia uma exceção
	//	para facilitar usamos uma expressão lambda
	public Cliente find(Integer id) {//usamos o find id normal, ele vai retornar um optional
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(//	retorna um objeto ou senão existir, lança um exceção chamando o metodo orElseThrow
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
	}

	@Transactional//para garantir que na mesma transação vou salvar no BD endereço e cliente 
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());//só foi possível pq no meu fromDTO eu associei: cli.getEnderecos().add(end);
		return obj;

	}

	public Cliente update (Cliente obj) {
		Cliente newObj = find(obj.getId());//instancia um cliente A PARTIR DO BD

		updateData(newObj, obj);//metodo auxiliar para atualizar os dados desse newObj que criei com base no obj que veio como argumento(BD)

		//	find(obj.getId());//chamo o find pq ele já busca no banco e se nao existir já lança a exceção
		return repo.save(newObj);//salvo o novo obj com os dados atualizados
	}

	public void delete(Integer id) {
		find(id);//garante que o id existe senao já dispara a exceção
		try {
			repo.deleteById(id);
		}//essa exceção personalizada vem da camada serviço e é recebida na camada Resource
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}

	public List<Cliente> findAll(){
		return repo.findAll();
	}

	//Page eh classe do SpringData q encapsula infos e operações sobre a paginação
	//informo qual pag eu quero(page), quantas linhas por pagina(linesperpage), por qual atributo ordenar(orderBy) e qual a ordem (ascendente ou descendente)
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		//PageRequest tbm eh do SpringData que prepara as informações para a consulta Direction eh um tipo e preciso converter a String
		return repo.findAll(pageRequest);//uso sobrecarga de método pra ele usar PageRequest como argumento
	}

	//metodo auxiliar que instancia uma Cliente a prtir de um obj DTO
	public Cliente fromDTO(ClienteDTO objDto) {//não implementei por enquanto então lanço exceção caso o metodo seja chamado
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);//CPF ou CNPJ e tipo do cliente são nulos pq o DTO não tem esse dado
	}

	//sobrecarga do método
	public Cliente fromDTO(ClienteNewDTO objDTO) {

		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getCidadeId()));
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);

		cli.getEnderecos().add(end);//incluir o endereço end na lista de endereços do cliente
		cli.getTelefones().add(objDTO.getTelefone1());//1 telefone é obrigatório ter
		if(objDTO.getTelefone2()!= null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3()!= null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}	
		return cli;
	}

	//metodo auxiliar de dentro da classe que uso no update, não precisa ficar visível pra fora
	private void updateData(Cliente newObj, Cliente obj) {
		//os unicos dados q eu tenho possibilidade de atualizar no PUT são esses 2
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		//esse meu newObj que eu busquei do BD com todos os dados foi atualizado com os dados que forneci no obj 
	}


}
