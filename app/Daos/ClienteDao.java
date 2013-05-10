package Daos;

import models.ClienteModel;
import models.ConsultorModel;

public class ClienteDao {


	public static ClienteDao self;

	private ClienteDao(){};

	public static ClienteDao getInstance() {
		if (self == null) {
			self = new ClienteDao();
		}
		return self;
	}

	public ClienteModel salvar(ClienteModel cliente){		
		return cliente;
	}
	
	public ClienteModel buscarCliente(long idCliente){
    	return ClienteModel.findById(idCliente);
	}
	
	public int excluir(long idCliente){
    	return ClienteModel.delete("id=?", idCliente);
	}

}
