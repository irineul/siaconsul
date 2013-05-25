package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
@Table(name = "CLIENTE")
public class ClienteModel extends Model{
	
	@Column(name="ID_CONSULTOR")
	private Long idConsultor;
	
	@Column(name="ID_USUARIO")
    private Long idUsuario;
	
	@Transient
	public UsuarioModel usuario = null;
	
	public ConsultorModel getConsultor() {
		return UsuarioModel.findById(this.idConsultor);
	}
	
	public void salvaCliente(UsuarioModel u, ConsultorModel c) {
		UsuarioModel usuario = u.save();
		idUsuario = usuario.id;
		this.usuario = UsuarioModel.findById(idUsuario);
		idConsultor = c.id;
		this.save();
	}
	
	public ArrayList<ProcessoModel> getProcessos () {
		List<ProcessoModel> list = ProcessoModel.find("idCliente = ?",this.id).fetch();
		return (ArrayList<ProcessoModel>) list;
	}
	
	public UsuarioModel getUsuario () {
		this.usuario = UsuarioModel.findById(idUsuario);

		this.usuario.id = idUsuario;
		return this.usuario;
	}
	
	public long getIsuario(){
		return idUsuario;
	}

}
