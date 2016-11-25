$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".editHref").attr("href", "/city?action=edit&name="+document.selectedUniqueCity+"&countryName="+document.selectedUniqueCountry);
		$(".routersHref").attr("href", "/routers?city="+document.selectedUniqueCity+"&country="+document.selectedUniqueCountry);
		$("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
    });

});