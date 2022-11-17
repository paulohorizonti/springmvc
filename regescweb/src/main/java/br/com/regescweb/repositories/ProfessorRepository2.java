package br.com.regescweb.repositories;

import org.springframework.stereotype.Repository;

import br.com.regescweb.models.Professor;

@Repository
public interface ProfessorRepository2 extends PagingAndSortingRepository<Professor, Long>{

}
