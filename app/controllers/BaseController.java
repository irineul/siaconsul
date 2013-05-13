package controllers;

import play.mvc.Before;
import play.mvc.Controller;

public class BaseController extends Controller{

	@Before(unless={"Application.index","Application.login"})
    static void checkAuthentification() {
        if(play.mvc.Scope.Session.current().get("idUsuario") == null )
        	Application.index();
    }
}
