$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		var city = $("#city").val();
		var country = $("#country").val();
		if (city && country) {
            $(".editHref").attr("href", "/router?action=edit&SN="+document.selectedUnique+"&city="+city+"&country="+country);
		} else {
            $(".editHref").attr("href", "/router?action=edit&SN="+document.selectedUnique);
		}

		$(".connectionsHref").attr("href", "/connections?SN="+document.selectedUnique);
		$("#deleteId").val(document.selectedId);
    });
});