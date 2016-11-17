$(document).ready(function() {
	var selected;
	$( ".selectable tbody tr" ).on( "click", function( event ) {
 		if(selected != null) {
 			selected.removeClass("info");
 		}
 		selected = $(this);
 		document.selected = $(this).find("td.unique").text();
 		selected.addClass("info");
    });
});