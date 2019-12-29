package com.udemy.cursomc.services;
import java.util.List;
//tratamento das exceções no subpacote exception
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.Categoria;
import com.udemy.cursomc.repositories.CategoriaRepository;
import com.udemy.cursomc.services.exceptions.DataIntegrityException;
import com.udemy.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	//quando vc declara a dependência de um objeto do tipo repository dentro da classe
	//e usa a anotação autowired ele instancia o repository, 
	//usando injeção de dependência ou inversão de controle
	@Autowired
	private CategoriaRepository repo;	

	//	 e esse metodo recebe uma função que instancia uma exceção
	//	para facilitar usamos uma expressão lambda
	public Categoria find(Integer id) {//usamos o find id normal, ele vai retornar um optional
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(//	retorna um objeto ou senão existir, lança um exceção chamando o metodo orElseThrow
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);//garante que realmente insere um obj novo. Se o id estiver valendo algo o metodo save vai entender como atualização e não inserção
		return repo.save(obj);

	}
	//update é igual ao insert, a diferença que ele vê é quando o id está valendo nulo(insere) ou nao(atualiza)
	public Categoria update (Categoria obj) {
		find(obj.getId());//chamo o find pq ele já busca no banco e se nao existir já lança a exceção
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);//garante que o id existe senao já dispara a exceção
		try {
			repo.deleteById(id);
		}//essa exceção personalizada vem da camada serviço e é recebida na camada Resource
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
}
