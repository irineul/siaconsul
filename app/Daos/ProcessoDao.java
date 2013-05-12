package Daos;

import java.util.ArrayList;
import java.util.List;

import Enums.ProcessoTipos;

import models.ConsultorModel;
import models.ProcessoModel;

public class ProcessoDao {

	
	public static ProcessoDao self;
	
	private ProcessoDao(){};
	
	public static ProcessoDao getInstance() {
		if (self == null) {
			self = new ProcessoDao();
		}
		return self;
	}
	
	public ProcessoModel salvar(ProcessoModel processo){
		processo.save();
		processo.getProcuracao().setIdProcesso(processo.getId());
		processo.getProcuracao().save();
		processo.getDeclaracaoDeHipos().setIdProcesso(processo.getId());
		processo.getDeclaracaoDeHipos().save();
		return processo;
	}
	
	public List<ProcessoModel> ListarByIdConsultor(Long idConsultor){
		List<ProcessoModel> lista = ProcessoModel.find("idConsultor=?", idConsultor).fetch();
		
		for(ProcessoModel pm : lista){
			/* carrega os atributos do objeto cliente para apresentar na tela */
			pm.getCliente().getUsuario();
		}
		
		return lista;
	}

}
