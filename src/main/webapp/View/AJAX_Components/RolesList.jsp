<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
%>

<div id="roles-link">
    <i class="fas fa-dice-d6"></i>
    <span class="role-sign">Ruoli</span>

    <span class="role-selector-icon">
        <i class="fas fa-chevron-right"></i>
    </span>

    <div id="roles-items">
        <c:forEach items="${roles}" var="role">
            <div 
                class="role-item"
                data-role-name="${role.roleName}"
            > 
                ${role.parsedRoleName} 
            </div>
        </c:forEach>
    </div>
</div>

<script src="Scripts/RolesListRoutines.js"></script>