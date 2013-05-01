package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.db.jpa.Model;

@Entity
@Table(name = "PROCESSO")
public class ProcessoModel extends Model{
	
	@Column(name="VALOR_COBRADO")
    private long ValorCobrado;
	
	@Column(name="ID_CLIENTE")
	private Long idCliente;
	
	@Transient
	private ClienteModel cliente;
	
	@Transient
	List<DocumentoModel> documentos = null;
	
	@Column(name="DESCRICAO", length=1000)
	private String descricao;
	
	public ClienteModel getCliente () {
		if (this.cliente == null) {
			this.cliente = ClienteModel.findById(this.idCliente);
		}
		return this.cliente;
	}
	
	public List<DocumentoModel> getDocumentosDoProcesso () {
		List<DocumentoModel> list = DocumentoModel.find("idProcesso = ?",this.id).fetch();
		documentos = list;
		return documentos;
	}
	
	public ArrayList<ValorRevisionalModel> getRevisionais () {
		List<ValorRevisionalModel> list = DocumentoModel.find("idProcesso = ?",this.id).fetch();
		return (ArrayList<ValorRevisionalModel>) list;
	}

	public long getValorCobrado() {
		return ValorCobrado;
	}

	public void setValorCobrado(long valorCobrado) {
		ValorCobrado = valorCobrado;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
		getCliente();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setCliente(ClienteModel cliente) {
		this.cliente = cliente;
	}
	
	
}
