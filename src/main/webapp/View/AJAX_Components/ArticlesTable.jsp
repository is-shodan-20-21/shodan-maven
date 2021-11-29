<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
%>

<table class="table">
    <tr class="table-title">
        <th>ID</th>
        <th>Titolo</th>
        <th>Sottotitolo</th>
        <th>Autore</th>
    </tr>
    
    <c:forEach items="${articles}" var="article">
        <tr class="table-row">
            <td class="table-data">${article.id}</td>
            <td class="table-data">${article.title}</td>
            <td class="table-data">${article.shortTitle}</td>
            <td class="table-data">${article.author.name}</td>
        </tr>
     </c:forEach>
</table>
