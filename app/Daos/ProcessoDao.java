package Daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.h2.command.dml.Update;

import Enums.ProcessoTipos;

import models.ClienteModel;
import models.ConsultorModel;
import models.DocumentoModel;
import models.ProcessoModel;
import models.ProcessoRespostaModel;

public class ProcessoDao {

	
	public static ProcessoDao self;
	
	private ProcessoDao(){};
	
	/**
	 * 
	 * @return
	 */
	public static ProcessoDao getInstance() {
		if (self == null) {
			self = new ProcessoDao();
		}
		return self;
	}
	
	/**
	 * 
	 * @param processo
	 * @return
	 */
	public ProcessoModel saveOrUpdate(ProcessoModel processo){
		
		if (processo.getId() == null || processo.getId() ==0) {
			salva(processo);
		}
		else {
			edita(processo);
		}
		return processo;
	}

	/**
	 * 
	 * @param processo
	 */
	private void edita(ProcessoModel processo) {
		ProcessoModel processoFromDb = ProcessoModel.findById(processo.getId());
		clone(processo, processoFromDb);
		processoFromDb.save();
	}

	/**
	 * 
	 * @param processo
	 * @param processoFromDb
	 */
	private void clone(ProcessoModel processo, ProcessoModel processoFromDb) {
		processoFromDb.setDescricao(processo.getDescricao());
		// processoFromDb.setValorCobrado(processo.getValorCobrado());
		processoFromDb.setIdCliente(processo.getIdCliente());
		processoFromDb.setIdConsultor(processo.getIdConsultor());
		processoFromDb.setBanco(processo.getBanco());
		processoFromDb.setDataAberturaProcesso(processo.getDataAberturaProcesso());
		processoFromDb.setTipoProcesso(processo.getTipoProcesso());
		
		if (processo.getIdDeclaracao() == null || processo.getIdDeclaracao()==0) {
			processo.getDeclaracaoDeHipos().save();
			processo.setIdDeclaracao(processo.getDeclaracaoDeHipos().getId());
		}
		processoFromDb.setIdDeclaracao(processo.getIdDeclaracao());
		
		
		if (processo.getIdProcuracao() == null || processo.getIdProcuracao()==0) {
			processo.getProcuracao().save();
			processo.setIdProcuracao(processo.getProcuracao().getId());
		}
		processoFromDb.setIdProcuracao(processo.getIdProcuracao());
		
		
		if (processo.getIdDocCarro() == null || processo.getIdDocCarro()==0) {
			processo.getDocCarro().save();
			processo.setIdDocCarro(processo.getDocCarro().getId());
		}
		processoFromDb.setIdDocCarro(processo.getIdDocCarro());
		
		if (processo.getIdComprovanteResidencia() == null || processo.getIdComprovanteResidencia()==0) {
			processo.getComprovanteResidencia().save();
			processo.setIdComprovanteResidencia(processo.getComprovanteResidencia().getId());
		}
		processoFromDb.setIdComprovanteResidencia(processo.getIdComprovanteResidencia());
		
		if (processo.getIdRgCpf() == null || processo.getIdRgCpf()==0) {
			processo.getRgOuCpf().save();
			processo.setIdRgCpf(processo.getRgOuCpf().getId());
		}
		processoFromDb.setIdRgCpf(processo.getIdRgCpf());
	
		processoFromDb.setIsOnBuscaApreencao(processo.getIsOnBuscaApreencao());
		processoFromDb.setIsCasaPropria(processo.getIsCasaPropria());
	}

	/**
	 * 
	 * @param processo
	 */
	private void salva(ProcessoModel processo) {
		DocumentoModel declaracao =  processo.getDeclaracaoDeHipos();
		declaracao.save();
		processo.setIdDeclaracao(declaracao.getId());
		
		DocumentoModel docCarro = processo.getDocCarro();
		docCarro.save();
		processo.setIdDocCarro(docCarro.getId());
		
		DocumentoModel procuracao = processo.getProcuracao();
		procuracao.save();
		processo.setIdProcuracao(procuracao.getId());
		
		DocumentoModel rgOuCpf = processo.getRgOuCpf();
		rgOuCpf.save();
		processo.setIdRgCpf(rgOuCpf.getId());
		
		DocumentoModel comprovanteResidencia = processo.getComprovanteResidencia();
		comprovanteResidencia.save();
		processo.setIdComprovanteResidencia(comprovanteResidencia.getId());
		
		processo.save();
	}
	
	/**
	 * 
	 * @param idProcesso
	 * @return
	 */
	public ProcessoModel buscaProcessoCompleto(Long idProcesso){
		ProcessoModel processo = ProcessoModel.findById(idProcesso);
		carregaDocumentos(processo);
		processo.getCliente().getUsuario();
		List<ProcessoRespostaModel> respostas =  ProcessoRespostaModel.find("idProcesso=?", idProcesso).fetch();
		if (respostas != null && respostas.size() > 0) {
			processo.setResposta(respostas.get(0));
			DocumentoModel anexo = DocumentoModel.findById(processo.getResposta().getIdDocumentoAnexo());
			processo.getResposta().setAnexo(anexo);
		}
		return processo;
	}

	/**
	 * 
	 * @param processo
	 */
	private void carregaDocumentos(ProcessoModel processo) {
		DocumentoModel declaracao = DocumentoModel.findById(processo.getIdDeclaracao());
		processo.setDeclaracaoDeHipos(declaracao);
		
		DocumentoModel docCarro = DocumentoModel.findById(processo.getIdDocCarro());
		processo.setDocCarro(docCarro);
		
		DocumentoModel procuracao = DocumentoModel.findById(processo.getIdProcuracao());
		processo.setProcuracao(procuracao);
		
		DocumentoModel rgCpf = DocumentoModel.findById(processo.getIdRgCpf());
		processo.setRgOuCpf(rgCpf);
		
		DocumentoModel comprovanteResidenca = DocumentoModel.findById(processo.getIdComprovanteResidencia());
		processo.setComprovanteResidencia(comprovanteResidenca);
	}
	
	/**
	 * 
	 * @param idConsultor
	 * @return
	 */
	public List<ProcessoModel> ListarByIdConsultor(Long idConsultor){
		List<ProcessoModel> lista = ProcessoModel.find("idConsultor=?", idConsultor).fetch();
		for(ProcessoModel pm : lista){
			pm.getCliente().getUsuario();
		}
		return lista;
	}
	
	/**
	 * 
	 * @param idAdvogado
	 * @return
	 */
	
	public List<ProcessoModel> ListarByIdAdvogado(long idAdvogado)
	{
		/* Busco os consultores do advogado e busco todos os processos de cada consultor */
		List<ConsultorModel> consultoresAdvogado = ConsultorModel.find("idAdvogado = ? ", idAdvogado).fetch();
		List<ProcessoModel> processosAdvogado = new ArrayList<ProcessoModel>();
		for(ConsultorModel consultor : consultoresAdvogado)
		{
			processosAdvogado.addAll(ProcessoDao.getInstance().ListarByIdConsultor(consultor.getId()));
		}
		
		return processosAdvogado;
	}	

}
