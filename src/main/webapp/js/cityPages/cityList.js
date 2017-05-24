$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".editHref").attr("href", "/city?action=edit&name="+document.selectedUniqueCity+"&countryName="+document.selectedUniqueCountry);
		$(".routersHref").attr("href", "/routers?city="+document.selectedUniqueCity+"&country="+document.selectedUniqueCountry);
		$(".connectionsHref").attr("href", "/connections?city="+document.selectedUniqueCity+"&country="+document.selectedUniqueCountry);
		$("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
    });

});

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;
    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};