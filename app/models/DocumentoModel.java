package models;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;
import play.modules.s3blobs.S3Blob;

@Entity
@Table(name = "DOCUMENTO")
public class DocumentoModel extends Model{
	
	
	@Column(name="DATA")
	private Date data;
	
	@Column(name="ARQUIVO_NOME")
	private String arquivoNome;
	
	@Column(name="ARQUIVO")
	private S3Blob arquivo;
	

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public S3Blob getFile() {
		return arquivo;
	}

	public void setFile(S3Blob file) {
		this.arquivo = file;
	}

	public String getArquivoNome() {
		return arquivoNome;
	}

	public void setArquivoNome(String arquivoNome) {
		this.arquivoNome = arquivoNome;
	}

	public S3Blob getArquivo() {
		return arquivo;
	}

	public void setArquivo(S3Blob arquivo) {
		this.arquivo = arquivo;
	}

	
}
