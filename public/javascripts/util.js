// Javascript com métodos genéricos

function validateRequired(fieldId,  messageToDisplay){
	var result = -1;
	var div = "#"+fieldId+"Validation";
	if ($("#"+fieldId).val() == null || $("#"+fieldId).val() =="") {
		$(div).html(messageToDisplay);
		$(div).show();
		result = -1;
	} else {
		$(div).hide();
		result = 1;
	}
	return result;
}

function validateWithRule(fieldId, rule, value, message){
	var result = -1;
	var fieldVal = $("#"+fieldId).val();
	var div = "#"+fieldId+"Validation";
	
	//Contains Rule
	if ("contains"==rule) {
		if (fieldVal.indexOf(value)<0) {
			$(div).html(message);
			$(div).show();
		} else {
			$(div).hide();
			result = 1;
		}
	}
	
	//Equals Rule
	if ("equals"==rule) {
		if (fieldVal != value) {
			$(div).html(message);
			$(div).show();
		} else {
			$(div).hide();
			result = 1;
		}
	}
	return result;
}
// Javascript com métodos genéricos


