$(document).ready(function() {
	
	// Máscaras nos campos do form
	$("#cpfCnpj").mask("999.999.999-99");
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
	if(tipoPessoa=="F"){
		$("#cpfCnpj").mask("999.999.999-99");
	}
	else{
		$("#cpfCnpj").mask("999.999.999/9999-99");
	}
}