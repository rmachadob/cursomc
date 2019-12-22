package com.udemy.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.cursomc.domain.Cliente;

@Repository
//JpaRepository: tipo de interface do Spring que acessa os dados com base em um tipo
//que vc vai passar, no caso abaixo Categoria
//depois tem que falar qual o tipo do atributo identificador do objeto
//nesse caso é o id, então Integer
//essa categoria já está mapeada no BD 
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	
}
