<form class="form-horizontal" action="/router" method="POST" role="form" id="form">
    <div class="form-group">
        <label for="name" class="col-xs-3 control-label">Country:</label>
        <div class="col-xs-9">
        <c:if test="${not empty editRouter}">
            <input class="form-control simpleText" autofocus required placeholder="Country" name="countryName" type="text" value="${editRouter.countryName}">
        </c:if>
        <c:if test="${empty editRouter}">
            <input class="form-control simpleText" required placeholder="Country" name="countryName" type="text" value="${param.countryName}">
        </c:if>
        </div>
    </div>
    <div class="form-group">
        <label for="name" class="col-xs-3 control-label">City:</label>
        <div class="col-xs-9">
        <c:if test="${not empty editRouter}">
            <input class="form-control simpleText" maxlength="15" required placeholder="City" name="cityName" type="text" value="${editRouter.cityName}">
        </c:if>
        <c:if test="${empty editRouter}">
            <input class="form-control simpleText" maxlength="15" required placeholder="City" name="cityName" type="text" value="${param.cityName}">
        </c:if>
        </div>
    </div>
    <div class="form-group">
        <label for="name" class="col-xs-3 control-label">Name:</label>
        <div class="col-xs-9">
        <c:if test="${not empty editRouter}">
            <input class="form-control simpleText" maxlength="15" required placeholder="Name" name="name" type="text" value="${editRouter.name}">
        </c:if>
        <c:if test="${empty editRouter}">
            <input class="form-control simpleText" maxlength="15" required placeholder="Name" name="name" type="text" value="${param.name}">
        </c:if>
        </div>
    </div>  
    <div class="form-group">
        <label for="group" class="col-xs-3 control-label">Active:</label>
        <div class="col-xs-2">
            <select class="form-control" id="group" name="active" form="form">
                <option value="true" <c:if test="${param.active eq 'true'}">selected</c:if>>true</option>
                <option value="false" <c:if test="${param.active eq 'false'}">selected</c:if>>false</option>
            </select>
        </div>
    </div>
    <input type="hidden" name="action" value="edit"/>
    <input type="hidden" name="id" value="${editRouter.id}"/>
    <input type="hidden" name="SN" value="${editRouter.SN}"/>
 </form>