package controllers;

import play.mvc.*;

import models.*;

public class Cliente extends Controller {

	public static void incluir(String nome, String endereco, String email, String telResidencial, String celular, String rg, String cpfCnpj) {
		UsuarioModel u = new UsuarioModel();
		u.setNome(nome);
		u.setEmail(email);
		u.setCpfCnpj(cpfCnpj.replaceAll(".", "").replaceAll("-", "").replaceAll("/", ""));
		u.setTelResidencial(telResidencial);
		u.setRg(rg);
		u.setCelular(celular);
		u.setEndereco(endereco);
		ClienteModel c = new ClienteModel();
		c.salvaCliente(u, Consultor.getConsultorLogado());
        Application.consultor();
    }
    
}