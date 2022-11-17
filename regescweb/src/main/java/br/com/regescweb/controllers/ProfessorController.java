package br.com.regescweb.controllers;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.regescweb.dto.RequisicaoFormProfessor;
import br.com.regescweb.models.Professor;
import br.com.regescweb.models.StatusProfessor;
import br.com.regescweb.repositories.ProfessorRepository;




@Controller
@RequestMapping(value="/professores")
public class ProfessorController {
	//mesmo que context db do c# - ja cria o objeto com cruds e tudo
	
	@Autowired //vai fazer a instanciacao da interface - injecao de dep
	private ProfessorRepository professorRepository;
	

	 
	
	@GetMapping("")
	public ModelAndView buscarTodos(@PageableDefault(size = 10) Pageable pageable,  Model model, String linhas, @RequestParam(value = "nome", required = false)String nome)
	{
		Page<Professor> page = professorRepository.findAll(pageable);
		
		
		if(nome != null && nome !="") {
			page = professorRepository.findByNome(nome, pageable);
			
		}
		
		
		ModelAndView mv = new ModelAndView("professores/index2");
		
		
		mv.addObject("page", page);
		//model.addAttribute("page", page);	
       return mv;		
	
	}
	/*@GetMapping("")
	public ModelAndView index() {
		
		
		List<Professor> professores = this.professorRepository.findAll();
		
		ModelAndView mv = new ModelAndView("professores/index");
		
		mv.addObject("professores", professores);
		
		return mv;
		
	}*/
	

	@GetMapping("/new")
	public ModelAndView nnew(RequisicaoFormProfessor requisicao) {
		ModelAndView mv = new ModelAndView("professores/new");
		
		//montar o select de status do professor
		mv.addObject("listaStatusProfessor", StatusProfessor.values());
		return mv;
		
	}
	
	//retorna para a pagina de index
	@PostMapping("")
	public ModelAndView create(@Valid RequisicaoFormProfessor requisicao, BindingResult result) { //receber os valores do form
		if(result.hasErrors()) {
			System.out.println("Tem erros");
			ModelAndView mv = new ModelAndView("professores/new");
			mv.addObject("listaStatusProfessor", StatusProfessor.values());
			return mv; //volta para a lista - nao redirecionando, mas somente carregando a pagina
			
		}else {
			
			Professor professor = requisicao.toProfessor(); //transpofta em professor
						
			this.professorRepository.save(professor); //faz o salvamento no banco
			return new ModelAndView("redirect:/professores/"+professor.getId()); //volta para a lista
			
		}
		
		
	}
	
	@GetMapping("/{id}")
	public ModelAndView show(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("professores/show");
		System.out.println("******* ID: " + id);
		
		Optional<Professor> optional = this.professorRepository.findById(id);
		
		if(optional.isPresent()) {
			Professor professor = optional.get();
			
		   mv = new ModelAndView("professores/show");
			mv.addObject("professor", professor);
			
			return mv;
			
		}else {
			//nao achou
			mv = this.retornaErroProfessores("show ERROR: Professor #"+id+" não encontrado no banco");
			return mv;
		}
		
		
	}
	
	@GetMapping("/{id}/edit")
	public ModelAndView edit(@PathVariable Long id, RequisicaoFormProfessor requisicao) {
		Optional<Professor> optional =  this.professorRepository.findById(id);
		
		if(optional.isPresent()) {
			Professor professor = optional.get();
			requisicao.fromProfessor(professor);
			
			ModelAndView mv = new ModelAndView("professores/edit");
			mv.addObject("professorId", professor.getId());
			//montar o select de status do professor
			mv.addObject("listaStatusProfessor", StatusProfessor.values());
			
			
			
		return mv;
		}
		else {
			
		    ModelAndView mv = new ModelAndView("redirect:/professores");
		    mv = this.retornaErroProfessores("EDIT  ERROR: Professor #"+id+" não encontrado no banco");
		    return mv;
			
		}
		
		
		
	}
	
	@PostMapping("/{id}")
	public ModelAndView update(@PathVariable Long id, @Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors())
		{
			ModelAndView mv = new ModelAndView("professores/edit");
			mv.addObject("professorId", id);
			mv.addObject("listaStatusProfessor", StatusProfessor.values());
			
			
			
			return  mv;
		}else
		{
			Optional<Professor>optional = this.professorRepository.findById(id);
			
			if(optional.isPresent())
			{
				//se o objeto for presente,se existir o professor no banco
				Professor professor =requisicao.toProfessor(optional.get()); //vindo do banco
				
				this.professorRepository.save(professor); //vai salvar		
				
				ModelAndView mv = new ModelAndView("redirect:/professores");
				mv.addObject("mensagem", "Professor #"+id+" alterado com sucesso");
				mv.addObject("erro", false);
						
			    return mv;
				
			}else
			{
			    System.out.println("########### - NÃO ENCONTRADO - ###########");
			    ModelAndView mv = this.retornaErroProfessores("UPDATE ERROR: Professor #"+id+" não encontrado no banco");
				return mv;
			}
		}
				
		
	}
	
	@GetMapping("/{id}/delete")
	public ModelAndView delete(@PathVariable Long id) 
	{
		ModelAndView mv = new ModelAndView("redirect:/professores");
		
		try {
			this.professorRepository.deleteById(id);
			
			mv.addObject("mensagem", "Professor #"+id+" deletado com sucesso");
			mv.addObject("erro", false);
			
		}catch(EmptyResultDataAccessException e) {
			System.out.println(e);
			mv = this.retornaErroProfessores("DELETE ERROR: Professor #"+id+" não encontrado no banco");
			
			
		}
		
		return mv;
		
	}
	
	private ModelAndView retornaErroProfessores(String msg) {
		ModelAndView mv = new ModelAndView("redirect:/professores");
	    mv.addObject("mensagem", msg);
		mv.addObject("erro", true);
	    return mv;
		
	}
	
	
}
