package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Cliente extends Controller {

	public static void incluir(String nome, String email, String cpf, String telefone) {
		UsuarioModel u = new UsuarioModel();
		u.setNome(nome);
		u.setEmail(email);
		u.setCpf(cpf);
		u.setTelefone(telefone);
		ClienteModel c = new ClienteModel();
		c.salvaCliente(u, Consultor.getConsultorLogado());
        Application.consultor();
    }
    
}