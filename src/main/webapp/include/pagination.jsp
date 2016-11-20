<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Pagination block -->			
<c:if test="${endPage > 1}">
	<ul class="pagination">
		<c:if test="${beginPage > previousPage}">
			<li class="page-item">
				<a class="page-link" href="?itemsPerPage=${param.itemsPerPage}&page=${previousPage}&sortBy=${param.sortBy}&asc=${param.asc}" aria-label="Previous">
					<span aria-hidden="true">&laquo;</span>
					<span class="sr-only">Previous</span>
				</a>
			</li>
		</c:if>

		
		
		<c:forEach begin="${beginPage}" end="${endPage}" varStatus="i">
			<c:if test="${i.index == currentPage}">
					<c:set var="isActive" value="active"/>
			</c:if>
			<c:if test="${i.index != currentPage}">
					<c:set var="isActive" value=""/>
			</c:if>
			<li class="page-item ${isActive}">
				<a class="page-link" href="?itemsPerPage=${param.itemsPerPage}&page=${i.index}&sortBy=${param.sortBy}&asc=${param.asc}">
					${i.index}
				</a>
			</li>
		</c:forEach>

		<c:if test="${endPage < nextPage}">
			<li class="page-item">
				<a class="page-link" href="?itemsPerPage=${param.itemsPerPage}&page=${nextPage}&sortBy=${param.sortBy}&asc=${param.asc}" aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
					<span class="sr-only">Next</span>
				</a>
			</li>
		</c:if>
	</ul>
</c:if>	

<div class="row">
	<div class="col-sm-3">
	</div>

	<div class="col-sm-3">
		<label class="pull-right control-label">Items per page:</label>
	</div>

	<div class="col-sm-2">
		<select class="pull-left" id="itemsPerPageSelect" onChange="window.location.href=this.value">
            <option <c:if test="${param.itemsPerPage == 5}">selected</c:if>  value="?itemsPerPage=5&page=${param.page}&sortBy=${param.sortBy}&asc=${param.asc}">5</option>
            <option <c:if test="${param.itemsPerPage == 10 || empty param.itemsPerPage}">selected</c:if> value="?itemsPerPage=10&page=${param.page}&sortBy=${param.sortBy}&asc=${param.asc}">10</option>
            <option <c:if test="${param.itemsPerPage == 15}">selected</c:if> value="?itemsPerPage=15&page=${param.page}&sortBy=${param.sortBy}&asc=${param.asc}">15</option>
        </select>
    </div>

    <div class="col-sm-4">
	</div>
</div>
<!-- Pagination -->