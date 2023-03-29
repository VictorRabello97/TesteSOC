package com.age.crm.Model;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;


//RESPONSÁVEL POR TODA A INTEGRAÇÃO ENTRE EXAME E FUNCIONARIO
//3 TABELA.

@Entity
@Table(name = "funcionarios_exames")
public class ExameFuncionarioModel {
	@EmbeddedId
	private ExameFuncionarioId id = new ExameFuncionarioId();

	@ManyToOne
	@MapsId("funcionarioId")
	private FuncionarioModel funcionario;

	@ManyToOne
	@MapsId("exameId")
	private ExameModel exames;

	@CreationTimestamp
	@Column(name = "added_at", nullable = false)
	private LocalDate addedAt;

	public ExameFuncionarioModel() {
	}

	public ExameFuncionarioModel(ExameFuncionarioId id) {
		this.id = id;

	}

	public ExameFuncionarioId getId() {
		return id;
	}

	public FuncionarioModel getFuncionario() {
		return funcionario;
	}

	public ExameModel getExame() {
		return exames;
	}

	public ExameFuncionarioModel(ExameFuncionarioId id, LocalDate addedAt) {
		this.id = id;
		this.addedAt = addedAt;
	}

	public LocalDate getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(LocalDate addedAt) {
		this.addedAt = addedAt;
	}

	@Embeddable
	public static class ExameFuncionarioId implements Serializable {
		private Integer funcionarioId;
		private Integer exameId;

		public ExameFuncionarioId() {
		}

		public ExameFuncionarioId(Integer funcionarioId, Integer exameId) {
			super();
			this.funcionarioId = funcionarioId;
			this.exameId = exameId;
		}

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

	public void setFuncionario(FuncionarioModel funcionario) {
		this.funcionario = funcionario;

	}

	public void setExame(ExameModel exame) {
		this.exames = exame;

	}

}