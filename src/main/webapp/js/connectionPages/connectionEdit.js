function updateSelect(selected, url) {
	$.ajax({
		url: url,
		success: function(data) {
			//console.log(selected+"\n");
			for (var i in data) {
				console.log(data[i]+"-"+selected);
				if (data[i] == selected) {
					console.log("Repeated "+data[i]);
				}
			}
		}
	})
}

$(document).ready(function() {
	$("#country1").on("change", function() {
		console.log($(this).val());
		updateSelect($("#city1").val(), "/suggestions?field=city&country="+$(this).val());
	}) 
})