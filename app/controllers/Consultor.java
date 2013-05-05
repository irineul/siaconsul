package controllers;

import play.db.jpa.GenericModel;
import play.mvc.*;

import models.*;

public class Consultor extends Controller {

  
	public static ConsultorModel getConsultorLogado() {
		String id = play.mvc.Scope.Session.current().get("idUsuario");
    	ConsultorModel consultor = GenericModel.findById(Long.parseLong(id));
    	return consultor;
	}
}