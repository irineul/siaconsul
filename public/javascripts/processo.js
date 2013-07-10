/*Import*/
var imported = document.createElement('script');
imported.src = '/public/javascripts/util.js';
document.head.appendChild(imported);




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
	
	
	
	$(".errorMessage").hide();
	
	$("#insertSubmitButton").click(function() {
		  validateForm("formProcesso");
	});
});


/*
 * 
 * Validations
 */
function validateForm(formId){
	
	var field1 = validateRequired("procuracaoDoc",           "Campo obrigatório");
	var field2 = validateRequired("declaracaoDoc",           "Campo obrigatório");
	var field3 = validateRequired("docCarro"     ,           "Campo obrigatório");
	var field4 = validateRequired("idComprovanteResidencia", "Campo obrigatório");
	var field5 = validateRequired("idRgCpfValidation"	   , "Campo obrigatório");
	
	if (field1 ==1 && field2 ==1 && field3 ==1) {
		document.getElementById(formId).submit();
	}
	
}




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

