function updateSelect(select, url) {
	$.ajax({
		url: url,
		success: function() {
			console.log("success");
		}
	})
}

$(document).ready(function() {
	$("#country1").on("change", function() {
		console.log($(this).val());
		updateSelect($("city1"), "/suggestions?field=city&country="+$(this).val());
	}) 
})