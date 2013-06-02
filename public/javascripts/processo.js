

function deleteFile(id) {
	if(confirm("Você realmente deseja excluir o arquivo?")){
		
	}
}

$(document).ready(function() {
	$("#histProcesso").hide();
});

function showHistProc(){
	$("#dadosProcesso").hide();
	$("#histProcessoLi").attr("class","active");
	$("#dadosProcessoLi").attr("class","");
	$("#histProcesso").show();
}

function showDadosProc(){
	$("#histProcesso").hide();
	$("#dadosProcessoLi").attr("class","active");
	$("#histProcessoLi").attr("class","");
	$("#dadosProcesso").show();
}

function cancelar(){
	var url = "/processo/lista"; 
	$(location).attr('href',url);
}

function responderProcesso(idProcesso){
	var url = "/processo/resposta?idProcesso=" + idProcesso; 
	$(location).attr('href',url);
}

