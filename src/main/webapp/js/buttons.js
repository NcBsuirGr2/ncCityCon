$(document).ready(function() {

	$('#ButtonAdd').click(function() {
		$.ajax({
			url : 'GetUserServlet' + '?'+ $.param({"action" : $('#ButtonAdd').val()}),
			type: 'Get',
			success : function(result) {
				$('#ajaxButtonUserServletResponse').text(result);
			}
		});
	});
	$('#ButtonEdit').click(function() {
		$.ajax({
			url : 'GetUserServlet' + '?'+ $.param({"action" : $('#ButtonEdit').val()}),
			type: 'Put',
			success : function(result) {
				$('#ajaxButtonUserServletResponse').text(result);
			}
		});
	});
	$('#ButtonDelete').click(function() {
		$.ajax({
			url : 'GetUserServlet' + '?'+ $.param({"action" : $('#ButtonDelete').val()}),
			type: 'Delete',
			success : function(result) {
				$('#ajaxButtonUserServletResponse').text(result);
			}

		});
	});



	$('#userGetName').blur(function() {
		$.ajax({
			url : 'GetUserServlet',
			type: 'Get',
			data : {
				action : "asdf"
			},
			success : function(result) {
				$('#ajaxGetUserServletResponse').text(result);
			}
		});
	});

	$('#userGet2Name').blur(function() {
		$.ajax({
			url : 'GetUserServlet' + '?' + $.param({"field" : "field"}),
			type: 'Delete',
			success : function(result) {
				$('#ajaxGet2UserServletResponse').text(result);
			}
		});
	});

	$('#userPostName').blur(function() {
		$.ajax({
			url : 'GetUserServlet',
			type: 'Post',
			data : {
				userName : $('#userPostName').val()
			},
			success : function(result) {
				$('#ajaxPostUserServletResponse').text(result);
			}
		});
	});


});