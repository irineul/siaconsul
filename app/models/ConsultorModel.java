package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
@Table(name = "CONSULTOR")
public class ConsultorModel extends Model{
	
	
	@Column(name="ID_ADVOGADO")
	private Long idAdvogado;
	
	@Column(name="ID_USUARIO")
    private Long idUsuario;
	
	public AdvogadoModel getAdvogado() {
		return AdvogadoModel.findById(this.idAdvogado);
	}
	
	public void salvaConsultor(UsuarioModel u, AdvogadoModel adv) {
		u.save();
		idUsuario = u.id;
		idAdvogado = adv.id;
		this.save();
	}
	
	public ArrayList<ClienteModel> getClientes () {
		List<ClienteModel> list = ClienteModel.find("idConsultor = ?",this.id).fetch();
		/*for(ClienteModel cm : list)
		{
			cm.usuario = UsuarioModel.findById(idUsuario);
		}*/
		return (ArrayList<ClienteModel>) list;
	}
	
	public ArrayList<UsuarioModel> getUsuario () {
		List<UsuarioModel> list = ClienteModel.find("idUsuario = ?",this.id).fetch();
		return (ArrayList<UsuarioModel>) list;
	}
	
	public long getIsuario(){
		return idUsuario;
	}

}
