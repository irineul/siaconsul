package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import others.Calculo;
import others.Util;

import models.ClienteModel;
import models.ConsultorModel;
import models.DocumentoModel;
import models.ProcessoModel;
import models.ProcessoRespostaModel;
import play.libs.MimeTypes;
import play.modules.s3blobs.S3Blob;
import Daos.ProcessoDao;
import Enums.ProcessoTipos;

public class Processo extends BaseController {

	/** 
	 * Locale Brasileiro 
	 */  
	private static final Locale BRAZIL = new Locale("pt","BR");  
	/** 
	 * Símbolos especificos do Real Brasileiro 
	 */  
	private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRAZIL);  
	/** 
	 * Mascara de dinheiro para Real Brasileiro 
	 */  
	public static final DecimalFormat DINHEIRO_REAL = new DecimalFormat("¤ ###,###,##0.00",REAL);	

	public static void lista(String successMsg) {
		String tipoUsuarioLogado = others.Util.getTipousuarioLogado();
		/* Se o usuário for do tipo advgoado, lista os processos de todos consultores dele */
		if(tipoUsuarioLogado != null && tipoUsuarioLogado.equals("A"))
		{
			String idAdvogado = others.Util.getIdAdvogado();
			if(idAdvogado != null)
			{
				List<ProcessoModel> processos = ProcessoDao.getInstance().ListarByIdAdvogado(Long.parseLong(idAdvogado));
				render(processos, successMsg, tipoUsuarioLogado); 
			}
		}
		/* O usuário logado é consultor, lista seus processos */
		else
		{
	    	ConsultorModel consultor = others.Util.getConsultorLogado();
	    	List<ProcessoModel> processos = ProcessoDao.getInstance().ListarByIdConsultor(consultor.getId());
	    	render(processos, successMsg, tipoUsuarioLogado);			
		}
    }

	/**
	 * 
	 * @param idProcesso
	 * @throws FileNotFoundException
	 */
	public static void resposta(Long idProcesso) throws FileNotFoundException {
		String operacao = params.get("OPERACAO", String.class);
		if ( "INCLUIR".equals(operacao)){
			ProcessoRespostaModel resposta = new ProcessoRespostaModel();
			idProcesso = params.get("resposta.idProcesso", Long.class);
			resposta.setIdProcesso(idProcesso);
			String comentario = params.get("resposta.comentario", String.class);
			resposta.setComentario(comentario);
			DocumentoModel anexo = getDocumentoFromRequest("resposta.anexo", "resposta.anexo");
			anexo.save();
			resposta.setAnexo(anexo);
			resposta.setData(new Date());
			resposta.setIdDocumentoAnexo(anexo.getId());
			resposta.save();
	    	lista("Resposta enviada com sucesso");
		}else{
			ProcessoRespostaModel resposta = new ProcessoRespostaModel();
			resposta.setIdProcesso(idProcesso);
			render(resposta);
		}
    }
	
	/**
	 * 
	 * @param idCliente
	 * @throws FileNotFoundException 
	 */
    public static void carrega(Long idCliente, ProcessoModel processo, Long idProcesso) throws FileNotFoundException {
    
    	List<String> erros = new ArrayList<String>();
    	String operacao = params.get("operacao", String.class);
    	if ("INCLUIR".equals(operacao)) {
    		
    		processo = montaProcessoFromRequest();
	    	processo.setTipoProcesso(ProcessoTipos.VEICULO);
	    	ProcessoDao.getInstance().saveOrUpdate(processo);
	    	lista("Processo criado com sucesso!");
	    	
    	}else{
 
    		ClienteModel cliente =ClienteModel.findById(idCliente);
	    	if (processo == null) {
	    		processo = new ProcessoModel();
	    	}
	    	processo.setIdCliente(idCliente);
	    	processo.setCliente(cliente);
	    	if (processo.getDataAberturaProcesso() == null) {
	    		processo.setDataAberturaProcesso(new Date());
	    	}
	    	if (processo.getVlrJurosNovo()== null) {
	    		setCalculosToProcesso(processo);
	    	}
	    	
			Double rendaMensal = cliente.getRendaMensal();
			
			/* Se foi informado a renda mensal, formato-a para apresentação na tela */
			if(rendaMensal != null)
			{
				String rendaMensalAp = Util.mascaraDinheiro(rendaMensal, DINHEIRO_REAL);
				cliente.setRendaMensalApresentacao(rendaMensalAp);
			}
			
	    	render(cliente, erros, processo, idProcesso);
    	}
    }

    
    
    /**
	 * 
	 * @param idProcesso
	 * @throws FileNotFoundException 
	 */
    public static void editar(Long id) throws FileNotFoundException {
    		ProcessoModel processo = ProcessoDao.getInstance().buscaProcessoCompleto(id);
    		carrega(processo.getIdCliente(), processo,id);
    }
    
    public static void excluir(Long id) throws FileNotFoundException {
		ProcessoModel.delete("id=?", id);
		lista("Processo Removido com sucesso!");
    }
    
    /**
	 * 
	 * @param idProcesso
	 * @throws FileNotFoundException 
	 */
    public static void detalhar(Long id) throws FileNotFoundException {
    		ProcessoModel processo = ProcessoDao.getInstance().buscaProcessoCompleto(id);
    		String tipoUsuarioLogado = others.Util.getTipousuarioLogado();
    		
        	List<String> erros = new ArrayList<String>();
    		ClienteModel cliente = ClienteModel.findById(processo.getCliente().getId());
	    	if (processo.getDataAberturaProcesso() == null) {
	    		processo.setDataAberturaProcesso(new Date());
	    	}
	    	if (processo.getVlrJurosNovo()== null) {
	    		setCalculosToProcesso(processo);
	    	}
	    	
			Double rendaMensal = cliente.getRendaMensal();
			
			/* Se foi informado a renda mensal, formato-a para apresentação na tela */
			if(rendaMensal != null)
			{
				String rendaMensalAp = Util.mascaraDinheiro(rendaMensal, DINHEIRO_REAL);
				cliente.setRendaMensalApresentacao(rendaMensalAp);
			}
			
	    	render(cliente, erros, processo, processo.id, tipoUsuarioLogado);
    }    


	/**
	 * Pega todos os parametros da tela e seta no 
	 * objeto ProcessoModel
	 * 
	 * @return
	 * @throws FileNotFoundException 
	 */
	private static ProcessoModel montaProcessoFromRequest() throws FileNotFoundException {
		
		ProcessoModel processo = new ProcessoModel();
		
		ConsultorModel cm = others.Util.getConsultorLogado();
    	processo.setIdConsultor(cm.getId());
		processo.id = params.get("processo.id", Long.class);
    	processo.setIdCliente(params.get("idCliente", Long.class));
    	processo.setDescricao(params.get("descricao", String.class));
    	processo.setBanco(params.get("banco", String.class));
    	processo.setDataAberturaProcesso(params.get("dataAberturaProcesso", Date.class));
    	
    	carregaDocumentos(processo);
    	
    	setCalculosToProcesso(processo);
    	
		return processo;
	}

	private static void setCalculosToProcesso(ProcessoModel processo) {
		String vlrJurosAntigo = params.get("calcVlrJurosAtual", String.class);
    	processo.setVlrJurosAntigo(vlrJurosAntigo);
    	
    	String calcVlrJurosNovo = params.get("calcVlrJurosNovo", String.class);
    	processo.setVlrJurosNovo(calcVlrJurosNovo);
    	
    	String txtCalcVlrPagoIndevido = params.get("txtCalcVlrPagoIndevido", String.class);
    	processo.setVlrPagoIndevido(txtCalcVlrPagoIndevido);
    	
    	String txtCalcVlrNovaParcela = params.get("txtCalcVlrNovaParcela", String.class);
    	processo.setVlrNovaParcela(txtCalcVlrNovaParcela);
    	
    	/* Irineu - Adicionando campos sobre detalhes do financiamento */
    	String txtVlrFinanciado = params.get("vlrFinanciadoProcesso", String.class);
    	processo.setVlrFinanciado(txtVlrFinanciado);
    	
    	String txtQtdParcelas = params.get("qtdParcelasProcesso", String.class);
    	processo.setQtdParcelas(txtQtdParcelas);
    	
    	String txtVlrAtualParcela = params.get("vlrParcelaAtualProcesso", String.class);
    	processo.setVlrAtualParcela(txtVlrAtualParcela);
    	
    	String txtQtdParcelasPagas = params.get("qtdParcelasPagasProcesso", String.class);
    	processo.setQtdParcelasPagas(txtQtdParcelasPagas);    	
	}

	/**
	 * 
	 * @param processo
	 * @throws FileNotFoundException
	 */
	private static void carregaDocumentos(ProcessoModel processo) throws FileNotFoundException {
		
		DocumentoModel procuracao = getDocumentoFromRequest("idDocumentoProcuracao","procuracaoDoc");
    	if (procuracao == null) {
    		processo.setIdProcuracao(params.get("processo.idProcuracao", Long.class));
    		procuracao = DocumentoModel.findById(processo.getIdProcuracao());
    	}
    	processo.setProcuracao(procuracao);
    	processo.setIdProcuracao(procuracao.getId());
    	
    	DocumentoModel declaracao = getDocumentoFromRequest("idDocumentoDeclaracao", "declaracaoDoc");
    	if (declaracao == null) {
    		processo.setIdDeclaracao(params.get("processo.idDeclaracao", Long.class));
    		declaracao = DocumentoModel.findById(processo.getIdDeclaracao());
    	}
    	processo.setDeclaracaoDeHipos(declaracao);
    	processo.setIdDeclaracao(declaracao.getId());
    	
    	DocumentoModel docCarro = getDocumentoFromRequest("idDocumentoCarro", "docCarro");
    	if (docCarro == null) {
    		processo.setIdDocCarro (params.get("processo.idDocCarro", Long.class));
    		docCarro = DocumentoModel.findById(processo.getIdDocCarro());
    	}
    	processo.setDocCarro(docCarro);
    	processo.setIdDocCarro(docCarro.getId());
    	
    	DocumentoModel rgOuCpf = getDocumentoFromRequest("idRgCpf", "idRgCpf");
    	if (rgOuCpf == null) {
    		processo.setIdRgCpf (params.get("processo.idRgCpf", Long.class));
    		rgOuCpf = DocumentoModel.findById(processo.getIdRgCpf());
    	}
    	processo.setIdentidadeOuCpf(rgOuCpf);
    	processo.setIdRgCpf(rgOuCpf.getId());
    	
    	DocumentoModel comprovanteResidencia = getDocumentoFromRequest("idComprovanteResidencia", "idComprovanteResidencia");
    	if (comprovanteResidencia == null) {
    		processo.setIdComprovanteResidencia(params.get("processo.idComprovanteResidencia", Long.class));
    		comprovanteResidencia = DocumentoModel.findById(processo.getIdComprovanteResidencia());
    	}
    	processo.setComprovanteResidencia(comprovanteResidencia);
    	processo.setIdComprovanteResidencia(comprovanteResidencia.getId());
	}


	/**
	 * 
	 * @param id
	 * @param fieldName
	 * @return
	 * @throws FileNotFoundException 
	 */
	private static DocumentoModel getDocumentoFromRequest(String id, String fieldName) throws FileNotFoundException {
		DocumentoModel dm = null;
		Long idProcuracao = params.get(id, Long.class);
    	File arq = params.get(fieldName, File.class);
    	if ( (idProcuracao == null || idProcuracao == 0) && arq != null ){
	    	dm = new DocumentoModel();
	    	dm.comment=arq.getName();
	    	S3Blob file = new S3Blob();
	        file.set(new FileInputStream(arq), MimeTypes.getContentType(arq.getName()));
	    	dm.file = file;
	    	
    	} else if (idProcuracao != null) {
    		dm = DocumentoModel.findById(idProcuracao);
    	}
    	return dm;
	}
	
	public static void downloadFile(long id)
	  {
	    DocumentoModel doc = DocumentoModel.findById(id);
	    notFoundIfNull(doc);
	    response.setContentTypeIfNotSet(doc.file.type());
	    renderBinary(doc.file.get());
	  } 

    
}