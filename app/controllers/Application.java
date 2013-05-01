package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import javax.mail.Session;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void consultor() {
    	ConsultorModel consultor = Consultor.getConsultorLogado();
    	List<ClienteModel> clientes = new ArrayList<ClienteModel>();
    	for(ClienteModel cm : consultor.getClientes()) {
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
    	u.setEmail("douglas@gmail.com");
    	u.setNome("Douglas");
    	u.setSenha("teste");
    	u.setCpf("01942750005");
    	u.setTelefone("82551557");
    	AdvogadoModel adv = new AdvogadoModel();
    	adv.salvaAdvogado(u);
    	u.save();
    	
    	u = new UsuarioModel();
    	u.setNome("Irineu");
    	u.setEmail("irineu@gmail.com");
    	u.setSenha("teste");
    	u.setCpf("01942750006");
    	u.setTelefone("82551557");
    	ConsultorModel c = new ConsultorModel();
    	c.salvaConsultor(u, adv);
    	u.save();
    	
    	u = new UsuarioModel();
    	u.setNome("Mana Chele (Vida Loca)");
    	u.setEmail("manaChele@gmail.com");
    	u.setSenha("teste");
    	u.setCpf("01942750009");
    	u.setTelefone("82551557");
    	ClienteModel cli = new ClienteModel();
    	cli.salvaCliente(u, c);
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