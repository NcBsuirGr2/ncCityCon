function updateSelect(selected, url) {
	$.ajax({
		url: url,
		success: function(data) {
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
function updCountry() {
	updateSelect($("#country"), "/suggestions?field=country");
}

$(document).ready(function() {
	updCountry();
})