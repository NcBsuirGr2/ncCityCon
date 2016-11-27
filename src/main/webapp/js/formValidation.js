function validateSimpleText(text) {
	return text.match(/^[-a-z0-9]+$/i);
}

function validatePassword(password) {
	// Check ascii range
	var asciiMatch = password.match(/^[!-~]+$/i);
	var tagHackMatch = !password.match(/<.*\/>/);
	return asciiMatch && tagHackMatch;
} 
function validateEmail(email) {
	return email.match(/^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@\w+(\.\w+)+$/i);
}

function validateForm(form) {
	var validForm = true;
	$(".formAlert").html("Sorry, your form data is invalid");
    var $textInputs = $(form).find(".simpleText");
    $textInputs.each(function() {
    	if(!validateSimpleText($(this).val())) {
    		$(".formAlert").html("Your input \""+$(this).val().replace(/</g, "&lt;").replace(/>/g, "&gt;")+"\" is invalid");
    		validForm = false;
    		return validForm;
    	}
    });
    var $passwordInputs = $(form).find(".passwordInput");
    $passwordInputs.each(function() {
    	if(!validatePassword($(this).val())) {
    		$(".formAlert").html("Your password is invalid");
    		validForm = false;
    		return validForm;
    	}
    });
    var $emailInputs = $(form).find(".emailInput");
    $emailInputs.each(function() {
    	if(!validateEmail($(this).val())) {
    		$(".formAlert").html("Your email is invalid");
    		validForm = false;
    		return validForm;
    	}
    });
    return validForm;
}

$(document).ready(function() {
	var selected;
	$(".selectable tbody tr" ).on("click", function() {
 		if(selected != null) {
 			selected.removeClass("info");
 		}
 		selected = $(this);
 		document.selectedId = $(this).find("td.idField").text();
 		document.selectedUnique = $(this).find("td.unique").text();
 		selected.addClass("info");
    });
	$("input").focus(function() {
		$(".formAlert").addClass("hide");
	})
	$("#form").submit(function(event) {
		if (!validateForm($('#form'))) {
			event.preventDefault();
			$(".formAlert").removeClass("hide");
		}
	})
	$('.changesDialog').on("show.bs.modal", function(event) {
		if (!validateForm($('#form'))) {
			$(".formAlert").removeClass("hide");
			event.preventDefault();
		}
	})
	
});