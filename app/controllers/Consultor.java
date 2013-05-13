package controllers;

import play.db.jpa.GenericModel;
import play.mvc.*;

import models.*;

public class Consultor extends BaseController {

  
	public static ConsultorModel getConsultorLogado() {
		String id = play.mvc.Scope.Session.current().get("idUsuario");
    	ConsultorModel consultor = ConsultorModel.findById(Long.parseLong(id));
    	return consultor;
	}
}