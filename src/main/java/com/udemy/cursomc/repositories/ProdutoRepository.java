package com.udemy.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.udemy.cursomc.domain.Categoria;
import com.udemy.cursomc.domain.Produto;

@Repository
//JpaRepository: tipo de interface do Spring que acessa os dados com base em um tipo
//que vc vai passar, no caso abaixo Categoria
//depois tem que falar qual o tipo do atributo identificador do objeto
//nesse caso é o id, então Integer
//essa categoria já está mapeada no BD 
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
}
