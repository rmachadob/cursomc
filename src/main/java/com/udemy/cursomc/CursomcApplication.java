package com.udemy.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.udemy.cursomc.domain.Categoria;
import com.udemy.cursomc.domain.Produto;
import com.udemy.cursomc.repositories.CategoriaRepository;
import com.udemy.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	//commandLineRunner permite implementar um método auxiliar 
	//para executar alguma ação quando a aplicação iniciar
	@Autowired//dependência da classe
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override//instancia no back ao invés de usar comando SQL direto no banco
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria (null, "Informática");//o id é gerado automático, coloco nulo e deixo o banco gerar
		Categoria cat2 = new Categoria (null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		//tem que fazer as asssociações em via de mão dupla de categoria para produtos e vice versa
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));//seguindo o diagrama de classes para determinar quem se associa com quem
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));//aceita obj ou lista de objs
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
	}



}
