<form class="form-horizontal" action="/router" method="POST" role="form" id="form">
    <div class="form-group">
        <label class="col-xs-3 control-label">Country:</label>
        <div class="col-xs-7">
            <select class="form-control" id="country" name="countryName" form="form">
                <option label=" "></option>
                <c:if test="${not empty editRouter or not empty param.country}">
                    <c:choose>
                        <c:when test="${not empty editRouter}">
                            <option value="${editRouter.countryName}" selected>${editRouter.countryName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${param.country}" selected>${param.country}</option>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </select>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-xs-3 control-label">City:</label>
        <div class="col-xs-7">
            <select class="form-control" id="city" name="cityName" form="form">
                <option label=" "></option>
                <c:if test="${not empty editRouter or not empty param.city}">
                    <c:choose>
                        <c:when test="${not empty editRouter}">
                            <option value="${editRouter.cityName}" selected>${editRouter.cityName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${param.city}" selected>${param.city}</option>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label for="SN" class="col-xs-3 control-label">SN:</label>
        <div class="col-xs-7">
            <c:if test="${not empty editRouter}">
                <input type="text" class="form-control" readonly name="SN" value="${editRouter.SN}"/>
            </c:if>
            <c:if test="${empty editRouter}">
                <input type="text" calss="form-control" readonly name="SN" value="${param.SN}"/>
            </c:if>
            
        </div>
    </div>
    <div class="form-group">
        <label for="name" class="col-xs-3 control-label">Name:</label>
        <div class="col-xs-7">
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
    <input type="hidden" name="city" value="${param.city}"/>
    <input type="hidden" name="country" value="${param.country}"/>
 </form>