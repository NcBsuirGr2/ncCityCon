$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
        var sameSelect = $("#sameSelect").val();
		$(".editHref").attr("href", "/connection?action=edit&id="+document.selectedUnique+"&"+sameSelect);
		$("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
    });
    $(".editBtn").on("click", function(event) {
        if(!document.selectedId) {
            event.preventDefault();
            $(".selectConnectionModal").modal('show');
        }
    })
    $(".deleteDialog").on("show.bs.modal", function(event) {
    	console.log("Delete dialog");
    	console.log(document.selectedId);
    	if (!document.selectedId) {
    		event.preventDefault();
    		$(".selectConnectionModal").modal('show');
    	}
    });
});