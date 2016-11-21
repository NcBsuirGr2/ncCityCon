$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".editHref").attr("href", "/router?action=edit&SN="+document.selectedUnique);
		$("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
    });
    $("#deleteForm").submit(function(event) {
    	console.log($("#deleteId").val());
		if($("#deleteId").val() <= 0) {
			event.preventDefault();
			alert("Select router");
		} 
	});
});