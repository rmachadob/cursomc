package com.udemy.cursomc.domain;
//CLASSE DE ASSOCIAÇÃO
import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable//pra dizer que é um subtipo. 
//  Specifies a class whose instances are stored as an intrinsic 
// part of an owning entity and share the identity of the entity.
public class ItemPedidoPK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne//o ItemPedido tem que conhecer o pedido e o produto
	@JoinColumn(name="pedido_id")//FK
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name="produto_id")
	private Produto produto;
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	//pra comparar se o itemPedido é igual ao outro tem q ser com base nos 2 atributos
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		ItemPedidoPK other = (ItemPedidoPK) obj;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}
	
	
	
}
