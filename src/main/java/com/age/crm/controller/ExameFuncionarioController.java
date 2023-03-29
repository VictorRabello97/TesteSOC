 package com.age.crm.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.management.AttributeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.age.crm.Model.ExameFuncionarioModel;
import com.age.crm.Model.ExameModel;
import com.age.crm.Model.FuncionarioModel;
import com.age.crm.Model.VinculacaoDTO;
import com.age.crm.repository.ExameFuncionarioRepository;
import com.age.crm.repository.ExameRepository;
import com.age.crm.repository.FuncionarioRepository;

@RestController
@RequestMapping("/api")
public class ExameFuncionarioController {

	@Autowired
	FuncionarioRepository funcionarioRepository;

	@Autowired
	ExameRepository exameRepository;

	@Autowired
	ExameFuncionarioRepository exameFuncionarioRepository;
    
    
	// MOSTRA O FUNCIONÁRIO E SEUS EXAMES REALIZADOS
	@CrossOrigin("*")
	@GetMapping("/{id}/exames")
	public ResponseEntity<?> getExamesPorFuncionario(@PathVariable("id") Integer id) {
		Optional<FuncionarioModel> funcionario = funcionarioRepository.findById(id);

		if (funcionario.isPresent()) {
			List<ExameFuncionarioModel> exames = exameFuncionarioRepository.findByFuncionario(funcionario.get());
			return ResponseEntity.ok(exames);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// RELATORIO DE EXAMES POR DATA INICIAL E FINAL
	@CrossOrigin("*")
	@GetMapping("/relatorio")
	public List<ExameFuncionarioModel> getExamesFuncionariosPorData(
			@RequestParam("dataInicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
			@RequestParam("dataFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
		return exameFuncionarioRepository.findByAddedAtBetween(dataInicial, dataFinal);
	}

	
	// VINCULA UM FUNCIONÁRIO A UM EXAME E VALIDANDO DUPLICIDADE
	@CrossOrigin("*")
	@PostMapping("/funcionarios/exames")
	public ResponseEntity<String> vincularFuncionarioExame(@RequestBody VinculacaoDTO dto) {
		try {

			List<ExameFuncionarioModel> examesFuncionarioData = exameFuncionarioRepository
					.findByFuncionarioIdAndAddedAt(dto.getFuncionarioId(), LocalDate.now());

			for (ExameFuncionarioModel exameFuncionarioData : examesFuncionarioData) {
				if (exameFuncionarioData.getExame().getId() == dto.getExameId()) {
					return ResponseEntity.status(HttpStatus.CONFLICT)
							.body("Funcionário já realizou esse exame nessa mesma data");
				}
			}

			FuncionarioModel funcionario = funcionarioRepository.findById(dto.getFuncionarioId())
					.orElseThrow(() -> new AttributeNotFoundException(
							"Funcionário não encontrado com o ID " + dto.getFuncionarioId()));

			ExameModel exame = exameRepository.findById(dto.getExameId()).orElseThrow(
					() -> new AttributeNotFoundException("Exame não encontrado com o ID " + dto.getExameId()));

			ExameFuncionarioModel exameFuncionario = new ExameFuncionarioModel(
					new ExameFuncionarioModel.ExameFuncionarioId(dto.getFuncionarioId(), dto.getExameId()),
					LocalDate.now());
			exameFuncionario.setFuncionario(funcionario);
			exameFuncionario.setExame(exame);

			exameFuncionarioRepository.save(exameFuncionario);

			return ResponseEntity.status(HttpStatus.CREATED).body("Exame vinculado com sucesso");

		} catch (AttributeNotFoundException e) {
			return ResponseEntity.notFound().build();

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
    
	
	// DELETANDO FUNCIONÁRIO E EXAMES VINCULADOS A ELE
	@CrossOrigin("*")
	@DeleteMapping("/funcionarios/{id}")
	public ResponseEntity<Void> deleteFuncionario(@PathVariable Integer id) {
		try {
			funcionarioRepository.deleteById(id);
			return ResponseEntity.ok().build();
			
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
  

