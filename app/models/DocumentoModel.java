package models;

import java.io.File;
import java.util.Date;
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
	private Date data;
	
	@Column(name="ARQUIVO")
	private File arquivo;
	
	public ClienteModel getProcesso() {
		return GenericModel.findById(this.idProcesso);
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public File getFile() {
		return arquivo;
	}

	public void setFile(File file) {
		this.arquivo = file;
		System.out.println("Arquivo adicionado:"+ file.getName());
	}

	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}
	
}
