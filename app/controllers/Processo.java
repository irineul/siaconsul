package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.ClienteModel;
import models.ConsultorModel;
import models.DocumentoModel;
import models.ProcessoModel;
import play.libs.MimeTypes;
import play.modules.s3blobs.S3Blob;
import Daos.ProcessoDao;
import Enums.ProcessoTipos;

public class Processo extends BaseController {
	

	public static void lista() {
    	ConsultorModel consultor = others.Util.getConsultorLogado();
    	List<ProcessoModel> processos = ProcessoDao.getInstance().ListarByIdConsultor(consultor.getId());
    	render(processos);
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
    	}
    	ClienteModel cliente =ClienteModel.findById(idCliente);
    	if (processo == null) {
    		processo = new ProcessoModel();
    	}
    	if (processo.getDataAberturaProcesso() == null) {
    		processo.setDataAberturaProcesso(new Date());

    	}
    	
    	render(cliente, erros, processo, idProcesso);
    }

    
    
    /**
	 * 
	 * @param idCliente
	 * @throws FileNotFoundException 
	 */
    public static void editar(Long id) throws FileNotFoundException {
    		ProcessoModel processo = ProcessoDao.getInstance().buscaProcessoCompleto(id);
    		System.out.println("########### antes Processo.id " + processo.getId()  );
    		carrega(processo.getIdCliente(), processo,id);
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
    	
		return processo;
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
    	
    	DocumentoModel declaracao = getDocumentoFromRequest("idDocumentoDeclaracao", "declaracaoDoc");
    	if (declaracao == null) {
    		processo.setIdDeclaracao(params.get("processo.idDeclaracao", Long.class));
    		declaracao = DocumentoModel.findById(processo.getIdDeclaracao());
    	}
    	processo.setDeclaracaoDeHipos(declaracao);
    	
    	DocumentoModel docCarro = getDocumentoFromRequest("idDocumentoCarro", "docCarro");
    	if (docCarro == null) {
    		processo.setIdDocCarro (params.get("processo.idDocCarro", Long.class));
    		docCarro = DocumentoModel.findById(processo.getIdDocCarro());
    	}
    	processo.setDocCarro(docCarro);
    	
    	DocumentoModel rgOuCpf = getDocumentoFromRequest("idRgCpf", "idRgCpf");
    	if (rgOuCpf == null) {
    		processo.setIdRgCpf (params.get("processo.idRgCpf", Long.class));
    		rgOuCpf = DocumentoModel.findById(processo.getIdRgCpf());
    	}
    	processo.setIdentidadeOuCpf(rgOuCpf);
    	
    	DocumentoModel comprovanteResidencia = getDocumentoFromRequest("idComprovanteResidencia", "idComprovanteResidencia");
    	if (comprovanteResidencia == null) {
    		processo.setIdComprovanteResidencia(params.get("processo.idComprovanteResidencia", Long.class));
    		comprovanteResidencia = DocumentoModel.findById(processo.getIdComprovanteResidencia());
    	}
    	processo.setComprovanteResidencia(comprovanteResidencia);
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
	    	dm.setArquivoNome(arq.getName());
	    	dm.setData(new Date());
	    	S3Blob file = new S3Blob();
	        file.set(new FileInputStream(arq), MimeTypes.getContentType(arq.getName()));
	    	dm.setFile(file);
    	} else if (idProcuracao != null) {
    		dm = DocumentoModel.findById(idProcuracao);
    	}
    	return dm;
	}
	
	public static void downloadFile(long id)
	  {
	    DocumentoModel doc = DocumentoModel.findById(id);
	    notFoundIfNull(doc);
	    response.setContentTypeIfNotSet(doc.getFile().type());
	    renderBinary(doc.getFile().get());
	  } 

    
}