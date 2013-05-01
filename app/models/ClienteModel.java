package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.db.jpa.Model;

@Entity
@Table(name = "CLIENTE")
public class ClienteModel extends Model{
	
	@Column(name="ID_CONSULTOR")
	private Long idConsultor;
	
	@Column(name="ID_USUARIO")
    private Long idUsuario;
	
	@Transient
	private UsuarioModel usuario = null;
	
	public ConsultorModel getConsultor() {
		return ConsultorModel.findById(this.idConsultor);
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
		if (this.usuario ==null) {
			this.usuario = UsuarioModel.findById(idUsuario);
		}
		return this.usuario;
	}

}
