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
		processoFromDb.setValorCobrado(processo.getValorCobrado());
		processoFromDb.setIdCliente(processo.getIdCliente());
		processoFromDb.setIdConsultor(processo.getIdConsultor());
		processoFromDb.setBanco(processo.getBanco());
		processoFromDb.setDataAberturaProcesso(processo.getDataAberturaProcesso());
		processoFromDb.setTipoProcesso(processo.getTipoProcesso());
		processoFromDb.setIdDeclaracao(processo.getIdDeclaracao());
		processoFromDb.setIdProcuracao(processo.getIdProcuracao());
		processoFromDb.setIdDocCarro(processo.getIdDocCarro());
		processoFromDb.setIdComprovanteResidencia(processo.getIdComprovanteResidencia());
		processoFromDb.setIdRgCpf(processo.getIdRgCpf());
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

}
