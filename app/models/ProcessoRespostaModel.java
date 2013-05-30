package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity
@Table(name = "PROCESSO_RESPOSTA")
public class ProcessoRespostaModel extends Model{

	@Unique
	@Column(name="ID_PROCESSO")
    private Long idProcesso;
	
	@Column(name="COMENTARIO")
	private String comentario;

	@Column(name="ID_DOCUMENTO")
	private Long idDocumentoAnexo;

	@Transient
	private DocumentoModel anexo;
	
	@Column(name="DATA")
	private Date data;

	public Long getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Long getIdDocumentoAnexo() {
		return idDocumentoAnexo;
	}

	public void setIdDocumentoAnexo(Long idDocumentoAnexo) {
		this.idDocumentoAnexo = idDocumentoAnexo;
	}

	public DocumentoModel getAnexo() {
		return anexo;
	}

	public void setAnexo(DocumentoModel anexo) {
		this.anexo = anexo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
