package controllers;

import play.db.jpa.GenericModel;
import play.mvc.*;

import models.*;

public class Consultor extends BaseController {

  
	public static ConsultorModel getConsultorLogado() {
		String id = play.mvc.Scope.Session.current().get("idConsultor");
    	ConsultorModel consultor = ConsultorModel.findById(Long.parseLong(id));
    	return consultor;
	}
	
	public static void editar() {
		
		String operacao = params.get("OPERACAO", String.class);
		if (operacao != null && "EDITAR".equals(operacao)){
			Long id = params.get("id", Long.class);
			ConsultorModel consultor = ConsultorModel.findById(id);
			String senhaAtual = params.get("senhaAtual", String.class);
			if (senhaAtual.equals(consultor.getUsuario().getSenha())) {
				String novaSenha = params.get("novaSenha", String.class);
				consultor.getUsuario().setSenha(novaSenha);
				consultor.save();
			}
		}
		ConsultorModel consultor = getConsultorLogado();
    	consultor.getUsuario();
    	render(consultor);
		
    	
	}
	
	
	
}