function validateTextLength(text, col) {
	return text.length >= col;
}

function validateSimpleText(text) {
    return text.match(/^[-a-zA-Z0-9]+$/i);
}

function validateNotEmpty(text) {
	return (text && text != "");
}

function validateASCIIAndHack(password) {
	//Check ascii range
	var asciiMatch = password.match(/^[!-~]+$/i);
	var tagHackMatch = !password.match(/<.*\/>/);
	return asciiMatch && tagHackMatch;
} 
function validateEmail(email) {
	return email.match(/^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@\w{2,}(\.\w{2,}){1,3}$/i);
}

function validateForm(form) {
	var valid = true;
	$(".formAlert").html("Sorry, your form data is invalid");

    var $notEmptyInputs = $(form).find(".notEmptyInput");
    $notEmptyInputs.each(function() {
        if(!validateNotEmpty($(this).val())) {
            $(".formAlert").html("Please fill out required fields");
            valid = false;
            return;
        }
    });
    if (!valid) return false;

	var names = $("input[name='name']");
	names.each(function () {
		if(!validateTextLength(this.value, 3)) {
			$(".formAlert").html("Name is too short. Minimum 3 characters");
			valid = false;
			return;
		}
	});
	if (!valid) return false;

	var login = $("input[name='login']");
	login.each(function () {
		if(!validateTextLength(this.value, 3)) {
			$(".formAlert").html("Login is too short. Minimum 3 characters");
			valid = false;
			return;
		}
	});
    if (!valid) return false;

	var password = $("input[name='password']");
	password.each(function () {
		if(!validateTextLength(this.value, 6)) {
			$(".formAlert").html("Password is too short. Minimum 6 characters");
			valid = false;
			return;
		}
	});
    if (!valid) return false;

    var $textInputs = $(form).find(".simpleText");
    $textInputs.each(function() {
    	if(!validateSimpleText($(this).val())) {
			$(".formAlert").html("Your input \"" + $(this).val().replace(/</g, "&lt;").replace(/>/g, "&gt;") + "\" is invalid");
			valid = false;
			return;
		}
    });
    if (!valid) return false;

    var $asciiInput = $(form).find(".asciiInput");
    $asciiInput.each(function() {
    	if(!validateASCIIAndHack($(this).val())) {
    		$(".formAlert").html("Your input \"" + $(this).val().replace(/</g, "&lt;").replace(/>/g, "&gt;") + "\" is invalid");
			valid = false;
			return;
    	}
    });
    if (!valid) return false;

    var $emailInputs = $(form).find(".emailInput");
    $emailInputs.each(function() {
    	if(!validateEmail($(this).val())) {
    		$(".formAlert").html("Your email is invalid");
			valid = false;
			return;
    	}
    });
    return valid;
}

$(document).ready(function() {
    // var selected;
    // $(".selectable tbody tr" ).on("click", function() {
 	// 	if(selected != null) {
 	// 		selected.removeClass("info");
 	// 	}
 	// 	selected = $(this);
 	// 	document.selectedId = $(this).find("td.idField").text();
 	// 	document.selectedUnique = $(this).find("td.unique").text();
 	// 	selected.addClass("info");
    // });
	$("input, select").focus(function() {
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