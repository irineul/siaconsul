package Daos;

import java.util.List;

import models.ProcessoModel;
import models.UsuarioModel;

public class UsuarioDao {


	public static UsuarioDao self;

	private UsuarioDao(){};

	public static UsuarioDao getInstance() {
		if (self == null) {
			self = new UsuarioDao();
		}
		return self;
	}

	public UsuarioModel salvar(UsuarioModel usuario){		
		return usuario;
	}
	
	public UsuarioModel buscarUsuario(long idUsuario){
    	return UsuarioModel.findById(idUsuario);
	}
	
	public int excluir(long idUsuario){
    	return UsuarioModel.delete("id=?", idUsuario);
	}
	
	public List<UsuarioModel> ListarUsuarioByEmail(String email){
		return UsuarioModel.find("email = ?", email).fetch();
	}

	public List<UsuarioModel> ListarUsuarioByCpfCnpj(String cpfCnpj){
		return UsuarioModel.find("cpfCnpj = ?", cpfCnpj).fetch();
	}		

}
