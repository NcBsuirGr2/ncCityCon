$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".editHref").attr("href", "/router?action=edit&SN="+document.selectedUnique);
		$(".connectionsHref").attr("href", "/connections?SN="+document.selectedUnique);
		$("#deleteId").val(document.selectedId);
    });
});