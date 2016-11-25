<form class="form-horizontal" action="/router" method="POST" role="form" id="form">
    <div class="form-group">
        <label class="col-xs-3 control-label">Country:</label>
        <div class="col-xs-7">
            <select class="form-control" id="country" name="countryName" form="form">
                <option label=" "></option>
                <option value="Belarus" 
                    <c:if test="${editRouter.countryName == 'Belarus' or param.countryName == 'Belarus'}">    selected
                    </c:if>
                    >Belarus</option>
                <option value="Moldova"
                    <c:if test="${editRouter.countryName == 'Moldova' or param.countryName == 'Moldova'}">    selected
                    </c:if>
                    >Moldova</option>
                </option>
                <option value="Poland"
                    <c:if test="${editRouter.countryName == 'Poland' or param.countryName == 'Poland'}">    selected
                    </c:if>
                    >Poland
                </option>
                <option value="Ukraine"
                    <c:if test="${editRouter.countryName == 'Ukraine' or param.countryName == 'Ukraine'}">    selected
                    </c:if>
                    >Ukraine
                </option>
                <option value="Russia"
                    <c:if test="${editRouter.countryName == 'Russia' or param.countryName == 'Russia'}">    selected
                    </c:if>
                    >Russia
                </option>
            </select>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-xs-3 control-label">City:</label>
        <div class="col-xs-7">
            <select class="form-control" id="city" name="cityName" form="form">
                <option label=" "></option>
                <c:if test="${not empty editRouter or not empty param.cityName}">
                    <c:choose>
                        <c:when test="${not empty editRouter}">
                            <option value="${editRouter.cityName}" selected>${editRouter.cityName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${param.cityName}" selected>${param.cityName}</option>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label for="name" class="col-xs-3 control-label">Name:</label>
        <div class="col-xs-7">
            <input class="form-control simpleText" maxlength="15" required placeholder="Name" name="name" type="text" value="${param.name}">
        </div>
    </div>   
    <div class="form-group">
        <label for="login" class="col-xs-3 control-label">SN:</label>
         <div class="col-xs-7">                                 
                <input class="form-control simpleText" maxlength="15" required placeholder="SN" name="SN" type="text" value="${param.SN}">
         </div>
    </div>
    <div class="form-group">
        <label for="group" class="col-xs-3 control-label">Ports:</label>
        <div class="col-xs-2">
            <select class="form-control" id="group" name="portsNum" form="form">
                <option value="1" <c:if test="${param.editPortsNum == 1}">selected</c:if>>1</option>
                <option value="2" <c:if test="${param.editPortsNum == 2}">selected</c:if>>2</option>
                <option value="3" <c:if test="${param.editPortsNum == 3}">selected</c:if>>3</option>
                <option value="4" <c:if test="${param.editPortsNum == 4}">selected</c:if>>4</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="group" class="col-xs-3 control-label">Active:</label>
        <div class="col-xs-2">
            <select class="form-control" name="active" form="form">
                <option value="true" <c:if test="${param.editActive == 'true'}">selected</c:if> >true</option>
                <option value="false" <c:if test="${param.editActive == 'false'}">selected</c:if>>false</option>
            </select>
        </div>
    </div>
    <input type="hidden" name="action" value="add"/>
 </form>