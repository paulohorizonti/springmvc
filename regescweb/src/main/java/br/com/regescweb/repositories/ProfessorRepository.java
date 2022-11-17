package br.com.regescweb.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Repository;

import br.com.regescweb.models.Professor;

//reopositorio para fazer o crud com as entidades e banco

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
	
	//nome do método deve conter o atributo da classe
    
    
    Page<Professor> findByNome(String nome, Pageable page);
    
    //modelo de busca
    //Page<Pessoa> findByNomeIgnoreCaseAndIdade(final String nome, final Integer idade, final Pageable page);
    
}
