$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".editHref").attr("href", "/router?action=edit&SN="+document.selectedUnique);
		$("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
    });
    $(".editBtn").on("click", function(event) {
        if(!document.selectedId) {
            event.preventDefault();
            $(".selectRouterModal").modal('show');
        }
    })
    $("#deleteForm").submit(function(event) {
		if(!document.selectedId) {
			event.preventDefault();
			if (!document.selectedId) {
	    		e.preventDefault();
	    		$(".selectRouterModal").modal('show');
	    	}
		} 
	});
});