//classe produto tem relação muitos pra muitos com Categoria, 
//então precisa de uma entidade associativa

package com.udemy.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;//escolher sempre a interface e nao a implementação
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;

	@JsonBackReference//diz que do outro lado da associação já foram buscados os objetos, então agora eu não busco mais. ele vai omitir a lista de categorias para cada produto, barrando a referência cíclica
	@ManyToMany//relacionamento muitos pra muitos
	@JoinTable(name = "PRODUTO_CATEGORIA",//define qual a entidade associativa
	joinColumns = @JoinColumn(name="produto_id"),//nome da FK
	inverseJoinColumns = @JoinColumn(name = "categoria_id")	//nome da outra FK que referencia categoria	
			)
	private List<Categoria> categorias = new ArrayList<>();

	public Produto() {
	}

	public Produto(Integer id, String nome, Double preco) {
		super();//menos categoria pq é coleção, nao entra no construtor
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	//metodos para comparar os valores e não posição de memória
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



}
