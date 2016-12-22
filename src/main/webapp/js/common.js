function checkSelected(event) {
	if(!document.selectedId) {
        event.preventDefault();
        $(".selectAlert").removeClass('hide');
    }
}

$(document).ready(function() {
	var selected;
	$(".selectable tbody tr:not(.notSelectable)").on("click", function() {
 		if(selected != null) {
 			selected.removeClass("info");
 		}
 		selected = $(this);
 		document.selectedId = $(this).find("td.idField").text();
 		document.selectedUnique = $(this).find("td.unique").text();
 		selected.addClass("info");
 		$(".selectAlert").addClass("hide");
    });
	$(".editHref").on("click", function(event) {
        checkSelected(event);
    })
    $(".routersHref").on("click", function(event) {
        checkSelected(event);
    })
    $(".connectionsHref").on("click", function(event) {
        checkSelected(event);
    })
    $(".deleteDialog").on("show.bs.modal", function(event) {
    	checkSelected(event);
    });
});

