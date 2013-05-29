
$(document).ready(function() {
    $('#gridBootstrap').dataTable( {
    	"oLanguage": {
			"sLengthMenu": "Apresentar _MENU_ registros por página",
			"sZeroRecords": "Nenhum registro encontrado",
			"sInfo": "Apresentado _START_ a _END_ de _TOTAL_ registros",
			"sInfoEmpty": "Apresentado 0 a 0 de 0 records",
			"sInfoFiltered": "(filtrado de _MAX_ registros)",
			"sSearch" : "Buscar:",
			"sPrevious" : "Anterior",
			"sNext" : "Próximo"
		},    	
		"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
		"sPaginationType": "bootstrap"
    } );
} );
