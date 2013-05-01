package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Consultor extends Controller {

  
	public static ConsultorModel getConsultorLogado() {
		String id = play.mvc.Scope.Session.current().get("idUsuario");
    	ConsultorModel consultor = ConsultorModel.findById(Long.parseLong(id));
    	return consultor;
	}
}