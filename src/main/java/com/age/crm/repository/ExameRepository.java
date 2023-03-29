package com.age.crm.repository;
//
//import java.util.List;
////import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import com.age.crm.Model.ExameModel;
////import com.age.crm.Model.FuncionarioModel;
//
//@Repository
//public interface ExameRepository extends JpaRepository<ExameModel, Long> {
//
//	 @Query("SELECT e FROM ExameModel e JOIN e.funcionarios f WHERE f.id = :funcionarioId")
//	    List<ExameModel> findByExamesByFuncionariosId(@Param("funcionarioId") Long funcionarioId);
//
//	Iterable<ExameModel> findByNomeContaining(String nome);
//	}


import com.age.crm.Model.ExameModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExameRepository extends CrudRepository<ExameModel, Integer> {

	Iterable<ExameModel> findAllById(Integer id);

}