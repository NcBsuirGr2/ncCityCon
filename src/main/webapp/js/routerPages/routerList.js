$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".editHref").attr("href", "/router?action=edit&SN="+document.selectedUnique);
		$("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
    });
    $("#deleteForm").submit(function(event) {
		if(!document.selectedId) {
			event.preventDefault();
			alert("Select router");
		} 
	});
});