
$(document).ready(function() {
	$("#novoArquivo").click(function(){
		appendInputFile();
	});
	
	
	function appendInputFile(){
		var htmlFileInput =  "<div class='fileUploadContainerFiles'> " +
						" 	<div class='fileupload fileupload-new' data-provides='fileupload'> " +
						"   		<div class='input-append'>" +
						"	    	<div class='uneditable-input span3'>" +
						"	    	<i class='icon-file fileupload-exists'></i>" + 
						"	    	<span class='fileupload-preview'></span>" +
						"	    	</div>" +
						"	    	<span class='btn btn-file'>" +
						"	    	<span class='fileupload-new'>Selecione o Arquivo</span>" +
						"	    	<span class='fileupload-exists'>Alterar</span>" +
						"	    	<input id='files' name='files' type='file' /></span><a href='#' class='btn fileupload-exists' data-dismiss='fileupload'>Remover</a>" +
						"       </div>" +
						"	</div>" +
						"</div>";
		
		$("#fileUploadContainerFiles").append(htmlFileInput);
	}
});
