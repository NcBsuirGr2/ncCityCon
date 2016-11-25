function updateSelect(selected, url) {
	$.ajax({
		url: url,
		success: function(data) {
			console.log("ajax success");
			if(!data) return;
			var jdata = JSON.parse(data);
			var suggestions = jdata.suggestions;
			var repeated = false;
			
			for (var i in suggestions) {
				if (suggestions[i] == $(selected).val()) {
					repeated = true;
					break;
				}
			}
			if (repeated) {
				$(selected).find('option').not(':selected').remove();
			} else {
				$(selected).html("<option label=\" \"></option>");
			}
			
			for (var i in suggestions) {
				if (suggestions[i] == $(selected).val()) {
					continue;
				}
				$(selected).append($('<option>', {
				    value: suggestions[i],
				    text: suggestions[i]
				}));
			}
		}
	})
}
function updCity(country) {
	updateSelect($("#city"), "/suggestions?field=city&country="+country);
}

$(document).ready(function() {
	updCity($("#country").val());

	$("#country").on("change", function() {
		updCity($(this).val());
	});
})