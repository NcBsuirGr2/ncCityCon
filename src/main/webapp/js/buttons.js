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
	$('#ButtonSave').click(function() {
		$.ajax({
			url : 'GetUserServlet' + '?'+ $.param({"action" : $('#ButtonDelete').val()}),
			type: 'Delete',
			success : function(result) {
				$('#ajaxButtonUserServletResponse').text(result);
			}
		});
	});
});