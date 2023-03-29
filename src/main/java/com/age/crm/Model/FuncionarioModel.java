package com.age.crm.Model;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "funcionarios")
@Entity
public class FuncionarioModel {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Integer id;

  @Column(unique = true, nullable = false, length = 100)
  private String nome;
  
  @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ExameFuncionarioModel> exames = new ArrayList<>();

  
  public FuncionarioModel() {}

  public FuncionarioModel(String nome) {
    this.nome = nome;
  }

  public Integer getId() {
    return id;
  }


  public String getnome() {
    return nome;
  }
}
