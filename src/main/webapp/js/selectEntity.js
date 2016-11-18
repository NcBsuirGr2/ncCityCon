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
});