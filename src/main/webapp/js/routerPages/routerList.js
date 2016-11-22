$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".editHref").attr("href", "/router?action=edit&SN="+document.selectedUnique);
		$(".connectionsHref").attr("href", "/connections?SN="+document.selectedUnique);
		$("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
    });
    $(".editBtn").on("click", function(event) {
        checkSelected(event);
    })
    $(".connectionsHref").on("click", function(event) {
        checkSelected(event);
    })
    $(".deleteDialog").on("show.bs.modal", function(event) {
    	checkSelected(event);
    });
});
function checkSelected(event) {
	if(!document.selectedId) {
        event.preventDefault();
        $(".selectRouterModal").modal('show');
    }
}