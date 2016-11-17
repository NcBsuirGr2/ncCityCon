$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function( event ) {
		$(".editHref").attr("href", "/user?action=edit&login="+document.selected);
		$(".deleteButton").attr("value", document.selected);
		console.log($(".deleteButton").attr("value"));
    });
});