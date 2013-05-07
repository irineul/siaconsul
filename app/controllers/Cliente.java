package controllers;

import play.mvc.*;

import models.*;
import play.data.validation.Validation;

public class Cliente extends Controller {

	
	public static void index(){
		render();
	}
	
	
	public static void incluir(String nome, String endereco, String email, String telResidencial, String celular, String rg, String cpfCnpj) {
		validation.required(nome);
		if(validation.hasErrors()){
	        params.flash(); // add http parameters to the flash scope
	        validation.keep(); // keep the errors for the next request			
			index();
		}
		else{
			UsuarioModel u = new UsuarioModel();
			u.setNome(nome);
			u.setEmail(email);
			String newCpf = cpfCnpj.replace(".", "");
			newCpf = newCpf.replaceAll("-", "");
			newCpf = newCpf.replaceAll("/", "");
			u.setCpfCnpj(newCpf);
			u.setTelResidencial(telResidencial);
			u.setRg(rg);
			u.setCelular(celular);
			u.setEndereco(endereco);			
			ClienteModel c = new ClienteModel();
			c.salvaCliente(u, Consultor.getConsultorLogado());
			Application.consultor();
		}
		render(nome,endereco,email, telResidencial,celular,rg,cpfCnpj);
	}

}