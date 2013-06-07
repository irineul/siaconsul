


function removeDoc(idDoc,idDivToHide,idDivToShow, label){
	
	$("#"+idDivToHide).hide();
	var html =
	" <lable class='active'>"+label+"</lable> "+
	" <div class='fileupload fileupload-new' data-provides='fileupload'>"+
	"	<div class='input-append'>"+
    "	<div class='uneditable-input span3'>"+
    " 	<i class='icon-file fileupload-exists'></i>"+ 
    "	<span class='fileupload-preview'></span>"+
    "	</div>"+
    "	<span class='btn btn-file'>"+
    "	<span class='fileupload-new'>Selecione o Arquivo</span>"+
    "	<span class='fileupload-exists'>Alterar</span>"+
    "	<input id='"+idDoc+"' name='"+idDoc+"' type='file' /></span><a href='#' class='btn fileupload-exists' data-dismiss='fileupload'>Remover</a>"+
    " </div> "+
    " </div>";
	
	$("#"+idDivToShow).html(html);
	$("#"+idDivToShow).show();
}


$(document).ready(function() {
	$("#histProcesso").hide();
	
	$('#formProcesso').validate({
	    rules: {
	      banco: {
	        minlength: 5,
	        required: true
	    },
	    dataAberturaProcesso: {
	        required: true
	    },
	    descricao: {
	    	minlength: 10,
	        required: true
	        
	    },
	    numeroProcesso:{
	    	minlength: 1,
	        required: true
	    },
	    procuracaoDoc:{
	    	required: true
	    },
			highlight: function(element) {
				$(element).closest('.control-group').removeClass('success').addClass('error');
			},
			success: function(element) {
				element
				.text('OK!').addClass('valid')
				.closest('.control-group').removeClass('error').addClass('success');
			}
	  }});
});


function setValueTo(value,id){
	$("#"+id).val(value);
}
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

