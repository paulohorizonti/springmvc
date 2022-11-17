package br.com.regescweb.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Professor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	private BigDecimal salario;
	
	@Enumerated(EnumType.STRING)
	private StatusProfessor statusProfessor;
	
		
	public Professor() {
		super();
	}
	
	
	public Professor(String nome, BigDecimal salario, StatusProfessor statusProfessor) {
		super();
		this.nome = nome;
		this.salario = salario;
		this.statusProfessor = statusProfessor;
	}




	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getSalario() {
		return salario;
	}
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	public StatusProfessor getStatusProfessor() {
		return statusProfessor;
	}
	public void setStatusProfessor(StatusProfessor statusProfessor) {
		this.statusProfessor = statusProfessor;
	}

    //STRING COM OS DADOS DO OBJETO
	@Override
	public String toString() {
		return "Professor [id=" + id + ", nome=" + nome + ", salario=" + salario + ", statusProfessor="
				+ statusProfessor + "]";
	}
	
	
	
	
	
	
	
}
