package com.age.crm.Model;

//classe responsável por fazer a integração do exameId e funcionarioId

public class VinculacaoDTO {
	private Integer funcionarioId;
	private Integer exameId;

	public Integer getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Integer funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Integer getExameId() {
		return exameId;
	}

	public void setExameId(Integer exameId) {
		this.exameId = exameId;
	}

}
