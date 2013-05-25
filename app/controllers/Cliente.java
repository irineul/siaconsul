package controllers;

import java.util.List;

import Daos.ProcessoDao;

import play.mvc.*;

import models.*;
import play.data.validation.Validation;
import Daos.ClienteDao;
import Daos.UsuarioDao;;

public class Cliente extends BaseController {

	/**
	 * 
	 * Carrega os dados do cliente para a tela
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
	public static void index(long id, String tipoPessoa, String nome, String endereco, String email, String telResidencial, String celular, String rg, String cpfCnpj) {
		render(id, tipoPessoa, nome, endereco, email, telResidencial,celular,rg,cpfCnpj);
	}

	public static void editar(long id, String tipoPessoa, String nome, String endereco, String email, String telResidencial, String celular, String rg, String cpfCnpj) {
		render(id, tipoPessoa, nome, endereco, email, telResidencial,celular,rg,cpfCnpj);
	}	


	public static void salvar(long id, String tipoPessoa, String nome, String endereco, String email, String telResidencial, String celular, String rg, String cpfCnpj) {

		/* Valido unique */
		int unique = verificaUnique(cpfCnpj, email);
		if(unique == 1)
		{
			validation.addError("cpfCnpj", "validation.duplicated.cpfCnpj");
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request		
			if(id==0)
			{
				index(0, tipoPessoa, nome,endereco,email, telResidencial,celular,rg,cpfCnpj);
			}
			else
			{
				editar(id, tipoPessoa, nome,endereco,email, telResidencial,celular,rg,cpfCnpj);
			}				
		}
		else if(unique == 2)
		{
			validation.addError("email", "validation.duplicated.email");
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request		
			if(id==0)
			{
				index(0, tipoPessoa, nome,endereco,email, telResidencial,celular,rg,cpfCnpj);
			}
			else
			{
				editar(id, tipoPessoa, nome,endereco,email, telResidencial,celular,rg,cpfCnpj);
			}				
		}
		else
		{
			if(id == 0){
				inserir(tipoPessoa, nome, endereco, email, telResidencial, celular, rg, cpfCnpj);
			}
			else{
				ClienteModel cliente = ClienteDao.getInstance().buscarCliente(id);
				salvarEdit(cliente.getUsuario().id, tipoPessoa, nome, endereco, email, telResidencial, celular, rg, cpfCnpj);
			}
		}


	}

	public static void inserir(String tipoPessoa, String nome, String endereco, String email, String telResidencial, String celular, String rg, String cpfCnpj) {

		validation.required(nome);
		validation.required(endereco);
		validation.required(email);
		validation.required(telResidencial);
		validation.required(celular);
		if(tipoPessoa == "F"){
			validation.required(rg);
		}
		validation.required(cpfCnpj);

		if(validation.hasErrors()){
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request			
			index(0, tipoPessoa, nome,endereco,email, telResidencial,celular,rg,cpfCnpj);
		}
		else{
			UsuarioModel u = fresh(tipoPessoa, nome, endereco, email, telResidencial, celular, rg, cpfCnpj);			
			ClienteModel c = new ClienteModel();
			c.salvaCliente(u, Consultor.getConsultorLogado());
			Application.consultor();
		}

	}

	public static void editarCliente(long idCliente){
		ClienteModel cliente = ClienteDao.getInstance().buscarCliente(idCliente);
		editar(cliente.getId(), cliente.usuario.getTipoPessoa(), cliente.usuario.getNome(), cliente.usuario.getEndereco(), cliente.usuario.getEmail(), cliente.usuario.getTelResidencial(), cliente.usuario.getCelular(), cliente.usuario.getRg(), cliente.usuario.getCpfCnpj());
	}

	public static void excluir(long idCliente){
		ClienteDao.getInstance().excluir(idCliente);
		Application a = new Application();
		a.consultor();

	}

	public static void salvarEdit(long idUsuario, String tipoPessoa, String nome, String endereco, String email, String telResidencial, String celular, String rg, String cpfCnpj) {
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
			editar(idUsuario, tipoPessoa, nome,endereco,email, telResidencial,celular,rg,cpfCnpj);
		}
		else{		
			UsuarioModel u = UsuarioDao.getInstance().buscarUsuario(idUsuario);
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
			u.save();
			Application.consultor();
		}
	}

	public static UsuarioModel fresh(String tpPessoa, String nome, String endereco, String email, String telResidencial, String celular, String rg, String cpfCnpj){
		UsuarioModel u = new UsuarioModel();
		u.setTipoPessoa(tpPessoa);
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
		return u;
	}

	public static void cancelar()
	{
		Application.consultor();
	}

	/* Método que verifica se o post não quebrará nenhuma unique */
	/* 1 = cpfCnpj repetido, 2 = Email repetido */
	private static int verificaUnique(String cpfCnpj, String email)
	{
		List<UsuarioModel> usuarios = UsuarioDao.getInstance().ListarUsuarioByCpfCnpj(cpfCnpj);
		if(usuarios.size() > 0)
		{
			return 1;
		}
		else
		{
			usuarios = UsuarioDao.getInstance().ListarUsuarioByEmail(email);
			if(usuarios.size() > 0)
			{
				return 2;
			}
		}

		return 0;
	}
}
