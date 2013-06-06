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
	
	@Column(name="ID_CONSULTOR")
	private Long idConsultor;
	
	@Column(name="BANCO")
	private String banco;
	
	@Column(name="DT_ABERTURA_PROCESSO")
	private Date dataAberturaProcesso;

	@Column(name="TIPO_PROCESSO")
	private ProcessoTipos tipoProcesso;
	
	@Column(name="ID_DECLARACAO")
	private Long idDeclaracao;

	@Transient
	private DocumentoModel declaracaoDeHipos;

	@Column(name="ID_PROCURACAO")
	private Long idProcuracao;
	
	@Transient
	private DocumentoModel procuracao;
	
	@Column(name="ID_DOC_CARRO")
	private Long idDocCarro;
	
	@Transient
	private DocumentoModel docCarro;

	@Column(name="ID_COMPROVANTE_RESIDENCIA")
	private Long idComprovanteResidencia;
	
	@Transient
	private DocumentoModel comprovanteResidencia;

	@Column(name="ID_RG_CPF")
	private Long idRgCpf;
	
	@Transient
	private DocumentoModel rgOuCpf;

	@Transient
	private ClienteModel cliente;
	
	@Transient
	List<DocumentoModel> documentosExtras = null;
	
	@Column(name="DESCRICAO", length=1000)
	private String descricao;
	
	@Column(name="VALOR_NOVA_PARCELA")
	public String vlrNovaParcela;
	
	@Column(name="VALOR_NOVO_JUROS")
	public String vlrJurosNovo;
	
	@Column(name="VALOR_JUROS_ANTIGO")
	public String vlrJurosAntigo;
	
	@Column(name="VALOR_PAGO_INDEVIDO")
	public String vlrPagoIndevido;
	
	@Column(name="NUMERO_PROCESSO")
	public String numeroProcesso;

	@Column(name="IN_BUSCA_APREENCAO")
	public Boolean isOnBuscaApreencao;
	
	@Column(name="IN_CASA_PROPRIA")
	public Boolean isCasaPropria;
	
	@Transient
	private ProcessoRespostaModel resposta;
	
	public Boolean getIsOnBuscaApreencao() {
		return isOnBuscaApreencao;
	}
	public void setIsOnBuscaApreencao(Boolean isOnBuscaApreencao) {
		this.isOnBuscaApreencao = isOnBuscaApreencao;
	}
	public Boolean getIsCasaPropria() {
		return isCasaPropria;
	}
	public void setIsCasaPropria(Boolean isCasaPropria) {
		this.isCasaPropria = isCasaPropria;
	}
	public ProcessoRespostaModel getResposta() {
		return resposta;
	}
	public void setResposta(ProcessoRespostaModel resposta) {
		this.resposta = resposta;
	}
	public String getVlrNovaParcela() {
		return vlrNovaParcela;
	}
	public void setVlrNovaParcela(String vlrNovaParcela) {
		this.vlrNovaParcela = vlrNovaParcela;
	}
	public String getVlrJurosNovo() {
		return vlrJurosNovo;
	}
	public void setVlrJurosNovo(String vlrJurosNovo) {
		this.vlrJurosNovo = vlrJurosNovo;
	}
	public String getVlrJurosAntigo() {
		return vlrJurosAntigo;
	}
	public void setVlrJurosAntigo(String vlrJurosAntigo) {
		this.vlrJurosAntigo = vlrJurosAntigo;
	}
	public String getVlrPagoIndevido() {
		return vlrPagoIndevido;
	}
	public void setVlrPagoIndevido(String vlrPagoIndevido) {
		this.vlrPagoIndevido = vlrPagoIndevido;
	}
	
	public ClienteModel getCliente () {
		this.cliente = ClienteModel.findById(this.idCliente);
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

	public DocumentoModel getComprovanteResidencia() {
		return comprovanteResidencia;
	}

	public void setComprovanteResidencia(DocumentoModel comprovanteResidencia) {
		this.comprovanteResidencia = comprovanteResidencia;
	}

	public DocumentoModel getIdentidadeOuCpf() {
		return rgOuCpf;
	}

	public void setIdentidadeOuCpf(DocumentoModel identidadeOuCpf) {
		this.rgOuCpf = identidadeOuCpf;
	}

	public Long getIdConsultor() {
		return idConsultor;
	}

	public void setIdConsultor(Long idConsultor) {
		this.idConsultor = idConsultor;
	}

	public List<DocumentoModel> getDocumentosExtras() {
		return documentosExtras;
	}

	public void setDocumentosExtras(List<DocumentoModel> documentosExtras) {
		this.documentosExtras = documentosExtras;
	}
	
	public Long getIdDeclaracao() {
		return idDeclaracao;
	}

	public void setIdDeclaracao(Long idDeclaracao) {
		this.idDeclaracao = idDeclaracao;
	}

	public Long getIdProcuracao() {
		return idProcuracao;
	}

	public void setIdProcuracao(Long idProcuracao) {
		this.idProcuracao = idProcuracao;
	}

	public Long getIdDocCarro() {
		return idDocCarro;
	}

	public void setIdDocCarro(Long idDocCarro) {
		this.idDocCarro = idDocCarro;
	}

	public Long getIdComprovanteResidencia() {
		return idComprovanteResidencia;
	}

	public void setIdComprovanteResidencia(Long idComprovanteResidencia) {
		this.idComprovanteResidencia = idComprovanteResidencia;
	}

	public Long getIdRgCpf() {
		return idRgCpf;
	}

	public void setIdRgCpf(Long idRgCpf) {
		this.idRgCpf = idRgCpf;
	}

	public DocumentoModel getRgOuCpf() {
		return rgOuCpf;
	}

	public void setRgOuCpf(DocumentoModel rgOuCpf) {
		this.rgOuCpf = rgOuCpf;
	}
	public String getNumeroProcesso() {
		return numeroProcesso;
	}
	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}
}
