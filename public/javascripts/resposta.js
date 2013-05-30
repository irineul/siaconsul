$(document).ready(function() {


$('#respostaProcessoForm').validate({
	rules: {
	  resposta.comentario: {
	    minlength: 5,
	    required: true
	  },
	  anexo: {
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
	  });
});

