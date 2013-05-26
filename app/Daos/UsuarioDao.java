package Daos;

import java.util.ArrayList;
import java.util.List;

import controllers.Consultor;

import models.ClienteModel;
import models.ConsultorModel;
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
		ConsultorModel consultorLogado = Consultor.getConsultorLogado();
		List<UsuarioModel> usuarios = UsuarioModel.find("email = ?", email).fetch();

		ArrayList<UsuarioModel> usuariosDoConsultor = new ArrayList<UsuarioModel>();
		/* Retorno os usuários do consultor logado */
		for(UsuarioModel usuario : usuarios)
		{
			List<ClienteModel> cms = ClienteModel.find("idUsuario = ?", usuario.getId()).fetch();

			if(cms.size() > 0)
			{
				if(cms.get(0).getId() == consultorLogado.getId())
				{
					usuariosDoConsultor.add(usuario);
				}
			}
		}

		return usuariosDoConsultor;
	}

	public List<UsuarioModel> ListarUsuarioByCpfCnpj(String cpfCnpj){
		ConsultorModel consultorLogado = Consultor.getConsultorLogado();
		List<UsuarioModel> usuarios = UsuarioModel.find("cpfCnpj = ?", cpfCnpj).fetch();

		ArrayList<UsuarioModel> usuariosDoConsultor = new ArrayList<UsuarioModel>();
		/* Retorno os usuários do consultor logado */
		for(UsuarioModel usuario : usuarios)
		{
			ClienteModel cm = new ClienteModel();
			cm.setIdUsuario(usuario.id);
			cm.usuario = cm.getUsuario();
			if(cm.getConsultor().getId() == consultorLogado.getId())
			{
				usuariosDoConsultor.add(usuario);
			}
		}

		return usuariosDoConsultor;
	}		

}
