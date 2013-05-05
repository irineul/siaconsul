package models;

import java.io.File;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
@Table(name = "DOCUMENTO")
public class DocumentoModel extends Model{
	
	
	@Column(name="ID_PROCESSO")
	private Long idProcesso;
	
	@Column(name="DATA")
	private GregorianCalendar data;
	
	@Column(name="ARQUIVO")
	private File file;
	
	public ClienteModel getProcesso() {
		return GenericModel.findById(this.idProcesso);
	}

	public GregorianCalendar getData() {
		return data;
	}

	public void setData(GregorianCalendar data) {
		this.data = data;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}
	
}
