<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Pagination block -->			
<c:if test="${endPage > 1}">
	<ul class="pagination">
		<c:if test="${beginPage > previousPage}">
			<li class="page-item">
				<a class="page-link" href="?page=${previousPage}&search=${param.search}" aria-label="Previous">
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
				<a class="page-link" href="?page=${i.index}&search=${param.search}">
					${i.index}
				</a>
			</li>
		</c:forEach>

		<c:if test="${endPage < nextPage}">
			<li class="page-item">
				<a class="page-link" href="?page=${nextPage}&search=${param.search}" aria-label="Next">
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
			<option
					<c:if test="${paginationParameters['users']['itemsPerPage'] == 5}">
						selected
					</c:if>
					<c:choose>
						<c:when test="${not empty param.search}">
							value="?itemsPerPage=5&search=${param.search}">
						</c:when>
						<c:otherwise>
							value="?itemsPerPage=5">
						</c:otherwise>
					</c:choose>
					5
			</option>
			<option
					<c:if test="${paginationParameters['users']['itemsPerPage'] == 10 || empty paginationParameters['cities']['itemsPerPage']}">
						selected
					</c:if>
					<c:choose>
						<c:when test="${not empty param.search}">
							value="?itemsPerPage=10&search=${param.search}">
						</c:when>
						<c:otherwise>
							value="?itemsPerPage=10">
						</c:otherwise>
					</c:choose>
					10
			</option>
			<option
					<c:if test="${paginationParameters['users']['itemsPerPage'] == 15}">
						selected
					</c:if>
					<c:choose>
						<c:when test="${not empty param.search}">
							value="?itemsPerPage=15&search=${param.search}">
						</c:when>
						<c:otherwise>
							value="?itemsPerPage=15">
						</c:otherwise>
					</c:choose>
					15
			</option>
		</select>
    </div>

    <div class="col-sm-4">
	</div>
</div>
<!-- Pagination -->