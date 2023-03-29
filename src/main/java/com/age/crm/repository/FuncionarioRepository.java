package com.age.crm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.age.crm.Model.FuncionarioModel;

@Repository
public interface FuncionarioRepository extends CrudRepository<FuncionarioModel, Integer> {
	
}