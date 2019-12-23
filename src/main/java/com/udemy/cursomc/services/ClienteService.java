package com.udemy.cursomc.services;
//tratamento das exceções no subpacote exception
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.Cliente;
import com.udemy.cursomc.repositories.ClienteRepository;
import com.udemy.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	//quando vc declara a dependência de um objeto do tipo repository dentro da classe
	//e usa a anotação autowired ele instancia o repository, 
	//usando injeção de dependência ou inversão de controle
	@Autowired
	private ClienteRepository repo;	

	//	 e esse metodo recebe uma função que instancia uma exceção
	//	para facilitar usamos uma expressão lambda
	public Cliente find(Integer id) {//usamos o find id normal, ele vai retornar um optional
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(//	retorna um objeto ou senão existir, lança um exceção chamando o metodo orElseThrow
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
	}


}
