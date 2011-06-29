<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>

<commons:popupHeader />

    <div id="main-body-header">
        <fmt:message key="console.form.create.label.title"/>
    </div>
    <div id="main-body-content">

        <c:url var="url" value="" />
        <form:form id="createForm" action="${pageContext.request.contextPath}/web/console/app/${appId}/${appVersion}/form/submit" method="POST" commandName="formDefinition" cssClass="form">
            <input type="hidden" name="activityDefId" value="${activityDefId}"/>
            <input type="hidden" name="processDefId" value="${processDefId}"/>
            <form:errors path="*" cssClass="form-errors"/>
            <c:if test="${!empty errors}">
                <span class="form-errors" style="display:block">
                    <c:forEach items="${errors}" var="error">
                        <fmt:message key="${error}"/>
                    </c:forEach>
                </span>
            </c:if>
            <fieldset>
                <legend><fmt:message key="console.form.create.label.details"/></legend>
                <div class="form-row">
                    <label for="field1"><fmt:message key="console.form.common.label.id"/></label>
                    <span class="form-input"><form:input path="id" cssErrorClass="form-input-error" /> *</span>
                </div>
                <div class="form-row">
                    <label for="field1"><fmt:message key="console.form.common.label.name"/></label>
                    <span class="form-input"><form:input path="name" cssErrorClass="form-input-error" /> *</span>
                </div>
                <div class="form-row">
                    <label for="field1"><fmt:message key="console.form.common.label.tableName"/></label>
                    <span class="form-input"><form:input path="tableName" cssErrorClass="form-input-error" /> *</span>
                </div>
            </fieldset>
            <div class="form-buttons">
                <input class="form-button" type="button" value="<fmt:message key="general.method.label.save"/>"  onclick="validateField()"/>
                <input class="form-button" type="button" value="<fmt:message key="general.method.label.cancel"/>" onclick="closeDialog()"/>
            </div>
        </form:form>
    </div>
    
    <script type="text/javascript">
        function validateField(){
            var idMatch = /^[0-9a-zA-Z_]+$/.test($("#id").attr("value"));
            var tableNameMatch = /^[0-9a-zA-Z_]+$/.test($("#tableName").attr("value"));
            if(!idMatch || !tableNameMatch){
                var alertString = '';
                if(!idMatch){
                    alertString = '<fmt:message key="console.form.error.label.idInvalid"/>';
                    $("#id").focus();
                }
                if(!tableNameMatch){
                    if(alertString == ''){
                        $("#tableName").focus();
                    }else{
                        alertString += "\n";
                    }
                    alertString += '<fmt:message key="console.form.error.label.tableNameInvalid"/>';
                }
                alert(alertString);
            }else{
                $("#createForm").submit();
            }
        }

        function closeDialog() {
            if (parent && parent.PopupDialog.closeDialog) {
                parent.PopupDialog.closeDialog();
            }
            return false;
        }
    </script>

<commons:popupFooter />

