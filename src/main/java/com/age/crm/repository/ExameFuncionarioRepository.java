package com.age.crm.repository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.age.crm.Model.ExameFuncionarioModel;
import com.age.crm.Model.ExameModel;
import com.age.crm.Model.FuncionarioModel;



@Repository
public interface ExameFuncionarioRepository
		extends CrudRepository<ExameFuncionarioModel, ExameFuncionarioModel.ExameFuncionarioId> {
	List<ExameFuncionarioModel> findByIdFuncionarioId(Integer funcionarioId);

	List<ExameFuncionarioModel> findByExames(ExameModel exameModel);

	List<ExameFuncionarioModel> findByFuncionario(FuncionarioModel funcionarioModel);

	List<ExameFuncionarioModel> findByAddedAtBetween(LocalDate dataInicial, LocalDate dataFinal);

	List<ExameFuncionarioModel> findByFuncionarioIdAndAddedAt(Integer funcionarioId, LocalDate addedAt);

	


}