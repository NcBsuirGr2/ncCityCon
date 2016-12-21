<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="asc" required="true" rtexprvalue="true" %>
<%@ attribute name="sortByIs" required="true" rtexprvalue="true" %>
<%@ attribute name="sortByNeed" required="true" rtexprvalue="true" %>
<%@ tag body-content="empty"%>

<c:set var="newAsc" value="true"/>
<c:if test="${asc == true and sortByIs == sortByNeed}">
    <c:set var="newAsc" value="false"/>
</c:if>

<a href="?asc=${newAsc}&sortBy=${sortByIs}">
    ${sortByIs}
    <c:if test="${sortByIs == sortByNeed}">
        <c:if test="${newAsc == true}">&#9660;</c:if>
        <c:if test="${newAsc == false}">&#9650;</c:if>
    </c:if>
</a>
