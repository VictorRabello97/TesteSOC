package com.age.crm.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.age.crm.Model.ExameFuncionarioModel;
import com.age.crm.Model.ExameModel;
import com.age.crm.repository.ExameFuncionarioRepository;
import com.age.crm.repository.ExameRepository;

@RestController
@RequestMapping("/exames")
public class exameController {

	@Autowired
	private ExameRepository exameRepository;

	@Autowired
	private ExameFuncionarioRepository exameFuncionarioRepository;

	// BUSCANDO EXAMES CADASTRADOS
	@CrossOrigin("*")
	@GetMapping
	public ResponseEntity<?> getExames() {
		List<ExameModel> exames = new ArrayList<>();
		exameRepository.findAll().forEach(exames::add);
		if (exames.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há exames cadastrados");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(exames);
		}
	}
	
	
	//PEGANDO EXAMES POR ID
	@CrossOrigin("*")
	@GetMapping("/{id}")
	public ResponseEntity<?> getExames(@PathVariable Integer id) {
		List<ExameModel> exames = new ArrayList<>();
		exameRepository.findAllById(id).forEach(exames::add);
		if (exames.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há exames cadastrados");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(exames);
		}
	}

	// CRIANDO EXAME NO BANCO
	@PostMapping
	@CrossOrigin("*")
	public ExameModel createExame(@RequestBody ExameModel exame) {
		if(exame.getNome() == null || exame.getNome().isEmpty()) {
			throw new IllegalArgumentException("O nome do exame deve ser enviado no corpo da requisição.");
		}
		return exameRepository.save(exame);
	}

	// DELETANDO UM EXAME POR ID E VALIDANDO SE ESTÁ VINCULADO A UM FUNCIONÁRIO OU NAO
	@CrossOrigin("*")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteExame(@PathVariable Integer id) {
		Optional<ExameModel> exame = exameRepository.findById(id);
		if (exame.isPresent()) {
			List<ExameFuncionarioModel> vinculos = exameFuncionarioRepository.findByExames(exame.get());
			if (!vinculos.isEmpty()) {
				return ResponseEntity.badRequest().body("Exame já vinculado a um ou mais funcionários.");
			} else {
				exameRepository.delete(exame.get());
				return ResponseEntity.ok().body("Exame excluido com sucesso.");
			}
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// ATUALIZANDO NOME DO EXAME CASO NECESSÁRIO (PASSANDO ID)
	@CrossOrigin("*")
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateExame(@PathVariable Integer id, @RequestBody ExameModel exameNovo) {
		Optional<ExameModel> exameAntigo = exameRepository.findById(id);
		if (exameAntigo.isPresent()) {
			ExameModel exame = exameAntigo.get();
			exame.setNome(exameNovo.getNome());
			exameRepository.save(exame);
			return ResponseEntity.ok().body("Exame alterado com sucesso.");
		} else {
			return ResponseEntity.badRequest().body("Parametros inválidos.");
		}
	}
}
  








