package com.udemy.cursomc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");

	private int cod;
	private String descricao;

	//construtor de enumerado é private
	private TipoCliente(int cod, String descricao) {

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

	public static TipoCliente toEnum(Integer cod) {

		if(cod==null) {
			return null;
		}

		for(TipoCliente x : TipoCliente.values()) {//todo objeto x nos valores possiveis do TipoCLiente
			if(cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("ID inválido " + cod);
	}

}
