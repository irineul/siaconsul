package controllers;

import play.db.jpa.GenericModel;
import play.mvc.*;

import java.util.*;

import others.Util;

import models.*;


public class Application extends BaseController {

	public static void index() {
		render();
	}

	public static void logout() {
		play.mvc.Scope.Session.current().put("idUsuario",null);
		index();
	}

	public static void home() {
		ConsultorModel consultor = Consultor.getConsultorLogado();
		if(consultor != null && consultor.getId() != 0)
		{
			List<ClienteModel> clientes = new ArrayList<ClienteModel>();
			clientes = consultor.getClientes();
			render(clientes);
		}
		else
		{
			render();
		}
	}


	public static void pagination(int page) {
		page = 10*page;
		ConsultorModel consultor = Consultor.getConsultorLogado();
		List<ClienteModel> clientes = new ArrayList<ClienteModel>();
		List<ClienteModel> cls = consultor.getClientes();
		for(int i=0; i< cls.size(); i++) {
			if(i> page && clientes.size() <=10){
				clientes.add(cls.get(i));
			}
		}
		renderTemplate("consultor", clientes);
	}

	public static void login(String email, String senha) {

		List<UsuarioModel> result = UsuarioModel.find("email = ? And senha = ?", email, senha).fetch();

		if (result != null && result.size() > 0) {
			UsuarioModel usuario = result.get(0);
			List<AdvogadoModel> advogado = AdvogadoModel.find("idUsuario=?", usuario.id).fetch();
			if (advogado != null && advogado.size() > 0) {
				play.mvc.Scope.Session.current().put("idAdvogado",advogado.get(0).id);
				play.mvc.Scope.Session.current().put("idConsultor",0);
				play.mvc.Scope.Session.current().put("tpUsuario","A");
				play.mvc.Scope.Session.current().put("idUsuario",2);
				Processo.lista(null);
			}

			else
			{
				List<ConsultorModel> consultor = ConsultorModel.find("idUsuario=?", usuario.id).fetch();
				if (consultor != null && consultor.size() > 0) {
					play.mvc.Scope.Session.current().put("idConsultor",consultor.get(0).id);
					play.mvc.Scope.Session.current().put("idUsuario",consultor.get(0).getIsuario());
					play.mvc.Scope.Session.current().put("tpUsuario","C");
					home();
				}
			}

		}
		//TODO: mostrar erro na tela;
		index();
	}

	public static void criaUsuarioTeste() {
		UsuarioModel u = new UsuarioModel();
		u.setEmail("rossanaruscher@uol.com.br");
		u.setNome("Rossana Magali Ruscher");
		u.setSenha("junior");
		u.setCpfCnpj("86473752000195");
		u.setEndereco("");
		u.setTelResidencial("");
		u.setCelular("");
		AdvogadoModel adv = new AdvogadoModel();
		adv.salvaAdvogado(u);
		u.save();

		
		u = new UsuarioModel();
		u.setNome("Consultor 1");
		u.setEmail("consultor1@gmail.com");
		u.setSenha("consultor1");
		u.setCpfCnpj("37514750000191");
		u.setTelResidencial("33463412");
		u.setCelular("85491491");
		ConsultorModel c = new ConsultorModel();
		c.salvaConsultor(u, adv);
		u.save();
		
		u = new UsuarioModel();
		u.setNome("Consultor 2");
		u.setEmail("consultor2@gmail.com");
		u.setSenha("consultor2");
		u.setCpfCnpj("55153028000150");
		u.setTelResidencial("33463412");
		u.setCelular("85491491");
		c = new ConsultorModel();
		c.salvaConsultor(u, adv);
		u.save();
		
		u = new UsuarioModel();
		u.setNome("Consultor 3");
		u.setEmail("consultor3@gmail.com");
		u.setSenha("consultor3");
		u.setCpfCnpj("82524264000109");
		u.setTelResidencial("33463412");
		u.setCelular("85491491");
		c = new ConsultorModel();
		c.salvaConsultor(u, adv);
		u.save();
		
		u = new UsuarioModel();
		u.setNome("Consultor 4");
		u.setEmail("consultor4@gmail.com");
		u.setSenha("consultor4");
		u.setCpfCnpj("71853234000120");
		u.setTelResidencial("33463412");
		u.setCelular("85491491");
		c = new ConsultorModel();
		c.salvaConsultor(u, adv);
		u.save();
		
		
		u = new UsuarioModel();
		u.setNome("Consultor 5");
		u.setEmail("consultor5@gmail.com");
		u.setSenha("consultor5");
		u.setCpfCnpj("69775333000199");
		u.setTelResidencial("33463412");
		u.setCelular("85491491");
		c = new ConsultorModel();
		c.salvaConsultor(u, adv);
		u.save();

	}

	public static void criaTaxaFisicaModelTeste() {
		TaxaFisicaModel t = new TaxaFisicaModel();
		t.setAno(2011);
		t.setMes(2);
		t.setVlrVeiculo(2.28);
		t.save();
	}    
}