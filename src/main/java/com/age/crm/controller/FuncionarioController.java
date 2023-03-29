package com.age.crm.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.age.crm.Model.FuncionarioModel;
import com.age.crm.repository.FuncionarioRepository;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

  @Autowired
  private FuncionarioRepository funcionarioRepository;
  
  //BUSCA TODOS OS FUNCIONARIOS CADASTRADOS EM SISTEMA
  @CrossOrigin("*")
  @GetMapping
  public Iterable<FuncionarioModel> getAllFuncionarios() {
    return funcionarioRepository.findAll();
  }

  // CADASTRA FUNCIONÁRIOS NO SISTEMA
  @CrossOrigin("*")
  @PostMapping
  public FuncionarioModel createFuncionario(@RequestBody FuncionarioModel funcionario) {
    return funcionarioRepository.save(funcionario);
  }
  


}
