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
    
    public static void consultor() {
    	ConsultorModel consultor = Consultor.getConsultorLogado();
    	List<ClienteModel> clientes = new ArrayList<ClienteModel>();
    	for(ClienteModel cm : consultor.getClientes()) {
    		if(cm.usuario.getCpfCnpj() != null && !cm.usuario.getCpfCnpj().isEmpty()){
    			cm.usuario.setCpfCnpj(Util.formataCpfCnpj(cm.usuario.getCpfCnpj()));
    		}
    		clientes.add(cm);
    	}
        render(clientes);
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
    
    public static void advogado() {
        render();
    }

    public static void login(String email, String senha) {
    	
    	List<UsuarioModel> result = UsuarioModel.find("email = ? And senha = ?", email, senha).fetch();

    	if (result != null && result.size() > 0) {
    		UsuarioModel usuario = result.get(0);
    		List<AdvogadoModel> advogado = AdvogadoModel.find("idUsuario=?", usuario.id).fetch();
    		if (advogado != null && advogado.size() > 0) {
    			play.mvc.Scope.Session.current().put("idUsuario",advogado.get(0).id);
    			advogado();
    		}
    		
    		List<ConsultorModel> consultor = ConsultorModel.find("idUsuario=?", usuario.id).fetch();
    		if (consultor != null && consultor.size() > 0) {
    			play.mvc.Scope.Session.current().put("idUsuario",consultor.get(0).id);
    			consultor();
    		}
    		
    	}
		//TODO: mostrar erro na tela;
		index();
    }
    
    public static void criaUsuarioTeste() {
    	UsuarioModel u = new UsuarioModel();
    	u.setEmail("uauhahua@gmail.com");
    	u.setNome("auhuahuaa");
    	u.setSenha("teste");
    	u.setCpfCnpj("01942750005");
    	u.setEndereco("Rua blalablabla, viamão, rs");
    	u.setTelResidencial("33331212");
    	u.setCelular("12345678");
    	AdvogadoModel adv = new AdvogadoModel();
    	adv.salvaAdvogado(u);
    	u.save();
    	
    	u = new UsuarioModel();
    	u.setNome("Irineu");
    	u.setEmail("irineu@gmail.com");
    	u.setSenha("teste");
    	u.setCpfCnpj("01942750006");
    	u.setTelResidencial("33463412");
    	u.setCelular("85491491");
    	ConsultorModel c = new ConsultorModel();
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