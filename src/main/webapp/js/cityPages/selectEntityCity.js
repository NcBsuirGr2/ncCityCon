$(document).ready(function() {
	var selected;
	$(".selectable tbody tr" ).on("click", function() {
 		if(selected != null) {
 			selected.removeClass("info");
 		}
 		selected = $(this);
 		document.selectedId = $(this).find("td.idField").text();
 		document.selectedUniqueCity = $(this).find("td.uniqueCity").text();
 		document.selectedUniqueCountry = $(this).find("td.uniqueCountry").text();
 		selected.addClass("info");
    });
});