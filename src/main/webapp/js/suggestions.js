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
function updCity1(country) {
	updateSelect($("#city1"), "/suggestions?field=city&country="+country);
}
function updSN1(country, city) {
	updateSelect($("#SN1"), "/suggestions?field=router&country="+country+"&city="+city);
}
function updCity2(country) {
	updateSelect($("#city2"), "/suggestions?field=city&country="+country);
}
function updSN2(country, city) {
	updateSelect($("#SN2"), "/suggestions?field=router&country="+country+"&city="+city);
}

$(document).ready(function() {
	updCity1($("#country1").val());
	updSN1($("#country1").val(), $("#city1").val());
	updCity2($("#country2").val());
	updSN2($("#country2").val(), $("#city2").val());


	$("#country1").on("change", function() {
		updCity1($(this).val());
		updSN1($(this).val(), $("#city1").val());
	});
	$("#city1").on("change", function() {
		updSN1($("#country1").val(), $(this).val());
	});


	$("#country2").on("click", function() {
		updCity2($(this).val());
		updSN2($(this).val(), $("#city2").val());
	});
	$("#city2").on("change", function() {
		updSN2($("#country2").val(), $(this).val());
	});
})