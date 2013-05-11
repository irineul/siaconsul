package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.db.jpa.Model;
import Enums.ProcessoTipos;

@Entity
@Table(name = "PROCESSO")
public class ProcessoModel extends Model{
	
	@Column(name="VALOR_COBRADO")
    private long ValorCobrado;
	
	@Column(name="ID_CLIENTE")
	private Long idCliente;
	
	@Column(name="BANCO")
	private String banco;
	
	@Column(name="DT_ABERTURA_PROCESSO")
	private Date dataAberturaProcesso;

	@Column(name="TIPO_PROCESSO")
	private ProcessoTipos tipoProcesso;
	
	@Transient
	private DocumentoModel declaracaoDeHipos;

	@Transient
	private DocumentoModel procuracao;
	
	@Transient
	private DocumentoModel docCarro;

	@Transient
	private DocumentoModel comprovanteCarro;

	@Transient
	private DocumentoModel comprovanteResidencia;

	@Transient
	private DocumentoModel identidadeOuCpf;

	@Transient
	private ClienteModel cliente;
	
	@Transient
	List<DocumentoModel> documentosExtras = null;
	
	@Column(name="DESCRICAO", length=1000)
	private String descricao;
	
	public ClienteModel getCliente () {
		if (this.cliente == null) {
			this.cliente = ClienteModel.findById(this.idCliente);
		}
		return this.cliente;
	}
	
	public List<DocumentoModel> getDocumentosExtra () {
		List<DocumentoModel> list = DocumentoModel.find("idProcesso = ?",this.id).fetch();
		documentosExtras = list;
		return documentosExtras;
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

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public Date getDataAberturaProcesso() {
		return dataAberturaProcesso;
	}

	public void setDataAberturaProcesso(Date dataAberturaProcesso) {
		this.dataAberturaProcesso = dataAberturaProcesso;
	}

	public ProcessoTipos getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(ProcessoTipos tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	public DocumentoModel getDeclaracaoDeHipos() {
		return declaracaoDeHipos;
	}

	public void setDeclaracaoDeHipos(DocumentoModel declaracaoDeHipos) {
		this.declaracaoDeHipos = declaracaoDeHipos;
	}

	public DocumentoModel getProcuracao() {
		return procuracao;
	}

	public void setProcuracao(DocumentoModel procuracao) {
		this.procuracao = procuracao;
	}

	public DocumentoModel getDocCarro() {
		return docCarro;
	}

	public void setDocCarro(DocumentoModel docCarro) {
		this.docCarro = docCarro;
	}

	public DocumentoModel getComprovanteCarro() {
		return comprovanteCarro;
	}

	public void setComprovanteCarro(DocumentoModel comprovanteCarro) {
		this.comprovanteCarro = comprovanteCarro;
	}

	public DocumentoModel getComprovanteResidencia() {
		return comprovanteResidencia;
	}

	public void setComprovanteResidencia(DocumentoModel comprovanteResidencia) {
		this.comprovanteResidencia = comprovanteResidencia;
	}

	public DocumentoModel getIdentidadeOuCpf() {
		return identidadeOuCpf;
	}

	public void setIdentidadeOuCpf(DocumentoModel identidadeOuCpf) {
		this.identidadeOuCpf = identidadeOuCpf;
	}
}
