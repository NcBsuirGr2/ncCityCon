$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".editHref").attr("href", "/city?action=edit&name="+document.selectedUniqueCity+"&countryName="+document.selectedUniqueCountry);
		$("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
    });
    //$(".deleteDialog").on("show.bs.modal", function(e) {
    //	console.log("Delete dialog");
    //	console.log(document.selectedId);
    //	if (!document.selectedId) {
    //		e.preventDefault();
    //		$(".selectUserModal").modal('show');
    //		//alert("Select user");
    //	}
    //});
});