package com.udemy.cursomc.services;
import java.util.List;
//tratamento das exceções no subpacote exception
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.Categoria;
import com.udemy.cursomc.domain.Produto;
import com.udemy.cursomc.repositories.CategoriaRepository;
import com.udemy.cursomc.repositories.ProdutoRepository;
import com.udemy.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	//quando vc declara a dependência de um objeto do tipo repository dentro da classe
	//e usa a anotação autowired ele instancia o repository, 
	//usando injeção de dependência ou inversão de controle
	@Autowired
	private ProdutoRepository repo;	
	
	@Autowired 
	private CategoriaRepository categoriaRepository;

	//	 e esse metodo recebe uma função que instancia uma exceção
	//	para facilitar usamos uma expressão lambda
	public Produto find(Integer id) {//usamos o find id normal, ele vai retornar um optional
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(//	retorna um objeto ou senão existir, lança um exceção chamando o metodo orElseThrow
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName())); 
	}
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);	
	}

}
