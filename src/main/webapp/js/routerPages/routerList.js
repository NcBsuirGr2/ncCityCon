$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		var city = $("#city").val();
		var country = $("#country").val();
		$(".editHref").attr("href", "/router?action=edit&SN="+document.selectedUnique+"&city="+city+"&country="+country);
		$(".connectionsHref").attr("href", "/connections?SN="+document.selectedUnique);
		$("#deleteId").val(document.selectedId);
    });
});