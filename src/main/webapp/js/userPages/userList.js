$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".editHref").attr("href", "/user?action=edit&login="+document.selectedUnique);
		$("#deleteId").val(document.selectedId);
    });
});