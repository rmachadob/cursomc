package com.udemy.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.udemy.cursomc.domain.Categoria;
import com.udemy.cursomc.repositories.CategoriaRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	//commandLineRunner permite implementar um método auxiliar 
	//para executar alguma ação quando a aplicação iniciar
	@Autowired//dependência da classe
	private CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override//instancia no back ao invés de usar comando SQL direto no banco
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria (null, "Informática");//o id é gerado automático, coloco nulo e deixo o banco gerar
		Categoria cat2 = new Categoria (null, "Escritório");

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));//aceita obj ou lista de objs
	}



}
