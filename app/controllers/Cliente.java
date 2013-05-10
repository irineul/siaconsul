package controllers;

import java.util.List;

import Daos.ProcessoDao;

import play.mvc.*;

import models.*;
import play.data.validation.Validation;
import Daos.ClienteDao;

public class Cliente extends Controller {
	
	
	/**
	 * 
	 * Carrega a tela de inclusão
	 * 
	 * @param nome
	 * @param endereco
	 * @param email
	 * @param telResidencial
	 * @param celular
	 * @param rg
	 * @param cpfCnpj
	 * 
	 */
    public static void index(String nome, String endereco, String email, String telResidencial, String celular, String rg, String cpfCnpj) {
    	render(nome,endereco,email, telResidencial,celular,rg,cpfCnpj);
    }
	
	public static void inserir(String nome, String endereco, String email, String telResidencial, String celular, String rg, String cpfCnpj) {
		
		validation.required(nome);
		validation.required(endereco);
		validation.required(email);
		validation.required(telResidencial);
		validation.required(celular);
		validation.required(rg);
		validation.required(cpfCnpj);
		
		if(validation.hasErrors()){
	        params.flash(); // add http parameters to the flash scope
	        validation.keep(); // keep the errors for the next request			
	        index(nome,endereco,email, telResidencial,celular,rg,cpfCnpj);
			//index();
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
		
	}

	public static void editar(long idCliente){
		ClienteModel cliente = ClienteDao.getInstance().buscarCliente(idCliente);
		index(cliente.usuario.getNome(), cliente.usuario.getEndereco(), cliente.usuario.getEmail(), cliente.usuario.getTelResidencial(), cliente.usuario.getCelular(), cliente.usuario.getRg(), cliente.usuario.getCpfCnpj());
	}
	
	public static void excluir(long idCliente){
		ClienteDao.getInstance().excluir(idCliente);
		Application a = new Application();
		a.consultor();
		
		
	}	
	
	
}