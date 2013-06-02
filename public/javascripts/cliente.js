$(document).ready(function() {

	// Validate
	// http://bassistance.de/jquery-plugins/jquery-plugin-validation/
	// http://docs.jquery.com/Plugins/Validation/
	// http://docs.jquery.com/Plugins/Validation/validate#toptions
	
	$("#rendaMensal").maskMoney({showSymbol:true, symbol:"R$", decimal:",", thousands:"."});
	
		$('#formCliente').validate({
	    rules: {
	      nome: {
	        minlength: 5,
	        required: true
	      },
	      profissao: {
		        required: true
		  },	      
	      endereco: {
	        minlength: 5,
	        required: true
	      },
	      email: {
	        required: true,
	        email: true
	      },
	      rg: {
	        required: true
	      },
		 cpfCnpj: {
		 		required: true, cpf:function(element) {
		            if($("#tipoPessoa").val() == "F"){ return true; }
		         }, cnpj:function(element) {
		            if($("#tipoPessoa").val() == "J"){ return true; }
		         }
		  },
	      telResidencial: {
	        required: true
	      },
	      celular: {
	        required: true
	      },
	      rendaMensal: {
	    	  money: true
		  },
	    },
			highlight: function(element) {
				$(element).closest('.control-group').removeClass('success').addClass('error');
			},
			success: function(element) {
				element
				.text('OK!').addClass('valid')
				.closest('.control-group').removeClass('error').addClass('success');
			}
	  });
	  	
	$("#telResidencial").mask("(99) 9999-9999?9")  
        .on('focusout', function (event) {  
            var target, phone, element;  
            target = (event.currentTarget) ? event.currentTarget : event.srcElement;  
            phone = target.value.replace(/\D/g, '');  
            element = $(target);  
            element.unmask();  
            if(phone.length > 10) {  
                element.mask("(99) 99999-999?9");  
            } else {  
                element.mask("(99) 9999-9999?9");  
            }  
        }); 
	$("#celular").mask("(99) 9999-9999?9")  
        .on('focusout', function (event) {  
            var target, phone, element;  
            target = (event.currentTarget) ? event.currentTarget : event.srcElement;  
            phone = target.value.replace(/\D/g, '');  
            element = $(target);  
            element.unmask();  
            if(phone.length > 10) {  
                element.mask("(99) 99999-999?9");  
            } else {  
                element.mask("(99) 9999-9999?9");  
            }  
        }); 
});

function modificaMascara(tipoPessoa){
	$("#tipoPessoa").val(tipoPessoa);
	ajustaForm(tipoPessoa);
}

function mantemForm(tpPessoa){
	if(tpPessoa == "F"){
		$("#tpPessoaJuridica").removeClass("btn active");
	}
	else{
	}
	ajustaForm(tpPessoa);
}


function ajustaForm(tpPessoa){
	if(tpPessoa=="F"){
		formPessoaFisica();
		
		//Ajusto os radiobuttons
		$(".btn-group button.btn:eq(0)").addClass("active");
		$(".btn-group button.btn:eq(1)").removeClass("active");
	}
	else{
		formPessoaJuridica();
		//Ajusto os radiobuttons
		$(".btn-group button.btn:eq(1)").addClass("active");		
		$(".btn-group button.btn:eq(0)").removeClass("active");
	}
}


function formPessoaFisica(){
	$("#cpfCnpj").mask("999.999.999-99");
	$("#rgCliente").show();
}

function formPessoaJuridica(){
	$("#cpfCnpj").mask("99.999.999/9999-99");
	$("#rgCliente").hide();	
}

function cancelar(){
	$("#formCliente").validate().cancelSubmit = true;
	var url = "/application/home"; 
	$(location).attr('href',url);
}

function voltar(){
	var url = "/application/home"; 
	$(location).attr('href',url);
}