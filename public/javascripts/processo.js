

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
