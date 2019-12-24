package com.udemy.cursomc.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1, "Pendente"), 
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");

	private int cod;
	private String descricao;

	private EstadoPagamento(Integer cod, String descricao) {
		this.cod=cod;
		this.descricao=descricao;
	}
	//apenas os gets, em tipos enumerados uma vez instanciado nao se altera
	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	//converter um int para o EstadoPagamento
	public static EstadoPagamento toEnum(Integer cod) {

		if(cod==null) {
			return null;
		}

		for(EstadoPagamento x : EstadoPagamento.values()) {//todo objeto x nos valores possiveis do TipoCLiente
			if(cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("ID inválido " + cod);
	}

}
