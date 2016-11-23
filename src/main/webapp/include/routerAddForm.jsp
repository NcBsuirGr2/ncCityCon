<form class="form-horizontal" action="/router" method="POST" role="form" id="form">
    <div class="form-group">
        <label for="name" class="col-xs-3 control-label">Country:</label>
        <div class="col-xs-7">
            <input class="form-control simpleText" autofocus required placeholder="Country" name="countryName" type="text" value="${param.countryName}">
        </div>
    </div>
    <div class="form-group">
        <label for="name" class="col-xs-3 control-label">City:</label>
        <div class="col-xs-7">
            <input class="form-control simpleText" required placeholder="City" name="cityName" type="text" value="${param.cityName}">
        </div>
    </div>
    <div class="form-group">
        <label for="name" class="col-xs-3 control-label">Name:</label>
        <div class="col-xs-7">
            <input class="form-control simpleText" required placeholder="Name" name="name" type="text" value="${param.name}">
        </div>
    </div>   
    <div class="form-group">
        <label for="login" class="col-xs-3 control-label">SN:</label>
         <div class="col-xs-7">                                 
                <input class="form-control simpleText" required placeholder="SN" name="SN" type="text" value="${param.editSN}">
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