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

import play.db.jpa.Model;

@Entity
@Table(name = "ADVOGADO")
public class AdvogadoModel extends Model{
	
	@Column(name="ID_USUARIO")
    private long idUsuario;
	
	public void salvaAdvogado(UsuarioModel u) {
		UsuarioModel usuario = u.save();
		idUsuario = usuario.id;
		this.save();
	}

	public ArrayList<UsuarioModel> getUsuario () {
		List<UsuarioModel> list = UsuarioModel.find("idUsuario = ?",this.id).fetch();
		return (ArrayList<UsuarioModel>) list;
	}

}
