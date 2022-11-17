package br.com.regescweb.dto;



import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.regescweb.models.Professor;
import br.com.regescweb.models.StatusProfessor;

//classe parecida com modelview para o .net data transfer object..so insere o que estier aqui
public class RequisicaoFormProfessor {
	@NotBlank
	@NotNull
	private String nome; //em caso de erro
	
	@NotNull
	@DecimalMin("0.0")
	private BigDecimal salario;
	
	private StatusProfessor statusProfessor;
	
	
	
	public BigDecimal getSalario() {
		return salario;
	}
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public StatusProfessor getStatusProfessor() {
		return statusProfessor;
	}
	public void setStatusProfessor(StatusProfessor statusProfessor) {
		this.statusProfessor = statusProfessor;
	}
	
	//metodo para converter em um objeto de verdade
	public Professor toProfessor() {
		Professor professor = new Professor();
		professor.setNome(this.nome);
		professor.setSalario(this.salario);
		professor.setStatusProfessor(this.statusProfessor);
		
		return professor;
	}
	
	
	public Professor toProfessor(Professor professor)
	{
		professor.setNome(this.getNome());
		professor.setSalario(this.getSalario());
		professor.setStatusProfessor(this.getStatusProfessor());
		
		return professor;
		
	}
	
	public void fromProfessor(Professor professor) {
		this.nome = professor.getNome();
		this.salario = professor.getSalario();
		this.statusProfessor = professor.getStatusProfessor();
	}
	@Override
	public String toString() {
		return "RequisicaoNovoProfessor [salario=" + salario + ", nome=" + nome + ", statusProfessor=" + statusProfessor
				+ "]";
	}
	
	
	

}
